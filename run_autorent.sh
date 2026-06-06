#!/usr/bin/env bash
cd "$(dirname "$0")"
echo "🔧 Building (skip tests)…"
mvn clean package -DskipTests
if [[ $? -ne 0 ]]; then echo "❌ Build failed"; exit 1; fi
JAR=$(ls target/AutoRentWeb-*.jar | head -n1)
nohup java -jar "$JAR" > autorent.log 2>&1 &
APP_PID=$!
sleep 3
open http://localhost:8080
echo "✅ Server PID $APP_PID – tail logs with: tail -f autorent.log"
wait $APP_PID
