#!/bin/bash
#run.sh is a tool to upload latest JAR to remote host
# and restart the MOSS service on the remote host
#前提条件是docker compose 运行了此项目


HOST="moloom@moloom.cn"

npm run build

ssh -p 7777 $HOST 'bash -s' <<EOF
rm -rf ~/moimg/work_base_env/nginx/html/*
EOF

scp -r -P 7777 dist/* $HOST:~/moimg/work_base_env/nginx/html

ssh -p 7777 $HOST 'bash -s' << EOF
cd ~/moimg
docker compose down page
rm -rf /moimg/nginx/html/*
cp -rf work_base_env/nginx/html/* /moimg/nginx/html/
docker compose up -d page
EOF
