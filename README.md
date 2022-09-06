# :sunrise_over_mountains: city_air_quality_app 

This application shows us the air quality values of the city typed in searchview. 

## What does this app do ?

- Receives the air quality of the city we type into the search view using Retrofit from the API
- The received cities' values are saved locally via Room Database
- Search view gives us city name suggestions based on old data.
- If there is no such city name searched before, you can go to the detail screen by clicking the search button.
- We can click city name suggestion to go to detail screen.
- In detail screen there is a part that includes up to date air quality of the city.Furthermore, there are previous searches we get from database

### Built with ; 

:small_orange_diamond: <a href="https://alitalhacoban.medium.com/use-roomdb-instead-sqlite-in-android-c0a34495470f">Room Database</a>- is a part of the Android Architecture components which provides an abstraction layer over SQLite.

:small_orange_diamond: <a href="https://developer.android.com/training/dependency-injection/hilt-android">Hilt-Dagger</a> - Library that provides a standard way to incorporate Dagger dependency injection into an Android application.

:small_orange_diamond: <a href="https://developer.android.com/topic/libraries/architecture/livedata">LiveData</a> - Observable data holder class

:small_orange_diamond: <a href="https://square.github.io/retrofit/">Retrofit</a> - A type-safe HTTP client for Android , Java and Kotlin.

:small_orange_diamond: <a href="https://docs.ambeedata.com/">Ambeedata</a> - Api used in this project

:small_orange_diamond: <a href="https://developer.android.com/topic/libraries/view-binding">View Binding</a> - Feature that allows you to more easily write code that interacts with views

:small_orange_diamond: <a href="https://alitalhacoban.medium.com/usage-snackbars-android-ef0f55d7882b">Snackbars</a> - To gives error and warning messages. 
