# QA Engineer Technical Test — Project 

This repository is a starter template for the QA Engineer technical test. It contains a minimal Java + Cucumber project skeleton for:

- Web UI tests (Selenium)
- Android UI tests (Appium)
- API tests (REST-assured + Cucumber)

Prerequisites
- Java JDK 11 or later installed and `JAVA_HOME` set
- Maven 3.6+ installed (`mvn` on PATH)
- For Web tests: a matching browser (Chrome) and ChromeDriver available on PATH (or use WebDriverManager)
- For Android tests: Android SDK, `adb`, and Appium server installed and running
- Appium (if running mobile tests) — install globally with `npm i -g appium` or use Appium Desktop

Quick setup (macOS / zsh)
```bash
# install maven if needed (Homebrew)
brew install maven

# start Appium (if running mobile tests)
appium --port 4723 &
```

Running tests from the command line

Notes about tag filtering:
- Recommended property for Cucumber 7+ is `cucumber.filter.tags`. You can also use `cucumber.options` (the project forwards this to the JVM), but `cucumber.filter.tags` is clearer and less error-prone.

- Run only `@web` scenarios:
```bash
mvn -Dcucumber.filter.tags="@web" test
```

- Run only `@buy` scenarios (Android buy flow):
```bash
# ensure Appium + emulator/device are running
mvn -Dcucumber.filter.tags="@buy" -Dapp="/path/to/mda-2.2.0-25.apk" -DdeviceName="Android Emulator" test
```

- Exclude a tag (e.g., run `@web` but not `@slow`):
```bash
mvn -Dcucumber.filter.tags="@web and not @slow" test
```

Headed vs Headless browser (web tests)
- The Selenium `DriverFactory` reads the system property `browser.headless` (default `true`). To run headed (visible) Chrome:
```bash
mvn -Dbrowser.headless=false -Dcucumber.filter.tags="@web" test
```
To run headless (default):
```bash
mvn -Dcucumber.filter.tags="@web" test
```

Appium / Android notes
- Default Appium URL: `http://127.0.0.1:4723/` (override with `-Dappium.url`)
- Default APK path: override with `-Dapp` (example above)
- Other Appium overrides supported by `AppiumDriverFactory`:
  - `-DplatformName` (default `Android`)
  - `-DautomationName` (default `UiAutomator2`)
  - `-DdeviceName` (default `Android Emulator`)
  - `-DappPackage` and `-DappActivity` if you need to override the launch activity

Selectors and code structure
- Selectors are centralized under `src/main/java/com/example/qa/selectors/` (e.g., `LoginSelectors`, `ProductsSelectors`, `CartSelectors`, `CheckoutSelectors`). Update those files when accessibility ids or resource-ids change — page objects use these constants.
- Page objects are under `src/main/java/com/example/qa/pages/` and step definitions under `src/test/java/com/example/qa/steps/`.
- The test flow stores the selected color in a small `TestContext` (`src/main/java/com/example/qa/context/TestContext.java`) so the cart can verify the same color after navigation. `TestContext` is cleared in the `@After` hook.

Debugging and artifacts
- On failures that involve UI mismatches (color/locator issues) the test will save artifacts under `target/test-artifacts/`:
  - `pagesource-<timestamp>.xml` — saved page source for inspection
  - `screenshot-<timestamp>.png` — screen capture
- The test runner prints diagnostic properties at JVM startup (see `TestRunner`) — look for lines such as:
  - `[DEBUG] cucumber.options=...`
  - `[DEBUG] cucumber.filter.tags=...`

Common commands
```bash
# run web tests (headed)
mvn -Dbrowser.headless=false -Dcucumber.filter.tags="@web" test

# run android buy scenario
mvn -Dcucumber.filter.tags="@buy" -Dapp="/path/to/mda-2.2.0-25.apk" -DdeviceName="Android Emulator" test

# run api tests
mvn -Dcucumber.filter.tags="@api" test
```

Project layout

- `pom.xml` — Maven build and dependencies
- `src/test/resources/features` — Cucumber feature files (`@web`, `@android`, `@api` tags)
- `src/test/java/com/example/qa/runner` — Cucumber JUnit runner (`TestRunner`)
- `src/test/java/com/example/qa/steps` — step definitions
- `src/main/java/com/example/qa/pages` — Page Objects
- `src/main/java/com/example/qa/selectors` — centralized selector constants
- `src/main/java/com/example/qa/driver` — driver factories (Selenium / Appium)

