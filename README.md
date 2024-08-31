# Moviequ

**Moviequ** is an Android application that allows users to browse and search for movies. It leverages modern Android development practices, including Jetpack Compose, Paging 3, and Room for local caching.

## Features
- Browse popular movies
- Search for movies by title
- Offline caching with Room
- Retry mechanism when loading fails
- Shimmer effect for loading states

## Tech Stack
- **Kotlin**: The primary programming language used in the project.
- **Jetpack Compose**: For building the UI declaratively.
- **Paging 3**: To load data in pages from the API and handle infinite scrolling.
- **Room**: For local caching of movie data.
- **Retrofit**: To handle network requests.
- **Hilt**: For dependency injection.
- **Coil**: For image loading.
- **Coroutines**: For managing background threads.
- **ViewModel**: For managing UI-related data.

## API
Moviequ fetches movie data from the [OMDb API](https://www.omdbapi.com/). You need to obtain an API key and add it to the project.


## Getting Started

### Clone the Repository

```bash
git clone https://github.com/arrazyfathan/Moviequ.git
cd Moviequ
```

## Open the Project
1. Open Android Studio.
2. Click on Open an existing Android Studio project.
3. Navigate to the Moviequ directory and select it.

## Build the Project
1. Make sure you have the required Android SDKs installed.
2. Sync the project with Gradle files by clicking Sync Now when prompted.
3. Build the project by selecting Build > Make Project or pressing Ctrl+F9.

## Run the App
1. Connect an Android device or start an emulator.
2. Click on Run > Run 'app' or press Shift+F10.

# License

```xml
MIT License

Copyright (c) [2024] [Ar Razy Fathan Rabbani]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```


