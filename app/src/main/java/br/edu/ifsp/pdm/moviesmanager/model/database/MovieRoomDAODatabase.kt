package br.edu.ifsp.pdm.moviesmanager.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.edu.ifsp.pdm.moviesmanager.model.Movie
import br.edu.ifsp.pdm.moviesmanager.model.dao.MovieRoomDAO

@Database(entities = [Movie::class], version = 1)
abstract class MovieRoomDAODatabase : RoomDatabase() {
    abstract fun getMovieRoomDAO(): MovieRoomDAO
}