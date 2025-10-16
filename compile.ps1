Write-Host "====================================" -ForegroundColor Green
Write-Host "Hotel Management System - PowerShell Build Script" -ForegroundColor Green
Write-Host "====================================" -ForegroundColor Green

# Set variables
$MYSQL_JAR = "lib\mysql-connector-j-8.0.33.jar"
$SRC_DIR = "src"
$BIN_DIR = "bin"
$MAIN_CLASS = "com.hotel.main.HotelManagementApplication"

# Create bin directory if it doesn't exist
if (!(Test-Path $BIN_DIR)) {
    New-Item -ItemType Directory -Path $BIN_DIR
}

Write-Host ""
Write-Host "[1/4] Cleaning previous build..." -ForegroundColor Yellow
if (Test-Path "$BIN_DIR\*") {
    Remove-Item "$BIN_DIR\*" -Recurse -Force
}

Write-Host ""
Write-Host "[2/4] Compiling Java source files..." -ForegroundColor Yellow
Write-Host "Classpath: $MYSQL_JAR;$SRC_DIR"

# Find all Java files
$javaFiles = @(
    Get-ChildItem -Path "$SRC_DIR\com\hotel\database\*.java" -ErrorAction SilentlyContinue
    Get-ChildItem -Path "$SRC_DIR\com\hotel\models\*.java" -ErrorAction SilentlyContinue
    Get-ChildItem -Path "$SRC_DIR\com\hotel\services\*.java" -ErrorAction SilentlyContinue
    Get-ChildItem -Path "$SRC_DIR\com\hotel\exceptions\*.java" -ErrorAction SilentlyContinue
    Get-ChildItem -Path "$SRC_DIR\com\hotel\managers\*.java" -ErrorAction SilentlyContinue
    Get-ChildItem -Path "$SRC_DIR\com\hotel\ui\*.java" -ErrorAction SilentlyContinue
    Get-ChildItem -Path "$SRC_DIR\com\hotel\main\*.java" -ErrorAction SilentlyContinue
    Get-ChildItem -Path "$SRC_DIR\main\model\*.java" -ErrorAction SilentlyContinue
    Get-ChildItem -Path "$SRC_DIR\main\dao\*.java" -ErrorAction SilentlyContinue
    Get-ChildItem -Path "$SRC_DIR\main\ui\*.java" -ErrorAction SilentlyContinue
    Get-ChildItem -Path "$SRC_DIR\main\*.java" -ErrorAction SilentlyContinue
)

$javaFilesList = ($javaFiles | ForEach-Object { $_.FullName }) -join " "

# Compile
$compileCommand = "javac -cp `"$MYSQL_JAR;$SRC_DIR`" -d $BIN_DIR -sourcepath $SRC_DIR $javaFilesList"
Write-Host "Executing: $compileCommand"

try {
    Invoke-Expression $compileCommand
    if ($LASTEXITCODE -ne 0) {
        throw "Compilation failed with exit code $LASTEXITCODE"
    }
    Write-Host "[3/4] Compilation successful!" -ForegroundColor Green
} catch {
    Write-Host "[ERROR] Compilation failed!" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host ""
Write-Host "[4/4] Running Hotel Management System..." -ForegroundColor Yellow
Write-Host "Main class: $MAIN_CLASS"
Write-Host ""

# Run the application
try {
    java -cp "$MYSQL_JAR;$BIN_DIR" $MAIN_CLASS
    if ($LASTEXITCODE -ne 0) {
        throw "Application failed with exit code $LASTEXITCODE"
    }
} catch {
    Write-Host "[ERROR] Application failed to start!" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host ""
Write-Host "Application finished." -ForegroundColor Green
Read-Host "Press Enter to exit"
