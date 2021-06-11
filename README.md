# BlockChain
BitCoin Market prices application:  Simple app to fetch Bitcoin market prices using [BlockChain API](https://www.blockchain.com/charts) and show that in graphical format.  


Developed using MVVM Architecture on Kotlin.

- Accessed the Open Source Blockchain API via RetroFit Network Calls.
- 100% Kotlin
- Clean and Reachable Code
- Modular Project Structure
- Clean Architecture with SOLID Principles
- Used Dagger - Dependency injection
- Created NetworkModule and implemented OkHttpClient inside that.
- Created a CustomOkHttpInterceptor - We can add a common header for all the API's here
- Used MVVM - Model View View Model - Fragment - ViewModel - Repo - API
- Live Data
- Used Data Binding for views
- CoRountines
- Material Design Components
- [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)
- Unit Testing - View Model Testing , Repo Testing, API Testsing using Mockito
- Instrumenation Testing with Espresso for UI Testing

### App Architecture

Used MVVM Architecture and Single Actvity Pattern using Nav Graph Component.

Nav Graph Contains a ChartFragment and a Filter Dialog - Used Bottom Sheet Dialog.

Also ChartFragment contains a side menu which has access to multiple charts. 

Used CoRountines  + Retrofit for the API call. Single API call for all the charts.

And Mapped the data in view model before binding it to View. And Created a Custom Chart using MPAndroidChart Library.

Added a Filter Dialog to filter the chart using Time Span.

### [Download APK](https://github.com/yokeshezumalai/BlockChain/blob/master/app-DEV-debug.apk)

