package com.x.a_technologies.weather.datas.DatasCityLocationAPI

data class CityLocation(
    val adminDivision1: AdminDivision1,
    val coordinates: Coordinates,
    val country: Country,
    val elevation: Int,
    val geonameId: Int,
    val id: String,
    val name: String,
    val population: Int,
    val score: Double,
    val timezoneId: String,
    val type: String
)