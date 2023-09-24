package com.shalatan.entertainmentapp.ui.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shalatan.entertainmentapp.R
import com.shalatan.entertainmentapp.databinding.ItemMovieBinding
import com.shalatan.entertainmentapp.model.Movie
import com.shalatan.entertainmentapp.utils.loadImage

class MovieAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Movie, MovieAdapter.MovieViewHolder>(DiffCallBack) {

    class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
//            binding.itemMoviePoster.transitionName = movie.original_title
            binding.movie = movie
            binding.executePendingBindings()
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return newItem === oldItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        return MovieViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)

        holder.itemView.findViewById<ImageView>(R.id.item_movie_poster)
            .loadImage(movie.posterPath)

        holder.itemView.setOnClickListener {
            (onClickListener.onClick(movie))
        }
        holder.bind(movie)
    }

    class OnClickListener(val clickListener: (movie: Movie) -> Unit) {
        fun onClick(movie: Movie) = clickListener(movie)
    }
}