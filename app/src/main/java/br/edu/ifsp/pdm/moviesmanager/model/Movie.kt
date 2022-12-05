package br.edu.ifsp.pdm.moviesmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie (
    val id: Int,
    val name: String,
    var launch: Int,
    var producer: String,
    var durationTime: Int,
    var watched: Boolean = false,
    var ranking: Int = 0,
    var gender: String
) : Parcelable