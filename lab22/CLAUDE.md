# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Проект

Учебное Android-приложение «Заметки» (lab22). Один модуль `:app`, Java (не Kotlin), XML-лейауты (не Compose), пакет `com.example.lab22`.

Стек: AGP 9.4.1, compileSdk/targetSdk 37, minSdk 26, Java 11. Версии зависимостей — в `gradle/libs.versions.toml` (version catalog, обращение через `libs.*`). Включён configuration cache (`gradle.properties`).

## Команды

Windows — использовать `./gradlew` из Git Bash или `.\gradlew.bat` из PowerShell.

```
./gradlew assembleDebug                 # сборка debug APK
./gradlew installDebug                  # установка на подключённое устройство/эмулятор
./gradlew test                          # unit-тесты (app/src/test)
./gradlew connectedAndroidTest          # инструментальные тесты (нужно устройство)
./gradlew lint                          # Android Lint
./gradlew :app:testDebugUnitTest --tests "com.example.lab22.ExampleUnitTest"   # один тест-класс
```

Тесты сейчас только шаблонные (`ExampleUnitTest`, `ExampleInstrumentedTest`).

## Архитектура

- `MainActivity` — список заметок в `RecyclerView`. Данные захардкожены в `loadData()` и хранятся в поле `List<Note>` в памяти; персистентности (Room/SharedPreferences/файлы) нет.
- `adapter/NoteAdapter` + `holder/NoteViewHolder` — привязка `Note` к `item_note.xml`. ViewHolder отдаёт `TextView` как public-поля.
- `model/Note` — POJO (`id`, `title`, `content`, `createdAt: Instant`).
- `NoteActivity` + `activity_note.xml` — экран редактирования (поля заголовка/текста, кнопки «Сохранить»/«Удалить»). Зарегистрирована в манифесте (`exported=false`), но логики пока нет и никто её не запускает. Кнопка `addNote` в `activity_main.xml` тоже не подключена.
- Обе Activity используют `EdgeToEdge.enable()` + обработчик insets на корневом view с `id="main"` — при новых лейаутах сохранять этот id у корня.

## Подводные камни

- Порядок аргументов конструктора `Note(String content, int id, String title)` — **content первым**, title последним. В `loadData()` строки вида `new Note("Почистить зубы", 1, "Утро")` дают title = «Утро».
- `createdAt` не заполняется в конструкторе, а `NoteAdapter.onBindViewHolder` вызывает `note.getCreatedAt().atZone(...)` без проверки на null — при текущих данных это NPE.
- R8 keep-правила лежат в `app/src/main/keepRules/` (новый формат AGP 9), а не в `proguard-rules.pro`. Оптимизация в release выключена (`optimization { enable = false }`).
- RecyclerView явно в зависимостях не объявлен — приходит транзитивно через `material`.
- Строки UI захардкожены в лейаутах на русском, а не в `strings.xml`.
