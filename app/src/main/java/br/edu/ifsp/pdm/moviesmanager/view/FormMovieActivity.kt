package br.edu.ifsp.pdm.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.pdm.moviesmanager.databinding.ActivityFormMovieBinding
import br.edu.ifsp.pdm.moviesmanager.model.Movie
import br.edu.ifsp.pdm.moviesmanager.util.Constants
import br.edu.ifsp.pdm.moviesmanager.util.Gender
import java.util.*
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

        val genderSpAdapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                Gender.values().map { it.value })
        genderSpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(afmb.genderSp) {
            adapter = genderSpAdapter
        }

        val gendersList = Gender.values().map { it.value }

        val currentMovie =
            intent.getParcelableExtra<Movie>(Constants.CURRENT_MOVIE)
        currentMovie?.let { _currentMovie ->
            with(afmb) {
                afmb.nameEt.isEnabled = false
                with(_currentMovie) {
                    nameEt.setText(name)
                    launchEt.setText(launch.toString())
                    producerEt.setText(producer)
                    durationEt.setText(durationTime.toString())
                    rankingEt.setText(ranking.toString())
                    watchedSp.setSelection(if (watched) 1 else 0)
                    genderSp.setSelection(gendersList.indexOf(gender.value))
                }
            }
        }

        afmb.saveBt.setOnClickListener {
            val launch = afmb.launchEt.text.toString().toInt()
            if (launch > Calendar.getInstance().get(Calendar.YEAR)) {
                Toast.makeText(this, "Error! Launch year is invalid", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val duration = afmb.durationEt.text.toString().toInt()
            if (duration > 200) {
                Toast.makeText(this, "Error! Duration time is invalid", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val ranking = afmb.rankingEt.text.toString().toInt()
            if (ranking > 10 || ranking < 0) {
                Toast.makeText(this, "Error! Ranking is invalid", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val movie = Movie(
                currentMovie?.id ?: Random(System.currentTimeMillis()).nextInt(),
                currentMovie?.name ?: afmb.nameEt.text.toString(),
                afmb.launchEt.text.toString().toInt(),
                afmb.producerEt.text.toString(),
                afmb.durationEt.text.toString().toInt(),
                afmb.watchedSp.selectedItemPosition != 0,
                afmb.rankingEt.text.toString().toInt(),
                Gender.values().find { it.value == afmb.genderSp.selectedItem.toString() }
                    ?: Gender.OTHER
            )

            val resIntent = Intent()
            setResult(RESULT_OK, resIntent)
            resIntent.putExtra(Constants.MOVIE, movie)
            resIntent.putExtra(Constants.ACTION_NAME, Constants.ACTION_ADD)
            finish()
        }
    }
}