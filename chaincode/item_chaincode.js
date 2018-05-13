/*
# Copyright IBM Corp. All Rights Reserved.
#
# SPDX-License-Identifier: Apache-2.0
*/

// ====CHAINCODE EXECUTION SAMPLES (CLI) ==================

// ==== Invoke items ====
//docker exec -e "CORE_PEER_LOCALMSPID=Org1MSP" -e "CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp" cli
// peer chaincode invoke -C mychannel -n servicecode -c '{"Args":["inititem","item1","blue","35","tom"]}'
// peer chaincode invoke -C mychannel -n servicecode -c '{"Args":["inititem","item2","red","50","tom"]}'
// peer chaincode invoke -C mychannel -n servicecode -c '{"Args":["inititem","item3","blue","70","tom"]}'
// peer chaincode invoke -C mychannel -n servicecode -c '{"Args":["transferitem","item2","jerry"]}'
// peer chaincode invoke -C mychannel -n servicecode -c '{"Args":["transferitemsBasedOnColor","blue","jerry"]}'
// peer chaincode invoke -C mychannel -n servicecode -c '{"Args":["delete","item1"]}'

// ==== Query items ====
// peer chaincode query -C mychannel -n servicecode -c '{"Args":["readitem","item1"]}'
// peer chaincode query -C mychannel -n servicecode -c '{"Args":["getitemsByRange","item1","item3"]}'
// peer chaincode query -C mychannel -n servicecode -c '{"Args":["getHistoryForitem","item1"]}'

// Rich Query (Only supported if CouchDB is used as state database):
//   peer chaincode query -C mychannel -n servicecode -c '{"Args":["queryitemsByOwner","tom"]}'
//   peer chaincode query -C mychannel -n servicecode -c '{"Args":["queryitems","{\"selector\":{\"owner\":\"tom\"}}"]}'

'use strict';
const shim = require('fabric-shim');
const util = require('util');

