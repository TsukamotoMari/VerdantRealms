@echo off
title TitanScape Client
echo ============================================
echo        TitanScape RSPS - Client
echo ============================================
echo.
echo Starting TitanScape client...
echo.

:: Check for Java
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java is not installed or not in PATH.
    echo Please install Java 17+ from https://adoptium.net
    echo.
    pause
    exit /b 1
)

:: Build if needed
if not exist "build\libs\titanscape-rsps-1.0.0-client.jar" (
    echo Building client JAR...
    call gradlew.bat clientJar
    if errorlevel 1 (
        echo Build failed! Check the error above.
        pause
        exit /b 1
    )
)

:: Run client
echo.
echo Launching TitanScape client...
echo.
java -Xmx256m -Xms128m -jar build\libs\titanscape-rsps-1.0.0-client.jar
