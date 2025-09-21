@echo off
echo 🔧 Setting up Android development environment...
echo.

REM Set up Java environment
set JAVA_HOME=C:\Program Files\Microsoft\jdk-17.0.16.8-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

REM Set up Android environment
set ANDROID_HOME=C:\Users\pepsi\AppData\Local\Android\Sdk
set ANDROID_SDK_ROOT=%ANDROID_HOME%
set PATH=%ANDROID_HOME%\platform-tools;%ANDROID_HOME%\tools;%ANDROID_HOME%\tools\bin;%PATH%

echo ✅ Java Home: %JAVA_HOME%
echo ✅ Android SDK: %ANDROID_HOME%
echo.

echo 🚀 Launching Android Studio with proper environment...
echo 📁 Project: %~dp0
echo.

REM Launch Android Studio with the project
start "" "C:\Program Files\Android\Android Studio\bin\studio64.exe" "%~dp0"

echo.
echo 🎯 Android Studio is starting with your blockchain game project!
echo.
echo 📋 Once Android Studio opens:
echo 1. Wait for Gradle sync to complete
echo 2. If you see any SDK errors, go to File → Project Structure → SDK Location
echo 3. Set Android SDK location to: C:\Users\pepsi\AppData\Local\Android\Sdk
echo 4. Click Apply and OK
echo 5. Let Gradle sync again
echo 6. Create/start an emulator (Tools → AVD Manager)
echo 7. Click the green Run button ▶️
echo.
echo 🎮 Your blockchain turn-based game will launch!
echo.
pause