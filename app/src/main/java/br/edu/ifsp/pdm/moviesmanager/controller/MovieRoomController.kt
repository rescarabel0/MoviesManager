package br.edu.ifsp.pdm.moviesmanager.controller

import android.os.Handler
import android.os.Looper
import androidx.room.Room
import br.edu.ifsp.pdm.moviesmanager.model.Movie
import br.edu.ifsp.pdm.moviesmanager.model.dao.MovieRoomDAO
import br.edu.ifsp.pdm.moviesmanager.model.database.MovieRoomDAODatabase
import br.edu.ifsp.pdm.moviesmanager.util.Constants
import br.edu.ifsp.pdm.moviesmanager.view.MainActivity
import java.util.concurrent.Executors

class MovieRoomController(private val mainActivity: MainActivity) {
    private val movieDaoImpl: MovieRoomDAO by lazy {
        Room.databaseBuilder(
            mainActivity,
            MovieRoomDAODatabase::class.java,
            Constants.MOVIE_DATABASE_FILE
        ).build().getMovieRoomDAO()
    }

    fun insert(movie: Movie) {
        Thread {
            movieDaoImpl.create(movie)
            findAll()
        }.start()
    }

    fun findById(id: Int) = movieDaoImpl.findById(id)

    fun findAll() {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            val returnList = mutableListOf<Movie>()
            returnList.addAll(movieDaoImpl.findAll())
            handler.post {
                mainActivity.updateMovieList(returnList)
            }
        }
    }

    fun update(movie: Movie) {
        Thread {
            movieDaoImpl.update(movie)
            findAll()
        }.start()
    }

    fun delete(movie: Movie) {
        Thread {
            movieDaoImpl.delete(movie)
            findAll()
        }.start()
    }
}