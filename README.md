# AtlysMovieDeck

Small Jetpack Compose app that browses **TMDB trending movies** with offline caching and smooth pagination.

## Demo Link
- https://drive.google.com/file/d/17_JajjaH3r3D2uYDLF_tHt7NRdz4Y-5v/view?usp=drive_link

## Features

- **Trending grid** (Paging 3)
- **Offline-first caching** (Room + RemoteMediator)
- **Local search** over cached movies
- **Movie details** screen
- **Edge-to-edge UI**

## Tech stack

- Kotlin, Jetpack Compose (Material 3)
- Paging 3 (`paging-compose`)
- Room
- Retrofit + OkHttp
- Hilt
- Coil

## Setup (TMDB API key)

The app reads the key from `BuildConfig.TMDB_API_KEY`.

Provide `TMDB_API_KEY` via one of:

1. `~/.gradle/gradle.properties`
```
TMDB_API_KEY=your_key_here
```

2. Environment variable
```
export TMDB_API_KEY=your_key_here
```

Notes:
- You can pass a **TMDB v4 Bearer token** as well (value starting with `Bearer `).
- The app builds without a key, but network calls will fail.

## Run

- Open in Android Studio
- Sync Gradle
- Run the `app` configuration
