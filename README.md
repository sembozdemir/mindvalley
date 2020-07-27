# Mindvalley code challenge

- MVVM & Architecture Components (ViewModel, LiveData) are used in this project.
- It is written in Kotlin.
- Kotlin Coroutines is used for background operations.
- Dagger Hilt is used for dependency injection.
- Retrofit is used for networking.
- Moshi is used for JSON parsing.
- Room is used for cache mechanism and offline support.
- Timber is used for logging and reporting exceptions easily.
- Glide is used for image loading and caching.
- AdapterDelegate is used for building a scalable recyclerview implementation easily.

### What parts of the test did you find challenging and why?

Writing UI Tests were challenging for me. I tried to write UI tests. However, I couldn't mock 
ViewModel in this MVVM architecture properly. On the other hand, I wrote unit tests for ViewModels. 
ViewModel tests cover all business logic.

Apart from that, sending three api requests parallel were a little bit challenging for me. 
I believe that sending these requests sequentially is not good for performance. I tried to make 
it with Kotlin Flow. Then, I realized that it can be implemented with using regular 
Kotlin coroutines. In my app, three api requests are sent at the same time and it waits 
for them to be completed before updating UI.

### What feature would you like to add in the future to improve the project?

I would like to add series and course detail screens and demonstrate collapsing toolbar. 
I reviewed course detail screen on Mindvalley app. There is no collapsing toolbar. It would be a 
great chance to show my perspective of UI/UX design to you. In addition that, I could demonstrate 
usage of navigation component if there were two screens in the app.



