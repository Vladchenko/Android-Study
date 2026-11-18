Project is implemented taking the course from a web-site startandroid.ru.
Stopped at 17th lesson.

## Build Requirements

- **JDK 21** (required)
- **Android Studio** with latest SDK
- **Gradle 8.9** (managed by wrapper)

### Setup

1. Open project in Android Studio
2. Gradle will auto-sync and download dependencies
3. If build fails with JDK version error, set system `JAVA_HOME` to JDK 21:
   - Windows: `setx JAVA_HOME "C:\Program Files\Java\jdk-21.0.12.1"`
   - macOS/Linux: `export JAVA_HOME=/path/to/jdk-21`
4. Or override in `gradle.properties`:
   ```
   org.gradle.java.home=/path/to/jdk-21
   ```