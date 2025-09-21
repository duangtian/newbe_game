# 🚀 Quick Setup Guide for Blockchain Turn-Based Game

## Current Status ✅
- ✅ Java JDK 17 installed and working
- ⏳ Android Studio is downloading and installing...

## Next Steps

### 1. After Android Studio Installation Completes:

1. **Open Android Studio**
2. **Choose "Open an existing project"**
3. **Select this folder:** `c:\Users\pepsi\Documents\WorkSpace\newbe_game`
4. **Let Android Studio download Android SDK** (it will prompt you)
5. **Accept all licenses** when prompted
6. **Wait for Gradle sync to complete**

### 2. Running the Game:

#### Option A: Using Android Studio (Recommended)
1. **Connect an Android device** via USB (enable Developer Options & USB Debugging)
   OR
2. **Create an Android emulator:**
   - Tools → AVD Manager
   - Create Virtual Device
   - Choose a device (e.g., Pixel 6)
   - Download a system image (API 28+ recommended)

3. **Click the "Run" button** (green triangle) in Android Studio

#### Option B: Using Command Line
Once Android Studio is installed and SDK is configured:
```powershell
# Set Android SDK path (replace with actual path)
$env:ANDROID_HOME = "C:\Users\$env:USERNAME\AppData\Local\Android\Sdk"
$env:PATH = "$env:ANDROID_HOME\platform-tools;$env:PATH"

# Build the project
.\gradlew.bat assembleDebug

# Install on connected device
.\gradlew.bat installDebug
```

## 🎮 Game Features to Test:

### 1. Main Screen:
- ✅ Click "เชื่อมต่อ Wallet" (Connect Wallet)
- ✅ See mock wallet address and balance
- ✅ Click "เริ่มเกม" (Start Game)

### 2. Game Screen:
- ⚔️ **Attack**: Deals 15-25 damage
- 🛡️ **Defend**: Reduces incoming damage by half + heals 5 HP
- 💥 **Special Attack**: Deals 25-35 damage (limited to 3 uses)

### 3. AI Opponent:
- AI will automatically choose random actions
- Battle log shows all actions in real-time
- Game ends when one player reaches 0 HP

### 4. Blockchain Features:
- Game results are automatically recorded to blockchain (simulated)
- Transaction hash is generated for tracking
- Mock wallet system for testing

## 🔧 Troubleshooting:

### If you get "SDK not found" errors:
1. Open Android Studio
2. Go to File → Settings → Appearance & Behavior → System Settings → Android SDK
3. Note the SDK Location path
4. Create `local.properties` file in project root with:
   ```
   sdk.dir=C\\:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
   ```

### If Gradle sync fails:
1. File → Invalidate Caches and Restart
2. Build → Clean Project
3. Build → Rebuild Project

## 🎯 What You'll See:

1. **Splash Screen**: "Blockchain Turn-Based Game"
2. **Wallet Connection**: Mock wallet with 1.5 ETH balance
3. **Battle Interface**: Player 1 vs Player 2 (AI)
4. **Real-time Combat**: Turn-based with visual feedback
5. **Game Over**: Winner announcement + blockchain recording
6. **Play Again**: Option to restart or return to main menu

## 📱 System Requirements:
- ✅ Windows 10/11
- ✅ Java JDK 17 (installed)
- ⏳ Android Studio (installing)
- 📱 Android device/emulator with API 24+ (Android 7.0+)

The game is ready to run as soon as Android Studio installation completes! 🎮⛓️