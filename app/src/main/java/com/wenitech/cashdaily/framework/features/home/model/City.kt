package com.wenitech.cashdaily.framework.features.home.model

data class City(
        var name: String? = null,
        var weather: ArrayList<Weather>? = null,
        var main: Main?= null,
)
