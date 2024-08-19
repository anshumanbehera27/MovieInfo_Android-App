package com.anshuman.myapplication.Models

import java.io.Serializable



data class Film(
    var Title: String = "",
    var Description: String = "",
    var Poster: String = "",
    var Time: String = "",
    var Trailer: String = "",
    var Imdb: Int = 0,
    var Year: Int = 0,
    var Genre: ArrayList<String> = arrayListOf(),
    var Casts: ArrayList<Cast> = arrayListOf(),
    var isFavorite: Boolean = false
) : Serializable