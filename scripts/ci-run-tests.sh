#!/usr/bin/env bash
set -e

# Ждём, пока эмулятор полностью загрузится (ADB может быть готов раньше UI)
echo "Waiting for emulator to boot..."
adb wait-for-device
until adb shell getprop sys.boot_completed 2>/dev/null | grep -m 1 "1"; do
  echo "  Boot in progress..."
  sleep 2
done
echo "Emulator ready."

# Appium 2/3 в CI использует base path / (не /wd/hub)
./gradlew test --no-daemon -PmobileOnly -Dmobile.appium.url=http://localhost:4723
