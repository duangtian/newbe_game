# Blockchain Turn-Based Game 🎮⛓️

เกม Android แบบ Turn-Based ที่ใช้เทคโนโลยี Blockchain สำหรับบันทึกผลการเล่นและการจัดการ Wallet

## ✨ Features

### 🎯 Game Features
- **Turn-Based Combat System**: ระบบการต่อสู้แบบผลัดกันเล่น
- **Player vs AI**: เล่นกับ AI ที่มีการกระทำแบบสุ่ม
- **Action Types**: 
  - ⚔️ Attack (โจมตี): สร้างความเสียหาย 15-25 แต้ม
  - 🛡️ Defend (ป้องกัน): ลดความเสียหายครึ่งหนึ่ง + ฟื้นฟู HP 5 แต้ม
  - 💥 Special Attack (โจมตีพิเศษ): ความเสียหาย 25-35 แต้ม (จำกัด 3 ครั้ง)
- **Health System**: ระบบ HP เริ่มต้น 100 แต้ม
- **Battle Log**: บันทึกการต่อสู้แบบเรียลไทม์

### ⛓️ Blockchain Features
- **Wallet Integration**: เชื่อมต่อกับ Ethereum wallet
- **Game Result Recording**: บันทึกผลเกมลง blockchain
- **Transaction Tracking**: ติดตามสถานะธุรกรรม
- **Game History**: ดูประวัติการเล่นจาก blockchain
- **Mock Wallet**: ระบบ wallet จำลองสำหรับการทดสอบ

## 🛠️ Technology Stack

### Frontend (Android)
- **Language**: Kotlin
- **UI**: Android View System with ViewBinding
- **Architecture**: MVVM pattern
- **Coroutines**: สำหรับ async operations

### Blockchain
- **Web3j**: Java/Android library สำหรับ Ethereum
- **Network**: รองรับ Ethereum testnet (Sepolia) และ local blockchain
- **Wallet**: การจัดการ private key และ address

### Dependencies
```gradle
// Core Android
implementation 'androidx.core:core-ktx:1.10.1'
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'com.google.android.material:material:1.9.0'

// Web3 & Blockchain
implementation 'org.web3j:core:4.9.8'
implementation 'org.web3j:crypto:4.9.8'
implementation 'org.web3j:abi:4.9.8'

// JSON & Coroutines
implementation 'com.google.code.gson:gson:2.10.1'
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3'
```

## 📱 How to Play

### 1. เชื่อมต่อ Wallet
- กดปุ่ม "เชื่อมต่อ Wallet"
- ระบบจะสร้าง mock wallet ให้อัตโนมัติ (สำหรับ demo)
- ดู address และ balance ของ wallet

### 2. เริ่มเกม
- กดปุ่ม "เริ่มเกม" หลังจากเชื่อมต่อ wallet แล้ว
- เข้าสู่หน้าจอการต่อสู้

### 3. การเล่น
- **ตาของคุณ**: เลือกการกระทำ (Attack/Defend/Special Attack)
- **ตาของ AI**: AI จะเลือกการกระทำแบบสุ่ม
- **ชนะ**: เมื่อ HP ของฝ่ายตรงข้ามเป็น 0

### 4. บันทึกผลเกม
- เมื่อเกมจบ ระบบจะบันทึกผลลง blockchain อัตโนมัติ
- รับ transaction hash สำหรับการติดตาม
- เลือกเล่นใหม่หรือกลับหน้าหลัก

## 🔧 Setup & Installation

### Prerequisites
- Android Studio Arctic Fox หรือใหม่กว่า
- Android SDK API 24+ (Android 7.0)
- Java 8+

### Installation Steps
1. **Clone repository**
   ```bash
   git clone <repository-url>
   cd newbe_game
   ```

2. **Open in Android Studio**
   - เปิด Android Studio
   - เลือก "Open an existing project"
   - เลือกโฟลเดอร์ `newbe_game`

3. **Sync Gradle**
   - รอให้ Gradle sync เสร็จ
   - ดาวน์โหลด dependencies ทั้งหมด

4. **Build & Run**
   ```bash
   ./gradlew assembleDebug
   ./gradlew installDebug
   ```

## 🧪 Testing

### Mock Wallet
- ใช้ mock wallet สำหรับการทดสอบ
- Address: สุ่มสร้างใหม่ทุกครั้ง
- Balance: 1.5 ETH (จำลอง)

### Game Testing
- ทดสอบการต่อสู้ทุก action type
- ทดสอบการบันทึกผลเกม
- ทดสอบ UI responsiveness

## 📁 Project Structure

```
newbe_game/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/blockchainturnbasedgame/
│   │   │   ├── blockchain/          # Blockchain integration
│   │   │   │   ├── WalletManager.kt
│   │   │   │   └── BlockchainGameManager.kt
│   │   │   ├── game/                # Game logic
│   │   │   │   └── GameEngine.kt
│   │   │   ├── model/               # Data models
│   │   │   │   ├── Player.kt
│   │   │   │   └── GameAction.kt
│   │   │   ├── MainActivity.kt      # Main screen
│   │   │   └── GameActivity.kt      # Game screen
│   │   └── res/
│   │       ├── layout/              # UI layouts
│   │       ├── values/              # Strings, colors, themes
│   │       └── xml/                 # App configs
│   └── build.gradle                 # App dependencies
├── gradle/                          # Gradle wrapper
├── build.gradle                     # Project config
└── settings.gradle                  # Project settings
```

## 🔮 Future Enhancements

### Gameplay
- [ ] Multiplayer (P2P) mode
- [ ] Character classes ที่หลากหลาย
- [ ] Equipment system
- [ ] Tournament mode

### Blockchain
- [ ] Smart contract deployment
- [ ] NFT character integration
- [ ] Token rewards system
- [ ] Decentralized leaderboard

### Technical
- [ ] Real wallet integration (MetaMask)
- [ ] Gas fee optimization
- [ ] Offline mode support
- [ ] Advanced encryption

## 🐛 Known Issues

1. **Web3j Dependencies**: อาจมีปัญหาเรื่อง library conflicts
2. **Network Configuration**: ต้องตั้งค่า RPC endpoint ให้ถูกต้อง
3. **Testing**: Mock wallet อาจไม่ถูกต้องเหมือนจริง

## 📞 Support

หากมีปัญหาหรือข้อสงสัย:
1. ตรวจสอบ [Issues](../../issues) ใน repository
2. สร้าง issue ใหม่พร้อมรายละเอียด
3. รวม log files และ error messages

## 📄 License

MIT License - ดูไฟล์ LICENSE สำหรับรายละเอียด

---

**Made with ❤️ for blockchain gaming community**
