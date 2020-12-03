package com.shalatan.entertainmentapp.ui.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shalatan.entertainmentapp.R
import com.shalatan.entertainmentapp.databinding.MovieItemBinding
import com.shalatan.entertainmentapp.model.Movie

class MovieAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Movie, MovieAdapter.MovieViewHolder>(DiffCallBack) {

    class MovieViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val image = binding.root.findViewById<ImageView>(R.id.movie_item)
        fun bind(movie: Movie) {
            binding.movie = movie
            ViewCompat.setTransitionName(image,movie.id.toString())
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
        return MovieViewHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.itemView.setOnClickListener {
            (onClickListener.onClick(movie))
        }
        holder.bind(movie)
    }

    class OnClickListener(val clickListener: (movie: Movie) -> Unit) {
        fun onClick(movie: Movie) = clickListener(movie)
    }
}