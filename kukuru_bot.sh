#!/bin/bash

base_dir=$(cd `dirname $0`; pwd)
target_dir=${base_dir}/target
client_dir=${base_dir}/client
jar_list=("kukuru-bot-0.0.1.jar")

#使用说明，用来提示输入参数
function usage() {
  echo "Usage: ./kukuru_bot.sh [start|stop|restart|status]"
  exit 1
}

#检查程序是否在运行
function is_exist() {
  pid=$(ps -ef | grep "$1" | grep -v grep | awk '{print $2}')
  #如果不存在返回1，存在返回0
  if [ -z "${pid}" ]; then
    return 1
  else
    return 0
  fi
}

#启动方法
function start() {
  is_exist "$1"
  if [ $? -eq "0" ]; then
    echo "$1 is already running. pid=${pid} ."
  else
    echo "run $1..."
    java -Xms1024m -Xmx1024m -jar "${target_dir}/$1" >/dev/null 2>&1 &
  fi
}

function start_all() {
  for jar_name in "${jar_list[@]}"; do
    start "$jar_name"
    sleep 5
  done
}

#停止方法
function stop() {
  is_exist "$1"
  if [ $? -eq "0" ]; then
    kill -9 "$pid"
    echo "kill thread $1: $pid"
  else
    echo "$1 is not running"
  fi
}

function stop_all() {
  pkill Go-Mirai-Client
  for jar_name in "${jar_list[@]}"; do
    stop "$jar_name"
  done
}

#输出运行状态
function status() {
  is_exist "$1"
  if [ $? -eq "0" ]; then
    echo "$1 is running. Pid is ${pid}"
  else
    echo "$1 is NOT running."
  fi
}

function status_all() {
  for jar_name in "${jar_list[@]}"; do
    status "$jar_name"
  done
}

#重启
function restart() {
  stop "$1"
  start "$1"
}

function restart_all() {
  stop_all
  sleep 1
  start_all
}

chmod 777 ${client_dir}/Go-Mirai-Client
#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
"start")
  start_all
  ;;
"stop")
  stop_all
  ;;
"status")
  status_all
  ;;
"restart")
  restart_all
  ;;
*)
  usage
  ;;
esac
