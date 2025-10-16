@echo off
echo ===================================
echo Hotel Management System - Run Script
echo ===================================

REM Set variables
set MYSQL_JAR=lib\mysql-connector-j-8.0.33.jar
set OUT_DIR=out
set SRC_DIR=src
set LOGIN_DIR=Login\main\java

echo.
echo [1/2] Compiling Java source files...

REM Create output directory if it doesn't exist
if not exist %OUT_DIR% mkdir %OUT_DIR%

REM Copy properties files
xcopy /y %LOGIN_DIR%\*.properties %OUT_DIR%\

REM Compile Login module
javac -cp "%MYSQL_JAR%" -d %OUT_DIR% %LOGIN_DIR%\utils\*.java %LOGIN_DIR%\models\*.java %LOGIN_DIR%\services\*.java %LOGIN_DIR%\ui\*.java

REM Compile hotel modules
javac -cp "%MYSQL_JAR%;%OUT_DIR%" -d %OUT_DIR% %SRC_DIR%\com\hotel\database\*.java %SRC_DIR%\com\hotel\models\*.java %SRC_DIR%\com\hotel\managers\*.java %SRC_DIR%\com\hotel\ui\*.java %SRC_DIR%\com\hotel\exceptions\*.java %SRC_DIR%\com\hotel\main\*.java %SRC_DIR%\com\hotel\dao\*.java

echo.
echo [2/2] Running Hotel Management System...
echo.

REM Run the application
java -cp "%MYSQL_JAR%;%OUT_DIR%" com.hotel.main.Main

pause