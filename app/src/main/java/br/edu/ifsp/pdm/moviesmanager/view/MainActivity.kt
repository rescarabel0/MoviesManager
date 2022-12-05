package br.edu.ifsp.pdm.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.pdm.moviesmanager.adapter.MovieAdapter
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        fillMovieList()
        movieAdapter = MovieAdapter(this, moviesList)
        amb.mainLv.adapter = movieAdapter

        parl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { res ->

        }

        amb.mainLv.setOnItemClickListener { _, _, position, _ ->
            val payerIntent = Intent(this, MovieActivity::class.java)
            payerIntent.putExtra(Constants.CURRENT_MOVIE, moviesList[position])
            parl.launch(payerIntent)
        }
    }

    private fun fillMovieList() {
        for (i in 1..5) {
            moviesList.add(
                Movie(
                    i,
                    "Nome $i",
                    i,
                    "Comprou $i",
                    i,
                    false,
                    i,
                    "Genero $i"
                )
            )
        }
    }

}