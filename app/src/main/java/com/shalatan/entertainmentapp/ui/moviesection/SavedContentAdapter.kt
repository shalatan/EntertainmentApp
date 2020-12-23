package com.shalatan.entertainmentapp.ui.moviesection

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shalatan.entertainmentapp.database.SavedMovie
import com.shalatan.entertainmentapp.databinding.FavouriteItemBinding
import com.shalatan.entertainmentapp.model.Movie

class SavedContentAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<SavedMovie, SavedContentAdapter.SavedContentViewHolder>(DiffCallBack)
{

    class SavedContentViewHolder(private val binding: FavouriteItemBinding) :
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
    ): SavedContentViewHolder {
        return SavedContentViewHolder(FavouriteItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SavedContentViewHolder, position: Int) {
        val savedMovie = getItem(position)
        holder.itemView.setOnClickListener {
            Log.e("ITEM", "DABADIYAAAAAAAAAAAAA")
            val movie = Movie(
                id = savedMovie.Id,
                posterPath = savedMovie.moviePoster!!,
                title = savedMovie.movieTitle!!
            )
            (onClickListener.onClick(movie))
        }
        holder.bind(savedMovie)
    }

    class OnClickListener(val clickListener: (movie: Movie) -> Unit) {
        fun onClick(movie: Movie) = clickListener(movie)
    }
}