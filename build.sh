#!/usr/bin/env bash
# OryxOS 构建脚本：固化 JAVA_HOME / MAVEN_HOME，一键编译打包
# 用法: ./build.sh            -> mvn clean package
#       ./build.sh <args...>  -> mvn <args...>
export JAVA_HOME="C:/Users/10142/.jdk/jdk-25.0.2"
export MAVEN_HOME="D:\\code\\apache-maven-3.9.16"
export PATH="$MAVEN_HOME/bin:$JAVA_HOME/bin:$PATH"

if [ "$#" -eq 0 ]; then
    exec mvn clean package
else
    exec mvn "$@"
fi