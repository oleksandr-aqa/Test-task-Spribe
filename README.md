# Spribe Test Task – Player API Automation

---

## Technologies and Tools
- **Java 21**
- **Maven** – dependency management and build tool
- **TestNG** – testing framework
- **RestAssured** – for REST API requests
- **Allure Test Report** – for interactive and detailed test reports
- **SLF4J + Logback** – logging
- **Jackson** – JSON serialization/deserialization

---
## Project Structure
```text
src/
├─ main/
│  ├─ java/
│  │  ├─ api/
│  │  ├─ dto/
│  │  │  ├─ request/
│  │  │  └─ response/
│  │  └─ helpers/
│  └─ resources/
├─ test/
│  ├─ java/
│  │  ├─ base/
│  │  ├─ data/
│  │  └─ player/
│  └─ resources/
pom.xml
testng.xml
player_api_test_cases.md
Found bugs.md
```
## Project Details

The project contains the following additional files to help with testing and automation:

- **`player_api_test_cases.md`**  
  A text file containing a list of API test cases in a simple format.  
  This file serves as a reference for creating automated tests.

- **`Found bugs.md`**  
  A text file listing known bugs or issues discovered during testing.

### Resources

- **`src/test/resources`**  
  Contains JSON files with user templates for convenient test data management.  
  These templates are read using the `JsonReader` utility class, which allows creating users in tests easily.

- **`src/main/resources`**  
  Contains a `properties` file with configuration parameters:
    - `baseURI` – the base URL of the API under test
    - `players.data.file.name` – specifies which JSON file with template user data to use

## Running Tests and Allure Reports


### Prerequisites

- Ensure **Maven** is installed on your system.
- Make sure **Maven is added to your system PATH** so that `mvn` commands can be executed from the terminal.
- Java 21 (or the configured version in `pom.xml`) must be installed.

### Running 

1. Open a terminal in the project root directory.
2. Run the following command to execute the tests:

```
mvn clean test
```
- Tests are configured to run in **3 parallel threads**, as specified in the `testng.xml` file.
- Basic logging is implemented using `log.info()` to print the **status code** and **response body** for each API request.
- Additionally, `log().ifValidationFails()` is used to capture detailed logs if a test assertion fails.

**Note:** Many tests may fail due to known bugs in the API, as documented in `Some bugs.md`.

3. After tests finish, generate and view the Allure report:

```
mvn allure:serve
