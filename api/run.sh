#!/bin/bash
#run.sh is a tool to upload latest JAR to remote host
# and restart the MOSS service on the remote host
#前提条件是docker compose 运行了此项目


HOST="moloom@moloom.com"

mvn clean && mvn package

scp -P 7777 target/MOSS-1.7.1.jar $HOST:~/moss/backend

ssh -p 7777 $HOST 'bash -s' << EOF
cd ~/moss
docker compose stop moss-backend
docker compose cp -a backend/MOSS-1.7.1.jar moss-backend:/moss
docker compose start moss-backend
EOF
