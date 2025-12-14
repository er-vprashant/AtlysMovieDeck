# AtlysMovieDeck

## TMDB API key setup

This project expects a TMDB API key via `BuildConfig.TMDB_API_KEY`.

You can provide it in either of the following ways:

1. `~/.gradle/gradle.properties`
Add:
```
   TMDB_API_KEY=your_api_key_here
```

2. Environment variable
Set:
```
   TMDB_API_KEY=your_api_key_here
```

The app will still build if the key is missing, but API calls will fail.
