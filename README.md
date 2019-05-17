[![Build Status](https://travis-ci.org/vcamargo/MyPlaces.svg?branch=master)](https://travis-ci.org/vcamargo/MyPlaces)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=vcamargo%3Astorytel-app&metric=coverage)](https://sonarcloud.io/dashboard?id=vcamargo%3Astorytel-app)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=vcamargo%3Astorytel-app&metric=alert_status)](https://sonarcloud.io/dashboard?id=vcamargo%3Astorytel-app)

# My Places App

This project use the Foursquare API to display the restaurants around the current user's location on the map.

## Getting Started

### Prerequisites

Android SDK API 28
Android SDK Build Tools 28.0.3

### Installing

After cloning and import the project on Android Studio, simple Run the App (Run > Run 'app') or execute the gradle task installDebug

```
./gradlew installDebug
```

Please note that you must have an Emulator running or a real device connected to your workstation.

## Running the tests

Our CI system ensure that the unit tests are all passing before you'll be able to merge your feature branch to master. Due to simplicity the UI tests are not included in the CI (sadly Travis-ci is not very friendly to create and run an Android Emulator).
But you can still run both junit and UI tests manually using gradle:

## Unit Test
```
./gradlew test
```

## UI Tests
```
./gradlew connectedAndroidTest
```

## Usefull links

* [Sonarcloud](https://sonarcloud.io/dashboard?id=vcamargo_MyPlaces) - Code Quality Tool
* [Travis-CI](https://travis-ci.org/vcamargo/MyPlaces) - CI Pipeline

## Authors

* **Vinicius Camargo** - [vcamargo](https://github.com/vcamargo)
