#!/bin/bash
#run.sh is a tool to upload latest JAR to remote host
# and restart the MOSS service on the remote host
#前提条件是docker compose 运行了此项目


HOST="moloom@moloom.com"

mvn clean && mvn package

scp -P 7777 target/api-1.7.1.jar $HOST:~/moImg/backend

ssh -p 7777 $HOST 'bash -s' << EOF
cd ~/moImg
docker compose stop moImg-api
docker compose cp -a backend/api-1.7.1.jar moImg-api:/moImg
docker compose start moImg-api
EOF
