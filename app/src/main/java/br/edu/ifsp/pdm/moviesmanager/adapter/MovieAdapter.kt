package br.edu.ifsp.pdm.moviesmanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.pdm.moviesmanager.R
import br.edu.ifsp.pdm.moviesmanager.model.Movie

class MovieAdapter (
    context: Context,
    private val movieList: MutableList<Movie>
        ) : ArrayAdapter<Movie>(context, R.layout.tile_movie, movieList) {
            private data class TileMovieHolder(val nameTv: TextView, val rankingTv: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val movie = movieList[position]
        var movieTileView = convertView

        if (movieTileView == null) {
            movieTileView =
                (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                    R.layout.tile_movie,
                    parent,
                    false
                )
            val tileMovieHolder = TileMovieHolder(
                movieTileView.findViewById(R.id.nameTv),
                movieTileView.findViewById(R.id.rankingTv),
            )
            movieTileView.tag = tileMovieHolder
        }

        with(movieTileView?.tag as TileMovieHolder) {
            nameTv.text = movie.name
            rankingTv.text = "Ranking: " + movie.ranking.toString()
        }

        return movieTileView
    }
}