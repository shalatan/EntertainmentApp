package com.shalatan.entertainmentapp.ui.recommendation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shalatan.entertainmentapp.database.SavedMovie
import com.shalatan.entertainmentapp.databinding.ItemRecommendedMovieBinding
import com.shalatan.entertainmentapp.model.Movie

//not using for now
class RecommendationMovieAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<SavedMovie, RecommendationMovieAdapter.RecommendedMovieViewHolder>(DiffCallBack) {

    class RecommendedMovieViewHolder(private val binding: ItemRecommendedMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(savedMovie: SavedMovie) {
            binding.savedMovie = savedMovie
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
    ): RecommendedMovieViewHolder {
        return RecommendedMovieViewHolder(ItemRecommendedMovieBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecommendedMovieViewHolder, position: Int) {
        val savedMovie = getItem(position)
        holder.itemView.setOnClickListener {
            val movie = Movie(
                id = savedMovie.Id,
                posterPath = savedMovie.moviePoster!!,
                original_title = savedMovie.movieTitle!!
            )
            (onClickListener.onClick(movie))
        }
        holder.bind(savedMovie)
    }

    class OnClickListener(val clickListener: (movie: Movie) -> Unit) {
        fun onClick(movie: Movie) = clickListener(movie)
    }
}