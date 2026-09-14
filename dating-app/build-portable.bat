@echo off

set "JAR_SOURCE_DIR=.\build\libs"
set "CONFIG_SOURCE_DIR=.\config"
set "BACKEND_DIR=.\portable\backend"

call .\gradlew.bat clean bootJar

call :copy_to_backend "%JAR_SOURCE_DIR%" "*.jar"
call :copy_to_backend "%CONFIG_SOURCE_DIR%" "application.yml"

exit /b 0

:copy_to_backend
setlocal
set "source=%~1\%~2"
set "target=%BACKEND_DIR%\%~2"

if not exist "%BACKEND_DIR%" mkdir "%BACKEND_DIR%"

if exist "%target%" del "%target%"
copy "%source%" "%BACKEND_DIR%"

endlocal
exit /b 0