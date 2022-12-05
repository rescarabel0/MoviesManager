package br.edu.ifsp.pdm.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.pdm.moviesmanager.databinding.ActivityFormMovieBinding
import br.edu.ifsp.pdm.moviesmanager.model.Movie
import br.edu.ifsp.pdm.moviesmanager.util.Constants
import kotlin.random.Random

class FormMovieActivity : AppCompatActivity() {
    private val afmb: ActivityFormMovieBinding by lazy {
        ActivityFormMovieBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(afmb.root)

        val watchedSpAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayOf("Yes", "No"))
        watchedSpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(afmb.watchedSp) {
            adapter = watchedSpAdapter
        }

        val currentMovie =
            intent.getParcelableExtra<Movie>(Constants.CURRENT_MOVIE)
        currentMovie?.let { _currentMovie ->
            with(afmb) {
                with(_currentMovie) {
                    nameEt.setText(name)
                    launchEt.setText(launch.toString())
                    producerEt.setText(producer)
                    durationEt.setText(durationTime.toString())
                    rankingEt.setText(ranking.toString())
                    watchedSp.setSelection(if (watched) 1 else 0)
                }
            }
        }

        afmb.saveBt.setOnClickListener {
            val movie = Movie(
                currentMovie?.id ?: Random(System.currentTimeMillis()).nextInt(),
                afmb.nameEt.text.toString(),
                afmb.launchEt.text.toString().toInt(),
                afmb.producerEt.text.toString(),
                afmb.durationEt.text.toString().toInt(),
                afmb.watchedSp.selectedItemPosition != 0,
                afmb.rankingEt.text.toString().toInt(),
                "to do"
            )

            val resIntent = Intent()
            setResult(RESULT_OK, resIntent)
            resIntent.putExtra(Constants.MOVIE, movie)
            resIntent.putExtra(Constants.ACTION_NAME, Constants.ACTION_ADD)
            finish()
        }
    }
}