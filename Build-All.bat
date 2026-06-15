@echo off
title TitanScape - Build All
echo ============================================
echo        TitanScape RSPS - Build
echo ============================================
echo.
echo Building server and client JARs...
echo.

call gradlew.bat buildAll

if errorlevel 1 (
    echo.
    echo Build FAILED! Check errors above.
) else (
    echo.
    echo Build SUCCESSFUL!
    echo.
    echo Server JAR: build\libs\titanscape-rsps-1.0.0-server.jar
    echo Client JAR: build\libs\titanscape-rsps-1.0.0-client.jar
    echo.
    echo To start: double-click Start-Server.bat, then Start-Client.bat
)

echo.
pause
