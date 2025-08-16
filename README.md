# ♟️ AsciiChess

A fully-featured ASCII chess game implemented in Java with terminal-based graphics and interactive gameplay.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

## 📖 Overview

AsciiChess is a terminal-based chess game that brings the classic game of chess to your command line. Built with Java 21 and featuring beautiful ASCII art, this implementation provides a complete chess experience with:

- ✨ Beautiful ASCII art interface
- 🎮 Interactive keyboard controls
- ♟️ Full chess piece movement validation
- 🤖 AI opponent (coming soon)
- 🎨 Colorized terminal output
- ⌨️ Cross-platform terminal support

## 🎯 Features

- **Complete Chess Implementation**: All standard chess pieces with proper movement rules
- **Interactive Gameplay**: Navigate the board using keyboard controls
- **Visual Board Display**: Clean ASCII representation of the chess board
- **Move Validation**: Ensures all moves follow chess rules
- **Cross-Platform**: Works on Windows, macOS, and Linux terminals
- **Modern Java**: Built with Java 21 and modern development practices

## 📸 Screenshots

### Game Interface
![Game Interface](https://user-images.githubusercontent.com/50636048/226130464-f6a0348f-e89c-4033-8be2-1ae4cb096e4a.png)

### Board View
![Board View](https://user-images.githubusercontent.com/50636048/226129537-7f163216-235a-43b6-966b-4aa7b80b7bea.png)

### Piece Selection
![Piece Selection](https://user-images.githubusercontent.com/50636048/226129549-d113c58c-7bff-4ff7-971d-388edee4e1e9.png)

### Move Highlighting
![Move Highlighting](https://user-images.githubusercontent.com/50636048/226129551-0ace4ecd-30d9-4544-b882-adaafb28e904.png)

### Gameplay
![Gameplay](https://user-images.githubusercontent.com/50636048/226129552-1db5f3bc-20ad-4fe6-a451-0e1adb7f2df9.png)

![Game Progress](https://user-images.githubusercontent.com/50636048/226129555-a7d54f8d-c7f5-4cf3-96f8-63efb49ee560.png)

### Demo Video
https://user-images.githubusercontent.com/50636048/226129556-8cacc601-6feb-4071-9b0e-77bc13042c67.mp4

## 🚀 Getting Started

### Prerequisites

- **Java 21** or higher
- **Maven 3.6+** for building
- A terminal that supports ANSI colors (most modern terminals)

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/AsciiChess.git
   cd AsciiChess
   ```

2. **Build the project**:
   ```bash
   mvn clean compile
   ```

3. **Run the game**:
   ```bash
   mvn exec:java -Dexec.mainClass="org.ascii.Main"
   ```

   Or build a JAR file:
   ```bash
   mvn assembly:single
   java -jar target/AsciiChess-1.0-SNAPSHOT-jar-with-dependencies.jar
   ```

## 🎮 How to Play

### Controls

- **Arrow Keys**: Navigate the board cursor
- **Enter/Space**: Select piece or confirm move
- **ESC**: Cancel current selection
- **Q**: Quit game

### Gameplay

1. Start the game and you'll see the chess board
2. Use arrow keys to move the cursor to the piece you want to move
3. Press Enter to select the piece
4. Move the cursor to the destination square
5. Press Enter to make the move
6. The game alternates between white and black players

## 🏗️ Project Structure

```
src/main/java/org/ascii/
├── Main.java                 # Entry point and game initialization
├── chess/
│   ├── ai/
│   │   └── KingsMovement.java # AI movement logic
│   ├── board/
│   │   ├── ChessBoard.java   # Board representation
│   │   ├── ChessBox.java     # Individual board squares
│   │   └── Game.java         # Game logic and state
│   ├── pieces/
│   │   ├── Bishop.java       # Bishop piece implementation
│   │   ├── ChessPieceType.java # Piece type enumeration
│   │   ├── ChessToken.java   # Base piece class
│   │   ├── King.java         # King piece implementation
│   │   ├── Knight.java       # Knight piece implementation
│   │   ├── Pawn.java         # Pawn piece implementation
│   │   ├── Queen.java        # Queen piece implementation
│   │   └── Rook.java         # Rook piece implementation
│   └── util/
│       ├── Colors.java       # Terminal color constants
│       ├── Cursor.java       # Cursor management
│       ├── Data.java         # Game data structures
│       ├── Directions.java   # Movement directions
│       ├── Display.java      # Display utilities
│       ├── Key.java          # Keyboard input constants
│       ├── KeyBoardInput.java # Input handling
│       ├── MovementRecord.java # Move history
│       ├── Movements.java    # Movement validation
│       ├── Path.java         # Path finding for pieces
│       ├── Players.java      # Player management
│       ├── Point.java        # Board coordinates
│       └── Text.java         # Text utilities
```

## 🛠️ Dependencies

- **JLine 3.23.0**: Terminal control and input handling
- **JNA 5.12.1**: Native library access for terminal operations
- **Jansi 2.4.0**: ANSI escape sequence support

## 🤝 Contributing

Contributions are welcome! Here's how you can help:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes
4. Add tests if applicable
5. Commit your changes (`git commit -m 'Add amazing feature'`)
6. Push to the branch (`git push origin feature/amazing-feature`)
7. Open a Pull Request

### Development Setup

```bash
# Clone your fork
git clone https://github.com/yourusername/AsciiChess.git
cd AsciiChess

# Build and test
mvn clean compile
mvn test

# Run the game
mvn exec:java -Dexec.mainClass="org.ascii.Main"
```

## 🔮 Future Features

- [ ] AI opponent with difficulty levels
- [ ] Save/load game functionality
- [ ] Network multiplayer support
- [ ] Move history and replay
- [ ] Chess notation support (PGN)
- [ ] Tournament mode
- [ ] Customizable themes
- [ ] Sound effects

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Hilal** - [GitHub Profile](https://github.com/yourusername)

## 🙏 Acknowledgments

- Thanks to the JLine team for excellent terminal control
- Chess piece movement algorithms inspired by classic chess implementations
- ASCII art generated with online tools

## 📞 Support

If you encounter any issues or have questions:

1. Check the [Issues](https://github.com/yourusername/AsciiChess/issues) page
2. Create a new issue with detailed information
3. Join the discussion in existing issues

---

⭐ **If you enjoyed this project, please give it a star!** ⭐

