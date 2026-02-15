#!/usr/bin/env bash
set -e

# Формирует notifications/config.json и отправляет уведомление в Telegram
# (qa-guru/allure-notifications).
#
# Обязательные env: NOTIFY_PROJECT, NOTIFY_REPORT_LINK, NOTIFY_ALLURE_FOLDER
# Для Telegram: TG_TOKEN, CHAT_ID (если не заданы — уведомление пропускается)
# Опционально: NOTIFY_ENVIRONMENT, NOTIFY_COMMENT, NOTIFY_LANGUAGE (ru), NOTIFY_LOGO (путь к картинке)

NOTIFY_PROJECT="${NOTIFY_PROJECT:-}"
NOTIFY_ENVIRONMENT="${NOTIFY_ENVIRONMENT:-CI}"
NOTIFY_COMMENT="${NOTIFY_COMMENT:-}"
NOTIFY_REPORT_LINK="${NOTIFY_REPORT_LINK:-}"
NOTIFY_ALLURE_FOLDER="${NOTIFY_ALLURE_FOLDER:-}"
NOTIFY_LANGUAGE="${NOTIFY_LANGUAGE:-ru}"
NOTIFY_LOGO="${NOTIFY_LOGO:-}"
TG_TOKEN="${TG_TOKEN:-}"
CHAT_ID="${CHAT_ID:-}"

mkdir -p notifications

# Собираем config.json (через jq, если есть — корректное экранирование)
build_config() {
  if command -v jq &>/dev/null; then
    jq -n \
      --arg project "$NOTIFY_PROJECT" \
      --arg environment "$NOTIFY_ENVIRONMENT" \
      --arg comment "$NOTIFY_COMMENT" \
      --arg reportLink "$NOTIFY_REPORT_LINK" \
      --arg language "$NOTIFY_LANGUAGE" \
      --arg allureFolder "$NOTIFY_ALLURE_FOLDER" \
      --arg logo "$NOTIFY_LOGO" \
      --arg token "$TG_TOKEN" \
      --arg chat "$CHAT_ID" \
      '{
        base: {
          logo: $logo,
          project: $project,
          environment: $environment,
          comment: $comment,
          reportLink: $reportLink,
          language: $language,
          allureFolder: $allureFolder,
          enableChart: true
        },
        telegram: {
          token: $token,
          chat: $chat,
          replyTo: ""
        }
      }' > "notifications/config.json"
  else
    # Без jq: подставляем как есть (в значениях не должно быть кавычек и обратных слэшей)
    cat > "notifications/config.json" << EOF
{
  "base": {
    "logo": "$NOTIFY_LOGO",
    "project": "$NOTIFY_PROJECT",
    "environment": "$NOTIFY_ENVIRONMENT",
    "comment": "$NOTIFY_COMMENT",
    "reportLink": "$NOTIFY_REPORT_LINK",
    "language": "$NOTIFY_LANGUAGE",
    "allureFolder": "$NOTIFY_ALLURE_FOLDER",
    "enableChart": true
  },
  "telegram": {
    "token": "$TG_TOKEN",
    "chat": "$CHAT_ID",
    "replyTo": ""
  }
}
EOF
  fi
}

# Пропуск, если нет токена или чата
if [ -z "$TG_TOKEN" ] || [ -z "$CHAT_ID" ]; then
  echo "TG_TOKEN или CHAT_ID не заданы — уведомление в Telegram пропущено."
  exit 0
fi

# Пропуск, если не хватает данных для base
if [ -z "$NOTIFY_PROJECT" ] || [ -z "$NOTIFY_REPORT_LINK" ] || [ -z "$NOTIFY_ALLURE_FOLDER" ]; then
  echo "Не заданы NOTIFY_PROJECT, NOTIFY_REPORT_LINK или NOTIFY_ALLURE_FOLDER — уведомление пропущено."
  exit 0
fi

build_config

JAR=""
for f in notifications/allure-notifications*.jar; do
  [ -f "$f" ] && { JAR="$f"; break; }
done

if [ -f "notifications/config.json" ] && [ -n "$JAR" ]; then
  echo "Отправка уведомления в Telegram (JAR: $JAR)"
  java -DconfigFile=notifications/config.json -jar "$JAR"
else
  echo "Пропуск: нет config.json или JAR в notifications/"
fi
