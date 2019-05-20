[![Build Status](https://travis-ci.org/vcamargo/MyPlaces.svg?branch=master)](https://travis-ci.org/vcamargo/MyPlaces)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=vcamargo_MyPlaces&metric=coverage)](https://sonarcloud.io/dashboard?id=vcamargo_MyPlaces)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=vcamargo_MyPlaces&metric=alert_status)](https://sonarcloud.io/dashboard?id=vcamargo_MyPlaces)

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

### Foursquare API Login
Before you start you must have a Foursquare API Login and an App created in the Foursquare developer's portal.
Follow the steps in order to create a login and app https://developer.foursquare.com/docs/api
After it's created you must update the Webservice interface locally.

File: com.vcamargo.myplaces.webservice.Webservice

```
// foursquare api Userless Auth
const val FOURSQUARE_API_CLIENT_ID = ""
// foursquare api Userless Auth
const val FOURSQUARE_API_CLIENT_SECRET = ""
```

### Google Maps Api Key

Also the file google_maps_api.xml must be updated with your Google Maps API Key https://developers.google.com/maps/documentation/android/start#get-key

File: res/values/google_maps_api.xml

```
 <string name="google_maps_key" translatable="false" templateMergeStrategy="preserve"></string>
```

There are two different debug build variantes (debug and debug_mock). Debug will set the repository to fetch data from the official Foursquare API, but debug_mock will use MockRepository with hardcoded responses instead.

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
