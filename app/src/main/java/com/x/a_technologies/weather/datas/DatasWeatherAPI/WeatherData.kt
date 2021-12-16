package com.x.a_technologies.weather.datas.DatasWeatherAPI

data class WeatherData(
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
)