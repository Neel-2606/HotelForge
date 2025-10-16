@echo off
echo ===================================
echo Hotel Management System - Database Setup
echo ===================================

echo.
echo This script will set up the database for the Hotel Management System.
echo.
echo Prerequisites:
echo 1. MySQL Server must be installed and running
echo 2. You need MySQL root access or appropriate privileges
echo.

set /p MYSQL_USER="Enter MySQL username (default: root): "
if "%MYSQL_USER%"=="" set MYSQL_USER=root

set /p MYSQL_PASSWORD="Enter MySQL password: "

echo.
echo Setting up database with user: %MYSQL_USER%
echo.

REM Execute the database setup script
mysql -u %MYSQL_USER% -p%MYSQL_PASSWORD% < database\schema.sql

if %ERRORLEVEL% neq 0 (
    echo.
    echo [ERROR] Database setup failed!
    echo Please check:
    echo 1. MySQL server is running
    echo 2. Username and password are correct
    echo 3. You have sufficient privileges
    pause
    exit /b 1
)

echo.
echo [SUCCESS] Database setup completed successfully!
echo.
echo Database: hotel_management
echo Tables created:
echo - users (for authentication)
echo - rooms (for room management)
echo - bookings (for booking management)
echo.
echo Sample data has been inserted for testing.
echo.
echo Default admin login:
echo Username: admin
echo Password: admin123
echo.
pause
