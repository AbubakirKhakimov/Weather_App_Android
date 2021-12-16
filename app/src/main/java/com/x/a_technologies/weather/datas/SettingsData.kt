package com.x.a_technologies.weather.datas

object SettingsData {

    var citiesList = ArrayList<CitiesData>()

    //File data
    val settingsFileName = "SettingsFile"
    val citiesDataKey = "CitiesData"

    //Weather API settings
    var units:String = "metric"
    var language:String = "en"

    //Weather API settings constants
    val BASE_URL_WEATHER = "https://api.openweathermap.org/data/2.5/"
    val API_KEY_WEATHER = "49234d0f6b38c5cbaeb8f9b542d64074"
    val exclude = "minutely,alerts"

    //City location API settings constants
    val BASE_URL_CITY_LOCATION = "https://spott.p.rapidapi.com/places/"
    val API_KEY_CITY_LOCATION = "994c76e968mshf6666e4108720b6p154e32jsnefa48133becd"
    val HOST = "spott.p.rapidapi.com"
    val limit = 10
    val type = "CITY"
}