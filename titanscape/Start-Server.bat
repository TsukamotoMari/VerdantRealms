@echo off
title TitanScape Server
echo ============================================
echo        TitanScape RSPS - Server
echo ============================================
echo.
echo Starting TitanScape server...
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
if not exist "build\libs\titanscape-rsps-1.0.0-server.jar" (
    echo Building server JAR...
    call gradlew.bat serverJar
    if errorlevel 1 (
        echo Build failed! Check the error above.
        pause
        exit /b 1
    )
)

:: Run server
echo.
echo Server starting on port 43594...
echo Press Ctrl+C to stop the server.
echo.
java -Xmx512m -Xms256m -jar build\libs\titanscape-rsps-1.0.0-server.jar
pause
