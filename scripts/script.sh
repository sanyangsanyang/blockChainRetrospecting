#!/usr/bin/env bash 

TIME=1

cd ../chaincode-invoker

echo Init an item
sleep $TIME
node invoke.js -method addItem -itemid asset1 -latitude 100 -longitude 200 -address 南京 -name 器材 -batchNum 12345 -productionDate 2018-01-01-01:01:01 -expire false

echo Query the item
sleep $TIME
node query.js -method getItem -itemId asset1

echo Change environment of the item
sleep $TIME
node invoke.js -method changeItem -itemId asset1 -latitude 50 -longitude 150 -address 北京

echo Query the item
sleep $TIME
node query.js -method getItem -itemId asset1

echo Expire the item
sleep $TIME
node invoke.js -method expireItem -itemId asset1 -expire true

echo Query the item
sleep $TIME
node query.js -method getItem -itemId asset1

echo Init an item
sleep $TIME
node invoke.js -method addItem -itemid asset2 -latitude 194 -longitude 255 -address 美国 -name 哑铃 -batchNum 66666 -productionDate 2016-01-01-01:01:01 -expire false

echo Get all items
sleep $TIME
node query.js -method getAllItems -prefix asset

echo Get item history
sleep $TIME
node query.js -method getItemHistory -itemId asset1


