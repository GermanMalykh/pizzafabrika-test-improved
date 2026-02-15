#!/usr/bin/env bash
set -e

# Ждём, пока эмулятор полностью загрузится
echo "Waiting for emulator to boot..."
adb wait-for-device
while [ "$(adb shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')" != "1" ]; do
  echo "  Boot in progress..."
  sleep 5
done
echo "Emulator ready."

# Запускаем только mobile-тесты
./gradlew test --no-daemon -PmobileOnly