How to customize
1. Update selectors in `src/main/java/com/example/qa/selectors` if resource-ids/accessibility ids change.
2. Update capabilities in `AppiumDriverFactory` or pass overrides with `-D...` when running.
3. If you want automatic ChromeDriver management, integrate `io.github.bonigarcia:webdrivermanager` and remove the manual ChromeDriver requirement.

If you'd like, I can also:
- Add a short `mvn` profile for common runs (e.g., `profile:web`),
- Add a GitHub Actions workflow to run `@web` smoke tests on push,
- Or convert the selector constants from `By` objects to simple strings if you prefer building locators at call-sites.

---
Quick support: run one of the commands above and paste the first 40 lines of Maven output if things look wrong — I can interpret the debug prints and help tune selectors or properties.
# QA Engineer Technical Test — Project Template

This repository is a starter template for the QA Engineer technical test. It contains a minimal Java + Cucumber project skeleton for:

- Web UI tests (Selenium)
- Android UI tests (Appium)
- API tests (REST-assured + Cucumber)

Prerequisites
- Java JDK 11 or later installed and `JAVA_HOME` set
- Maven 3.6+ installed (`mvn` on PATH)
- For Web tests: a matching browser driver (ChromeDriver/GeckoDriver) or use WebDriverManager
- For Android tests: Android SDK, `adb`, and Appium server installed and running
- Appium (if running mobile tests) — install globally with `npm i -g appium` or use Appium Desktop

Recommended quick setup (macOS / zsh)
```bash
# install maven if needed (Homebrew)
brew install maven

# start Appium (if running mobile):
appium --port 4723 &

# run web tests (example):
mvn -Dcucumber.options.tags="@web" test

# by default browser will headless to inactive add some command
mvn -Dcucumber.options.tags="@web" -Dbrowser.headless=false test

# run api tests (example):
mvn -Dcucumber.options.tags="@api" test

 # run only android tests (example, requires emulator/device + Appium):
 
 ```bash
 # start Appium (if not running)
 appium --port 4723 &
 
 # run only Android scenarios (tags: @android)
 mvn -Dcucumber.options.tags="@android" test
 ```
 
 Android test notes
 - Default Appium URL: `http://127.0.0.1:4723/` (override with `-Dappium.url`)
 - Default APK path: `/yourlocataion_path/yourappname.apk` (override with `-Dapp`)
 - Scenarios are under `src/test/resources/features/android_flow.feature` with tags `@login`, `@buy`, `@sort`.
 - Page objects live in `src/main/java/com/example/qa/pages` — update locators there if your app's resource-ids differ.
 
 Selectors and maintenance
 - The sample page objects use accessibility ids and resource-id/xpath heuristics. Real device builds may require adjusting these locators.
 - If you prefer a single-user quick run, use:
 
 ```bash
 mvn -Dapp="/yourlocataion_path/yourappname.apk" -DdeviceName="Android Emulator" -Dcucumber.options.tags="@buy" test
 ```
```

Project layout

- `pom.xml` — Maven build and dependencies
- `src/test/resources/features` — sample Cucumber feature files
- `src/test/java/com/example/qa/runner` — Cucumber JUnit runner
- `src/test/java/com/example/qa/steps` — step definitions
- `src/main/java/com/example/qa/driver` — driver factories (Selenium / Appium)

How to use this template

1. Clone the repo and open in your IDE.
2. Install required tools (JDK, Maven). See prerequisites above.
3. Update capabilities and endpoints in `AppiumDriverFactory` and `DriverFactory` to match your environment.
4. Implement real Page Objects under `src/main/java/com/example/qa/pages` and add step definitions under `src/test/java/com/example/qa/steps`.
5. Add your tests and push to GitHub/GitLab/Bitbucket.

Notes
- This template uses Cucumber tags to separate concerns: `@web`, `@android`, `@api` in feature files.
- The project is minimal by intent — it's designed so you can quickly implement examples required for the test.

If you'd like, I can tailor this to a Gradle build or add a CI workflow for GitHub Actions.
