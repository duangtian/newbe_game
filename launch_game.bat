@echo off
echo 🎮 Blockchain Turn-Based Game Launcher 🎮
echo.
echo Starting Android Studio...
echo.

REM Set Java environment
set JAVA_HOME=C:\Program Files\Microsoft\jdk-17.0.16.8-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

REM Launch Android Studio with this project
"C:\Program Files\Android\Android Studio\bin\studio64.exe" "%~dp0"

echo.
echo Android Studio should open with your blockchain game project!
echo.
echo 📱 To run the game:
echo 1. Wait for Gradle sync to complete
echo 2. Connect an Android device OR create an emulator
echo 3. Click the green "Run" button
echo.
echo 🎯 Game Features:
echo - Blockchain wallet integration
echo - Turn-based combat with AI
echo - Real-time battle log
echo - Game result recording on blockchain
echo.
pause