let Chaincode = class {
  async Init(stub) {
    let ret = stub.getFunctionAndParameters();
    console.info(ret);
    console.info('=========== Instantiated items Chaincode ===========');
    return shim.success();
  }

  async Invoke(stub) {
    console.info('Transaction ID: ' + stub.getTxID());
    console.info(util.format('Args: %j', stub.getArgs()));

    let ret = stub.getFunctionAndParameters();
    console.info(ret);

    let method = this[ret.fcn];
    if (!method) {
      console.log('no function of name:' + ret.fcn + ' found');
      throw new Error('Received unknown function ' + ret.fcn + ' invocation');
    }
    try {
      let payload = await method(stub, ret.params, this);
      return shim.success(payload);
    } catch (err) {
      console.log(err);
      return shim.error(err);
    }
  }

  // ===============================================
  // inititem - create a new item
  // ===============================================
  async addItem(stub, args, thisClass) {
    // ==== Input sanitation ====
    console.info('--- start init item ---')

    if (args.length != 8) {
      throw new Error('Incorrect number of arguments. Expecting itemId of the item to query');
    }

    let itemId = args[0];
    let latitude = args[1];
    let longitude = args[2];
    let address = args[3];
    let name =args[4];
    let batchNum = args[5];
    let productionDate = args[6];
    let expire = args[7];

    let item = {};
    item.docType = 'item';
    item.itemId = itemId;
    let environmentStatus = {};
    environmentStatus.latitude = latitude;
    environmentStatus.longitude = longitude;
    environmentStatus.address = address;
    item.environmentStatus = environmentStatus;
    let fixedInfo = {};
    fixedInfo.name = name;
    fixedInfo.batchNum = batchNum;
    fixedInfo.productionDate = productionDate;
    item.fixedInfo = fixedInfo;
    item.expire = expire;

    await stub.putState(itemId , Buffer.from(JSON.stringify(item)));

    console.info('- end init item');
  }

  // ===============================================
  // readitem - read a item from chaincode state
  // ===============================================
  async getItem(stub, args, thisClass) {
    if (args.length != 1) {
      throw new Error('Incorrect number of arguments. Expecting itemId of the item to query');
    }

    let itemId = args[0];
    if (!itemId) {
      throw new Error(' item name must not be empty');
    }
    let itemAsbytes = await stub.getState(itemId); //get the item from chaincode state
    if (!itemAsbytes.toString()) {
      let jsonResp = {};
      jsonResp.Error = 'item does not exist: ' + itemId;
      throw new Error(JSON.stringify(jsonResp));
    }
    console.info('=======================================');
    console.log(itemAsbytes.toString());
    console.info('=======================================');
    return itemAsbytes;
  }

  // ==================================================
  // delete - remove a item key/value pair from state
  // ==================================================
  async delete(stub, args, thisClass) {
    if (args.length != 1) {
      throw new Error('Incorrect number of arguments. Expecting name of the item to delete');
    }
    let itemName = args[0];
    if (!itemName) {
      throw new Error('item name must not be empty');
    }
    // to maintain the color~name index, we need to read the item first and get its color
    let valAsbytes = await stub.getState(itemName); //get the item from chaincode state
    let jsonResp = {};
    if (!valAsbytes) {
      jsonResp.error = 'item does not exist: ' + name;
      throw new Error(jsonResp);
    }
    let itemJSON = {};
    try {
      itemJSON = JSON.parse(valAsbytes.toString());
    } catch (err) {
      jsonResp = {};
      jsonResp.error = 'Failed to decode JSON of: ' + itemName;
      throw new Error(jsonResp);
    }

    await stub.deleteState(itemName); //remove the item from chaincode state

    // delete the index
    let indexName = 'color~name';
    let colorNameIndexKey = stub.createCompositeKey(indexName, [itemJSON.color, itemJSON.name]);
    if (!colorNameIndexKey) {
      throw new Error(' Failed to create the createCompositeKey');
    }
    //  Delete index entry to state.
    await stub.deleteState(colorNameIndexKey);
  }

  // ===========================================================
  // transfer a item by setting a new owner name on the item
  // ===========================================================
  async changeItem(stub, args, thisClass) {

    if (args.length < 4) {
      throw new Error('Incorrect number of arguments. Expecting itemId , longitude , latitude and address');
    }

    let itemId = args[0];
    let latitude = args[1];
    let longitude = args[2];
    let address = args[3];
    console.info('- start transferitem ');

    let itemAsBytes = await stub.getState(itemId);
    if (!itemAsBytes || !itemAsBytes.toString()) {
      throw new Error('item does not exist');
    }
    let itemToTransfer = {};
    try {
      itemToTransfer = JSON.parse(itemAsBytes.toString()); //unmarshal
    } catch (err) {
      let jsonResp = {};
      jsonResp.error = 'Failed to decode JSON of: ' + itemId;
      throw new Error(jsonResp);
    }
    console.info(itemToTransfer);

    let environmentStatus = {};
    environmentStatus.longitude = longitude;
    environmentStatus.latitude = latitude;
    environmentStatus.address = address;
    
    itemToTransfer.environmentStatus = environmentStatus;

    let itemJSONasBytes = Buffer.from(JSON.stringify(itemToTransfer));
    await stub.putState(itemId, itemJSONasBytes); //rewrite the item

    console.info('- end transferitem (success)');
  }

    async expireItem(stub, args, thisClass) {

    if (args.length < 2) {
      throw new Error('Incorrect number of arguments. Expecting itemId , expire');
    }

    let itemId = args[0];
    let expire = args[1];
    console.info('- start transferitem ');

    let itemAsBytes = await stub.getState(itemId);
    if (!itemAsBytes || !itemAsBytes.toString()) {
      throw new Error('item does not exist');
    }
    let itemToTransfer = {};
    try {
      itemToTransfer = JSON.parse(itemAsBytes.toString()); //unmarshal
    } catch (err) {
      let jsonResp = {};
      jsonResp.error = 'Failed to decode JSON of: ' + itemId;
      throw new Error(jsonResp);
    }
    console.info(itemToTransfer);
    
    itemToTransfer.expire = expire;

    let itemJSONasBytes = Buffer.from(JSON.stringify(itemToTransfer));
    await stub.putState(itemId, itemJSONasBytes); //rewrite the item

    console.info('- end transferitem (success)');
  }

  // ===========================================================================================
  // getitemsByRange performs a range query based on the start and end keys provided.

  // Read-only function results are not typically submitted to ordering. If the read-only
  // results are submitted to ordering, or if the query is used in an update transaction
  // and submitted to ordering, then the committing peers will re-execute to guarantee that
  // result sets are stable between endorsement time and commit time. The transaction is
  // invalidated by the committing peers if the result set has changed between endorsement
  // time and commit time.
  // Therefore, range queries are a safe option for performing update transactions based on query results.
  // ===========================================================================================
  async getAllItems(stub, args, thisClass) {

    if (args.length != 1) {
      throw new Error('Incorrect number of arguments. Expecting 1');
    }

    let prelix = args[0];

    let startKey = prelix+"1";
    let endKey = prelix+"99";

    let resultsIterator = await stub.getStateByRange(startKey, endKey);
    let method = thisClass['getAllResults'];
    let results = await method(resultsIterator, false);

    return Buffer.from(JSON.stringify(results));
  }

  // ==== Example: GetStateByPartialCompositeKey/RangeQuery =========================================
  // transferitemsBasedOnColor will transfer items of a given color to a certain new owner.
  // Uses a GetStateByPartialCompositeKey (range query) against color~name 'index'.
  // Committing peers will re-execute range queries to guarantee that result sets are stable
  // between endorsement time and commit time. The transaction is invalidated by the
  // committing peers if the result set has changed between endorsement time and commit time.
  // Therefore, range queries are a safe option for performing update transactions based on query results.
  // ===========================================================================================
  async transferitemsBasedOnColor(stub, args, thisClass) {

    //   0       1
    // 'color', 'bob'
    if (args.length < 2) {
      throw new Error('Incorrect number of arguments. Expecting color and owner');
    }

    let color = args[0];
    let newOwner = args[1].toLowerCase();
    console.info('- start transferitemsBasedOnColor ', color, newOwner);

    // Query the color~name index by color
    // This will execute a key range query on all keys starting with 'color'
    let coloreditemResultsIterator = await stub.getStateByPartialCompositeKey('color~name', [color]);

    let method = thisClass['transferitem'];
    // Iterate through result set and for each item found, transfer to newOwner
    while (true) {
      let responseRange = await coloreditemResultsIterator.next();
      if (!responseRange || !responseRange.value || !responseRange.value.key) {
        return;
      }
      console.log(responseRange.value.key);

      // let value = res.value.value.toString('utf8');
      let objectType;
      let attributes;
      ({
        objectType,
        attributes
      } = await stub.splitCompositeKey(responseRange.value.key));

      let returnedColor = attributes[0];
      let returneditemName = attributes[1];
      console.info(util.format('- found a item from index:%s color:%s name:%s\n', objectType, returnedColor, returneditemName));

      // Now call the transfer function for the found item.
      // Re-use the same function that is used to transfer individual items
      let response = await method(stub, [returneditemName, newOwner]);
    }

    let responsePayload = util.format('Transferred %s items to %s', color, newOwner);
    console.info('- end transferitemsBasedOnColor: ' + responsePayload);
  }


  // ===== Example: Parameterized rich query =================================================
  // queryitemsByOwner queries for items based on a passed in owner.
  // This is an example of a parameterized query where the query logic is baked into the chaincode,
  // and accepting a single query parameter (owner).
  // Only available on state databases that support rich query (e.g. CouchDB)
  // =========================================================================================
  async queryitemsByOwner(stub, args, thisClass) {
    //   0
    // 'bob'
    if (args.length < 1) {
      throw new Error('Incorrect number of arguments. Expecting owner name.')
    }

    let owner = args[0].toLowerCase();
    let queryString = {};
    queryString.selector = {};
    queryString.selector.docType = 'item';
    queryString.selector.owner = owner;
    let method = thisClass['getQueryResultForQueryString'];
    let queryResults = await method(stub, JSON.stringify(queryString), thisClass);
    return queryResults; //shim.success(queryResults);
  }

  // ===== Example: Ad hoc rich query ========================================================
  // queryitems uses a query string to perform a query for items.
  // Query string matching state database syntax is passed in and executed as is.
  // Supports ad hoc queries that can be defined at runtime by the client.
  // If this is not desired, follow the queryitemsForOwner example for parameterized queries.
  // Only available on state databases that support rich query (e.g. CouchDB)
  // =========================================================================================
  async queryitems(stub, args, thisClass) {
    //   0
    // 'queryString'
    if (args.length < 1) {
      throw new Error('Incorrect number of arguments. Expecting queryString');
    }
    let queryString = args[0];
    if (!queryString) {
      throw new Error('queryString must not be empty');
    }
    let method = thisClass['getQueryResultForQueryString'];
    let queryResults = await method(stub, queryString, thisClass);
    return queryResults;
  }

  async getAllResults(iterator, isHistory) {
    let allResults = [];
    while (true) {
      let res = await iterator.next();

      if (res.value && res.value.value.toString()) {
        let jsonRes = {};
        console.log(res.value.value.toString('utf8'));

        if (isHistory && isHistory === true) {
          jsonRes.TxId = res.value.tx_id;
          jsonRes.Timestamp = res.value.timestamp;
          jsonRes.IsDelete = res.value.is_delete.toString();
          try {
            jsonRes.Value = JSON.parse(res.value.value.toString('utf8'));
          } catch (err) {
            console.log(err);
            jsonRes.Value = res.value.value.toString('utf8');
          }
        } else {
          jsonRes.Key = res.value.key;
          try {
            jsonRes.Record = JSON.parse(res.value.value.toString('utf8'));
          } catch (err) {
            console.log(err);
            jsonRes.Record = res.value.value.toString('utf8');
          }
        }
        allResults.push(jsonRes);
      }
      if (res.done) {
        console.log('end of data');
        await iterator.close();
        console.info(allResults);
        return allResults;
      }
    }
  }

  // =========================================================================================
  // getQueryResultForQueryString executes the passed in query string.
  // Result set is built and returned as a byte array containing the JSON results.
  // =========================================================================================
  async getQueryResultForQueryString(stub, queryString, thisClass) {

    console.info('- getQueryResultForQueryString queryString:\n' + queryString)
    let resultsIterator = await stub.getQueryResult(queryString);
    let method = thisClass['getAllResults'];

    let results = await method(resultsIterator, false);

    return Buffer.from(JSON.stringify(results));
  }

  async getItemHistory(stub, args, thisClass) {

    if (args.length < 1) {
      throw new Error('Incorrect number of arguments. Expecting 1')
    }
    let itemId = args[0];
    console.info('- start getHistoryForitem: %s\n', itemId);

    let resultsIterator = await stub.getHistoryForKey(itemId);
    let method = thisClass['getAllResults'];
    let results = await method(resultsIterator, true);

    return Buffer.from(JSON.stringify(results));
  }
};

shim.start(new Chaincode());
