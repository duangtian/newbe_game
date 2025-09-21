@echo off
title Blockchain Turn-Based Game Player 🎮⛓️
color 0A

echo.
echo  ██████╗ ██╗      ██████╗  ██████╗██╗  ██╗ ██████╗██╗  ██╗ █████╗ ██╗███╗   ██╗
echo  ██╔══██╗██║     ██╔═══██╗██╔════╝██║ ██╔╝██╔════╝██║  ██║██╔══██╗██║████╗  ██║
echo  ██████╔╝██║     ██║   ██║██║     █████╔╝ ██║     ███████║███████║██║██╔██╗ ██║
echo  ██╔══██╗██║     ██║   ██║██║     ██╔═██╗ ██║     ██╔══██║██╔══██║██║██║╚██╗██║
echo  ██████╔╝███████╗╚██████╔╝╚██████╗██║  ██╗╚██████╗██║  ██║██║  ██║██║██║ ╚████║
echo  ╚═════╝ ╚══════╝ ╚═════╝  ╚═════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝
echo                    TURN-BASED GAME 🎮⛓️
echo.
echo 🎯 Your blockchain game is ready! Choose how to play:
echo.
echo [1] 🚀 Launch Android Studio (Recommended)
echo [2] 📱 Install on connected Android device
echo [3] 🔧 Manual emulator start
echo [4] 📋 View game features
echo [5] 📖 Read setup guide
echo [Q] Quit
echo.
set /p choice="Enter your choice (1-5 or Q): "

if /i "%choice%"=="1" goto android_studio
if /i "%choice%"=="2" goto install_device
if /i "%choice%"=="3" goto start_emulator
if /i "%choice%"=="4" goto show_features
if /i "%choice%"=="5" goto show_guide
if /i "%choice%"=="q" goto quit
goto menu

:android_studio
echo.
echo 🚀 Starting Android Studio...
echo 📁 Opening project: %~dp0
echo.
set JAVA_HOME=C:\Program Files\Microsoft\jdk-17.0.16.8-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%
start "" "C:\Program Files\Android\Android Studio\bin\studio64.exe" "%~dp0"
echo.
echo ✅ Android Studio should open with your blockchain game!
echo 📋 Next steps:
echo    1. Wait for Gradle sync to complete
echo    2. Create/start an emulator (Tools → AVD Manager)
echo    3. Click the green Run button ▶️
echo    4. Enjoy your blockchain game! 🎮
echo.
goto end

:install_device
echo.
echo 📱 Installing on connected Android device...
echo 🔍 Checking for devices...
set JAVA_HOME=C:\Program Files\Microsoft\jdk-17.0.16.8-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%
set ANDROID_HOME=C:\Users\%USERNAME%\AppData\Local\Android\Sdk
"%ANDROID_HOME%\platform-tools\adb.exe" devices
echo.
echo 🎯 Installing blockchain game...
gradlew.bat installDebug
if %errorlevel%==0 (
    echo ✅ Game installed successfully!
    echo 🎮 Look for "Blockchain Turn-Based Game" on your device
) else (
    echo ❌ Installation failed. Make sure:
    echo    - Android device is connected via USB
    echo    - USB Debugging is enabled
    echo    - Device drivers are installed
)
goto end

:start_emulator
echo.
echo 🔧 Starting Android emulator manually...
set ANDROID_HOME=C:\Users\%USERNAME%\AppData\Local\Android\Sdk
echo 📋 Available emulators:
"%ANDROID_HOME%\emulator\emulator.exe" -list-avds
echo.
echo 🚀 Starting Pixel 3a emulator...
start "" "%ANDROID_HOME%\emulator\emulator.exe" -avd Pixel_3a_API_34_extension_level_7_x86_64
echo.
echo ⏳ Emulator is starting... This may take 1-2 minutes
echo 💡 Tip: Wait for the emulator to fully boot, then run option [2]
goto end

:show_features
echo.
echo 🎮 BLOCKCHAIN TURN-BASED GAME FEATURES:
echo.
echo 🔗 BLOCKCHAIN INTEGRATION:
echo    ✅ Mock Ethereum wallet connection
echo    ✅ 1.5 ETH starting balance
echo    ✅ Game results recorded on blockchain
echo    ✅ Transaction hash tracking
echo.
echo ⚔️ TURN-BASED COMBAT:
echo    ✅ Player vs AI battles
echo    ✅ Attack: Deal 15-25 damage
echo    ✅ Defend: Block 50%% damage + heal 5 HP
echo    ✅ Special Attack: Deal 25-35 damage (limited)
echo.
echo 📱 MODERN ANDROID UI:
echo    ✅ Material Design interface
echo    ✅ Thai language support
echo    ✅ Real-time battle log
echo    ✅ Smooth animations
echo.
echo 🎯 GAMEPLAY:
echo    ✅ Strategic combat system
echo    ✅ AI opponent with random actions
echo    ✅ Win condition: Reduce opponent HP to 0
echo    ✅ Replay functionality
echo.
goto end

:show_guide
echo.
echo 📖 QUICK SETUP GUIDE:
echo.
echo 🔧 REQUIREMENTS (✅ Already installed):
echo    ✅ Java JDK 17
echo    ✅ Android Studio
echo    ✅ Android SDK
echo    ✅ Game APK built
echo.
echo 🚀 TO PLAY:
echo    1. Choose option [1] to open Android Studio
echo    2. Wait for project to load and sync
echo    3. Create emulator: Tools → AVD Manager → Create Virtual Device
echo    4. Choose device: Pixel 6 or similar
echo    5. Download system image: API 28+ recommended
echo    6. Start emulator and wait for boot
echo    7. Click green Run button ▶️ in Android Studio
echo.
echo 🎮 GAME FLOW:
echo    1. Launch → See welcome screen
echo    2. Connect Wallet → Mock wallet appears
echo    3. Start Game → Enter battle arena
echo    4. Choose actions → Strategic combat
echo    5. Win/Lose → Blockchain recording
echo    6. Play Again → Endless fun!
echo.
goto end

:quit
echo.
echo 👋 Thanks for checking out the Blockchain Turn-Based Game!
echo 🎮 Your APK is ready at: app\build\outputs\apk\debug\app-debug.apk
exit /b 0

:end
echo.
echo 💡 Press any key to return to menu...
pause >nul
goto menu

:menu
cls
goto start