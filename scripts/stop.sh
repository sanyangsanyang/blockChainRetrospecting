#!/usr/bin/env bash
cd ../basic-network
./stop.sh

docker rmi -f $(docker images | grep dev-peer[0-9].org[0-9] | awk '{print $3}')

docker network prune
