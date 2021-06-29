# Order Food Application
> Android application build it  with kotlin that accept/refuse order and show list of food

## Build
* should use Android Studio 2020.3.1 beta 4 or above 



##### In this project, we implement the  clean architecture
* we have 3 layer:

    * <srong>App module </string>  : This module contains all of the code related to the UI/Presentation layer such as activities,fragment,dialog,custom views  and contain viewModel,dependency injection module app
    * <srong>Core</string> : holds all concrete implementations of our repositories,usecaes and other data sources like  network
    * <srong>Domain module </string>  : contain all interfaces of repositories ,usecase and data classes



> I used hilt as dependency injection for this project
> I used compose for UI
> I used retrofit for http calls and flowAPI to collect data
> I build rest api using ktor 


### screenshots
<img width="150" height="300" src="/screenshot/emptyview.png" >
<img width="150" height="300" src="/screenshot/ingredient-main.png" alt="ingredient main">
<img width="150" height="300" src="/screenshot/ingredient-meal.png" alt="ingredient meal">
<img width="150" height="300" src="/screenshot/ingredient-other.png" alt="ingredient other">
<img width="150" height="300" src="/screenshot/orders.png" alt="order">