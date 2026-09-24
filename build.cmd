@echo off
rem OryxOS 构建脚本：固化 JAVA_HOME / MAVEN_HOME，一键编译打包
setlocal
set "JAVA_HOME=C:\Users\10142\.jdk\jdk-25.0.2"
set "MAVEN_HOME=D:\code\apache-maven-3.9.16"
set "PATH=%MAVEN_HOME%\bin;%JAVA_HOME%\bin;%PATH%"

if "%1"=="" (
    mvn clean package
) else (
    mvn %*
)
endlocal