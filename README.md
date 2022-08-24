<h1 align="center">Movies Hex</h1>

<p align="center"> 
<a href="https://play.google.com/store/apps/details?id=com.shalatan.entertainmentapp" target="_blank"> <img src="https://raw.githubusercontent.com/Shalatan/EntertainmentApp/master/gallery/play-store-badge.svg?token=AKRVCBOLTCHDEUKJB2KB2HLAPWV4E" alt="android" width="400" height="40"> </a>
</p>

<p align="center">  
Movies Hex is a movie guide app built using TMDB API, shows latest and trending movies, and all the information related to them like Synopsis, Rating, Cast, Posters, Trailers, Similar Movies etc.
<br>
Users can also mark movies as Watch-Later or Watched with the rating, which is stored locally and used to Recommend Movies based on those ratings.
</p>
</br>

# Screenshots
<p align="center">
<img src="gallery/a.gif" width="32%"/>
<img src="gallery/b.gif" width="32%"/>
<img src="gallery/c.gif" width="32%"/>
</p>

# Tech stack & Open-source libraries
- Minimum SDK level 24
- Single activity Appication using [Navigation Component](https://developer.android.com/guide/navigation) to navigate between fragments
- 100% [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
- Hilt for Dependency Injection
- JetPack
  - LiveData - Notify Domain Layer data to views.
  - LifeCycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct a database using the abstract layer.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - [Bindables](https://github.com/skydoves/bindables) - Android DataBinding kit for notifying data changes to UI layers.
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - Construct the REST APIs and paging network data.
- [Moshi](https://github.com/square/moshi/) - A modern JSON Parsing Library for Kotlin and Java
- [Room](https://developer.android.com/training/data-storage/room) - To Save Data in Local Database
- [Glide](https://github.com/bumptech/glide) - For Loading Images
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView.
- [Motion Layout](https://developer.android.com/training/constraint-layout/motionlayout) - To implement motions in fragments
- [ReflectionView](https://github.com/sparrow007/CarouselRecyclerview) - For Reflective Image Views

# Architecture
Movies Kex is based on MVVM architecture and repository pattern.

![alt text](gallery/mvvm-architecture.png)

# License
```xml
Copyright 2021 Shashank Singh

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```