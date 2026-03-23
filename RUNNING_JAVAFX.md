# Running the Smart Hospital JavaFX Application

## The Problem
Initially, attempting to compile the JavaFX application directly using `javac` with a manual `--module-path` caused an error because the absolute paths to the JavaFX SDK on the computer were incorrect. Furthermore, even when it successfully launched, a completely blank white window appeared because:
1. `Main.java` was only generating the title and showing an empty screen, making no attempt to load the actual user interface files.
2. The UI files like `Login.fxml` and `style.css` were completely empty (0 bytes), containing no actual layout code.
3. The Java backend wasn't connected to the database to facilitate the login process.

## The Solution
To fix these issues, we did the following:
1. **Maven Build System**: Emphasized that the project is configured as a Maven project (`pom.xml`). You **do not** need to manually download or link JavaFX modules or database drivers (like PostgreSQL). Maven automatically fetches dependencies and compiles everything.
2. **UI Implementation**: Built a fully functional user interface for the login (`src/main/resources/views/Login.fxml`) styled with a modern aesthetic (`src/main/resources/styles/style.css`).
3. **Application Loading Logic**: Updated `Main.java` to use `FXMLLoader` so the application recognizes and displays the `Login.fxml` UI upon launching.
4. **Unified Database Authentication**: Hooked up `LoginController.java` to `UserDAO.java`. Now, when a person logs in, the controller queries the local PostgreSQL `hospitaldb` database using the credentials in `DatabaseConnection.java`, automatically finding out whether the user is an **Admin**, **Doctor**, or **Patient** without needing separate login screens.

## How to Run the App

### Option A: Without Installing Maven Globally
You can use the local version of Maven we downloaded into the workspace. Open a terminal in the project folder and run:
`.\apache-maven-3.9.6\bin\mvn.cmd clean compile javafx:run`

### Option B: With Maven Installed Globally
If your team members have Maven installed globally (verifiable by running `mvn -version`), they simply need to run the following in their terminal/command prompt:
`mvn clean compile javafx:run`
