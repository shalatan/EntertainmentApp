package com.shalatan.entertainmentapp.ui.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shalatan.entertainmentapp.databinding.ItemMovieLandscapeBinding
import com.shalatan.entertainmentapp.model.Movie

class LandscapeMovieAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Movie, LandscapeMovieAdapter.LandscapeMovieViewHolder>(DiffCallBack) {

    class LandscapeMovieViewHolder(private val binding: ItemMovieLandscapeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
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
    ): LandscapeMovieViewHolder {
        return LandscapeMovieViewHolder(ItemMovieLandscapeBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: LandscapeMovieViewHolder, position: Int) {
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