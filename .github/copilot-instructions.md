<!--
This file guides AI coding agents (Copilot-like) to be productive in this repository.
Repository scan in this environment found no source files — this is a template
tailored for a Java + Selenium + Cucumber + Appium test project (based on the
workspace name). Please update placeholders below after syncing the repo so
the agent can reference concrete paths and files.
-->

# Copilot / AI Agent Instructions (project-specific)

Purpose: enable an AI coding assistant to make safe, focused changes to the
test automation codebase and to suggest reproducible developer workflows.

Key assumptions (update if inaccurate):
- Language: Java
- Test frameworks: Cucumber (feature files), JUnit/TestNG runners
- Automation: Selenium WebDriver for web, Appium for mobile
- Build: Maven (`pom.xml`) or Gradle (`build.gradle` / `gradlew`)

What to look for first
- If present, open these top-level files and folders and treat them as anchors:
  - `pom.xml` or `build.gradle` — project build and test command references
  - `src/test/resources/features` — Cucumber `.feature` files (entry points)
  - `src/test/java` — step definitions, runners, hooks
  - `src/main/java` or `lib` — shared helpers, Page Objects, driver factories
  - `src/test/resources/config` or `config` — environment capability files

Big-picture architecture (how to reason about changes)
- Tests flow: Feature file -> Step Definitions -> Page Objects / Service objects -> Driver (Selenium/Appium)
- Keep changes near the area of responsibility (UI selector changes belong in Page Objects; scenario flow in Step Definitions; capabilities in config files)
- Driver creation and remote endpoints are centralized (look for classes named `DriverFactory`, `WebDriverManager`, `AppiumDriverFactory`, or `RemoteDriver`).

Common repo conventions to follow
- Naming: `*Page` for page objects, `*Steps` for step definition classes, `*Runner` for test runners.
- Feature files live under `src/test/resources/features` and are referenced by runner annotations or `cucumber.options`.
- Environment and device capabilities are injected via properties or a JSON/YAML file read by `capabilities` loader classes.
- CI runs use non-interactive flags; prefer `mvn -q -DskipITs=false test` style commands when adding examples.

Developer workflows (examples to keep or update)
- Run full test suite (Maven):
  `mvn clean test`
- Run full test suite (Gradle):
  `./gradlew clean test`
- Run a single Cucumber feature (example):
  `mvn -Dtest=RunnerTest -Dcucumber.options="classpath:features/my_feature.feature" test`
- Run with device/emulator (example properties — repository may differ):
  `mvn test -Dplatform=android -DdeviceName=emulator-5554 -DappiumHost=localhost -DappiumPort=4723`
- Start Appium locally before mobile runs: `appium --port 4723` (or use `appium` desktop app)

Integration points and external dependencies the agent should not break
- Appium Server: host/port typically referenced by env vars like `APPIUM_HOST`/`APPIUM_PORT`
- Cloud device providers: SauceLabs/BrowserStack config often passed via `-D` properties
- Android / iOS SDKs: `ANDROID_HOME`, `ANDROID_SDK_ROOT`, and Xcode tooling for iOS

Safe modification rules for AI agents (repository-specific)
- Do not change capability files or CI configs without creating a matching test run example.
- When updating selectors, change only the Page Object method that owns them and run the related feature locally.
- Preserve existing step definitions and runners; add new ones instead of editing existing scenario mappings unless the change is backward compatible.

If you add or change tests, include runnable commands and environment variables in the commit message and update `README.md`.

Troubleshooting hints (where to look)
- Failing mobile tests: check Appium logs and device/emulator availability; confirm `adb devices` or `xcrun simctl list`.
- Failing web tests: check remote WebDriver URL and browser driver versions, and CI browser matrix.

What I need from you (to make these instructions precise)
- Push the repository contents or point me to a commit so I can replace placeholders with concrete file references.
- If this project uses Gradle vs Maven, tell me which one to prefer for commands.

After you sync the repo I will:
1. Re-scan the project and merge any existing `.github/copilot-instructions.md` content.
2. Replace placeholders with real file paths and concrete examples found in the code.
3. Submit a concise 20–50 line final version and ask for review.

--
Update this file to reflect the real repository layout once the sources are available.
