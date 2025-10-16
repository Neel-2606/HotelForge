@echo off
echo ===================================
echo Hotel Management System - Build Script
echo ===================================

REM Set variables
set MYSQL_JAR=lib\mysql-connector-j-8.0.33.jar
set SRC_DIR=src
set BIN_DIR=bin
set MAIN_CLASS=com.hotel.main.HotelManagementApplication

REM Create bin directory if it doesn't exist
if not exist %BIN_DIR% mkdir %BIN_DIR%

echo.
echo [1/4] Cleaning previous build...
if exist %BIN_DIR%\* del /q %BIN_DIR%\*

echo.
echo [2/4] Compiling Java source files...
echo Classpath: %MYSQL_JAR%;%SRC_DIR%

REM Compile all Java files
javac -cp "%MYSQL_JAR%;%SRC_DIR%" -d %BIN_DIR% -sourcepath %SRC_DIR% %SRC_DIR%\com\hotel\database\*.java %SRC_DIR%\com\hotel\models\*.java %SRC_DIR%\com\hotel\services\*.java %SRC_DIR%\com\hotel\exceptions\*.java %SRC_DIR%\com\hotel\managers\*.java %SRC_DIR%\com\hotel\ui\*.java %SRC_DIR%\com\hotel\main\*.java %SRC_DIR%\main\model\*.java %SRC_DIR%\main\dao\*.java %SRC_DIR%\main\ui\*.java %SRC_DIR%\main\*.java

if %ERRORLEVEL% neq 0 (
    echo.
    echo [ERROR] Compilation failed!
    echo Please check the error messages above.
    pause
    exit /b 1
)

echo [3/4] Compilation successful!

echo.
echo [4/4] Running Hotel Management System...
echo Main class: %MAIN_CLASS%
echo.

REM Run the application
java -cp "%MYSQL_JAR%;%BIN_DIR%" %MAIN_CLASS%

if %ERRORLEVEL% neq 0 (
    echo.
    echo [ERROR] Application failed to start!
    echo Please check the error messages above.
    pause
    exit /b 1
)

echo.
echo Application finished.
pause
