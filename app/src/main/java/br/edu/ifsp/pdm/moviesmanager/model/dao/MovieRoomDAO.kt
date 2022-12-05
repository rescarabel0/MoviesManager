package br.edu.ifsp.pdm.moviesmanager.model.dao

import androidx.room.*
import br.edu.ifsp.pdm.moviesmanager.model.Movie

@Dao
interface MovieRoomDAO {
    companion object Constant {
        const val MOVIE_TABLE = "movie"
        const val ID_COLUMN = "id"
        const val NAME_COLUMN = "name"
    }

    @Insert
    fun create(movie: Movie)

    @Query("SELECT * FROM $MOVIE_TABLE WHERE $ID_COLUMN = :id")
    fun findById(id: Int): Movie?

    @Query("SELECT * FROM $MOVIE_TABLE ORDER BY $NAME_COLUMN")
    fun findAll(): MutableList<Movie>

    @Update
    fun update(movie: Movie): Int

    @Delete
    fun delete(movie: Movie): Int
}