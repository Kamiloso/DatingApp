@echo off

set "GRADLEW=.\gradlew.bat"
set "JAR_PATH_SRC=.\build\libs"
set "ENV_PATH=.\dating-app-portable"
set "JAR_PATH_TRG=.\dating-app-portable\backend"

:: Build backend
call "%GRADLEW%" clean bootJar

:: Copy backend jar
del "%JAR_PATH_TRG%\*.jar"
copy "%JAR_PATH_SRC%\*.jar" "%JAR_PATH_TRG%"
