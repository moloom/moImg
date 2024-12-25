#!/bin/bash
#run.sh is a tool to upload latest JAR to remote host
# and restart the MOSS service on the remote host
#前提条件是docker compose 运行了此项目


HOST="moloom@moloom.cn"

mvn -DskipTests=true -Dmaven.compile.fork=true -T 1C clean package

scp -P 7777 target/api-1.7.1.jar $HOST:~/moimg/api

ssh -p 7777 $HOST 'bash -s' << EOF
cd ~/moimg
docker compose stop api
docker compose cp -a api/api-1.7.1.jar api:/api
docker compose start api
EOF
