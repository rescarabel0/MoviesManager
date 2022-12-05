package br.edu.ifsp.pdm.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.pdm.moviesmanager.databinding.ActivityMovieBinding
import br.edu.ifsp.pdm.moviesmanager.model.Movie
import br.edu.ifsp.pdm.moviesmanager.util.Constants

class MovieActivity : AppCompatActivity() {
    private val amb: ActivityMovieBinding by lazy {
        ActivityMovieBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        val currentMovie = intent.getParcelableExtra<Movie>(Constants.CURRENT_MOVIE)

        amb.movieNameTv.text = currentMovie?.name
        amb.movieRankingTv.text = "Ranking: " + currentMovie?.ranking.toString()
        amb.movieDurationTv.text = "Duration: " + currentMovie?.durationTime.toString() + " minutes"
        amb.movieGenderTv.text = "Gender: " + currentMovie?.gender
        amb.movieProducerTv.text = "Producer: " + currentMovie?.producer
        amb.movieLaunchTv.text = "Launch: " + currentMovie?.launch.toString()
        amb.movieWatchedTv.text =
            "Status: " + if (currentMovie?.watched == true) "Watched" else "Not watched"

        amb.deleteBtn.setOnClickListener {
            val resIntent = Intent()
            setResult(RESULT_OK, resIntent)
            resIntent.putExtra(Constants.ACTION_NAME, Constants.ACTION_DELETE)
            resIntent.putExtra(Constants.MOVIE, currentMovie)
            finish()
        }

        amb.editBtn.setOnClickListener {
            val resIntent = Intent()
            setResult(RESULT_OK, resIntent)
            resIntent.putExtra(Constants.ACTION_NAME, Constants.ACTION_EDIT)
            resIntent.putExtra(Constants.MOVIE, currentMovie)
            finish()
        }
    }
}