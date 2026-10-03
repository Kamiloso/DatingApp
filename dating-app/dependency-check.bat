@echo off

if "%NVD_API_KEY%"=="" (
    echo ERROR: NVD_API_KEY environment variable is missing.
	echo.
    echo 1. Get your API key at: https://nvd.nist.gov/developers/request-an-api-key
    echo 2. Set it as an environment variable named NVD_API_KEY and restart this terminal.
	echo.
    pause
    exit /b 1
)

call gradlew.bat dependencyCheckAggregate
start .\build\reports\dependency-check\dependency-check-report.html
exit /b 0