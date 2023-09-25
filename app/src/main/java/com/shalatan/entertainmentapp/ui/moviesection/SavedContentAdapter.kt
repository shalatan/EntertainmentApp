package com.shalatan.entertainmentapp.ui.moviesection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shalatan.entertainmentapp.database.SavedMovie
import com.shalatan.entertainmentapp.databinding.ItemSavedMovieBinding
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.utils.loadImage
import com.shalatan.entertainmentapp.utils.loadMovieRecommendation

class SavedContentAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<SavedMovie, SavedContentAdapter.SavedContentViewHolder>(DiffCallBack) {

    class SavedContentViewHolder(private val binding: ItemSavedMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(savedMovie: SavedMovie) {
            if (savedMovie.isRated) {
                binding.movieRating.visibility = View.VISIBLE
                binding.movieRating.rating = savedMovie.rating
                binding.savedMovieProgressBar.visibility = View.GONE
            } else {
                binding.savedMovieProgressBar.visibility = View.VISIBLE
                binding.savedMovieProgressBar.loadMovieRecommendation(savedMovie)
                binding.movieRating.visibility = View.GONE
            }
            binding.savedMoviePoster.loadImage(savedMovie.moviePoster)
            binding.savedMovieTitle.text = savedMovie.movieTitle
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<SavedMovie>() {
        override fun areItemsTheSame(oldItem: SavedMovie, newItem: SavedMovie): Boolean {
            return newItem === oldItem
        }

        override fun areContentsTheSame(oldItem: SavedMovie, newItem: SavedMovie): Boolean {
            return oldItem.Id == newItem.Id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SavedContentViewHolder {
        return SavedContentViewHolder(ItemSavedMovieBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SavedContentViewHolder, position: Int) {
        val savedMovie = getItem(position)
        holder.itemView.setOnClickListener {
            val movie = Movie(
                id = savedMovie.Id,
                posterPath = savedMovie.moviePoster,
                original_title = savedMovie.movieTitle
            )
            (onClickListener.onClick(movie))
        }
        holder.bind(savedMovie)
    }

    class OnClickListener(val clickListener: (movie: Movie) -> Unit) {
        fun onClick(movie: Movie) = clickListener(movie)
    }
}