package br.edu.ifsp.pdm.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.pdm.moviesmanager.R
import br.edu.ifsp.pdm.moviesmanager.adapter.MovieAdapter
import br.edu.ifsp.pdm.moviesmanager.controller.MovieRoomController
import br.edu.ifsp.pdm.moviesmanager.databinding.ActivityMainBinding
import br.edu.ifsp.pdm.moviesmanager.model.Movie
import br.edu.ifsp.pdm.moviesmanager.util.Constants

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val moviesList: MutableList<Movie> = mutableListOf()

    private lateinit var movieAdapter: MovieAdapter

    private lateinit var parl: ActivityResultLauncher<Intent>

    private val movieController: MovieRoomController by lazy {
        MovieRoomController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        movieAdapter = MovieAdapter(this, moviesList)
        amb.mainLv.adapter = movieAdapter

        parl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { res ->
            if (res.resultCode == RESULT_OK) {
                val actionName = res.data?.getStringExtra(Constants.ACTION_NAME)

                val movie = res.data?.getParcelableExtra<Movie>(Constants.MOVIE)
                movie?.let { _movie ->
                    when (actionName) {
                        Constants.ACTION_ADD -> {
                            val position = moviesList.indexOfFirst { it.id == _movie.id }
                            if (position != -1)
                                movieController.update(_movie)
                            else movieController.insert(_movie)
                        }
                        Constants.ACTION_DELETE -> {
                            movieController.delete(_movie)
                        }
                        Constants.ACTION_EDIT -> {
                            val formIntent = Intent(this, FormMovieActivity::class.java)
                            formIntent.putExtra(Constants.CURRENT_MOVIE, _movie)
                            parl.launch(formIntent)
                        }
                        else -> null
                    }
                }
                movieAdapter.notifyDataSetChanged()
            }
        }

        amb.mainLv.setOnItemClickListener { _, _, position, _ ->
            val payerIntent = Intent(this, MovieActivity::class.java)
            payerIntent.putExtra(Constants.CURRENT_MOVIE, moviesList[position])
            parl.launch(payerIntent)
        }

        movieController.findAll()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_payer_mi -> {
                parl.launch(Intent(this, FormMovieActivity::class.java))
                true
            }
            else -> false
        }
    }

    fun updateMovieList(_moviesList: MutableList<Movie>) {
        moviesList.clear()
        moviesList.addAll(_moviesList)
        movieAdapter.notifyDataSetChanged()
    }

}