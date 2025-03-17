# 🕹️ Quest Game

Quest Game is a text-based adventure where players navigate through different scenes, making choices that determine the story’s progression. The game is built using Java Servlets and a simple front-end interface.

## 🚀 Features

- 🌍 Dynamic scene transitions based on player choices
- 📝 User session management to track progress
- 📊 Statistics tracking (correct and incorrect moves)
- 🛠️ REST API for game logic handling
- 🎨 Retro-inspired design with interactive UI
- ✅ Comprehensive unit and integration tests

## 📂 Project Structure

```
📦 Quest-Game
├── 📁 src
│   ├── 📁 main
│   │   ├── 📁 java
│   │   │   ├── 📁 controller         # Servlets for handling HTTP requests
│   │   │   ├── 📁 model              # Game models (Player, Game, Choices)
│   │   │   ├── 📁 repository         # Stores game data (scenes, choices)
│   │   │   ├── 📁 service            # Business logic implementation
│   │   │   ├── 📁 service.session    # Manages user game sessions
│   │   │   ├── 📁 util               # Utility classes
│   ├── 📁 test
│   │   ├── 📁 java
│   │   │   ├── 📁 controller         # Tests for servlets
│   │   │   ├── 📁 service            # Tests for service logic
│   │   │   ├── 📁 session            # Tests for game session management
├── 📁 webapp
│   ├── 📁 images             # Game assets and background images
│   ├── 📁 css                # Front-end styling
│   ├── 📁 js                 # Client-side scripts
│   ├── 📝 index.html         # Main game page
├── 📝 pom.xml                # Maven dependencies
└── 📝 README.md              # Documentation
```

## 🔧 Installation & Setup

### 🛠 Requirements

- **JDK 17+**  
- **Apache Tomcat 10+**  
- **Maven**  

### 🚀 Running the Game

1. **Clone the repository**
   ```sh
   https://github.com/CrapMind/GameWithServlets.git
   ```

3. **Build the project using Maven**
   ```sh
   mvn clean package
   ```

4. **Deploy the application to Tomcat**  
   - Move `target/GameWithServlets.war` to the `webapps` directory of Tomcat  
   - Start the server:  
     ```sh
     cd apache-tomcat/bin
     ./startup.sh (Linux/macOS)
     startup.bat (Windows)
     ```

5. **Open the game in a browser**  
   ```
   http://localhost:8080/
   ```

## 📡 API Endpoints

| Method | Endpoint              | Description                          |
|--------|-----------------------|--------------------------------------|
| `GET`  | `/rest/scene?scene=X` | Retrieves the text of scene X       |
| `GET`  | `/rest/move`          | Processes a move based on choice    |
| `POST` | `/rest/setPlayerName` | Sets the player's name              |
| `GET`  | `/rest/newGame`       | Starts a new game session           |

## 🧪 Testing

The project includes unit and integration tests to ensure correctness.

### Running Tests

To execute tests, run:
```sh
mvn test
```

### Testing Strategy

- **Unit Tests:** Cover individual components (`MovingService`, `GameSession`, `Repository`).
- **Servlet Tests:** Use `Mockito` to mock HTTP requests and responses.
- **Integration Tests:** Simulate full game sessions.
- **Parameterized Tests:** Ensure a variety of input scenarios are tested.

## 💡 Contributing
Pull requests are welcome! If you'd like to contribute, please open an issue first to discuss the proposed changes.

## 🤝 Contact
For any inquiries, reach out via GitHub Issues or email: crapmindd@gmail.com

