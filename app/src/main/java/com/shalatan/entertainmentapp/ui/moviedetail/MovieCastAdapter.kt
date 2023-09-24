package com.shalatan.entertainmentapp.ui.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shalatan.entertainmentapp.R
import com.shalatan.entertainmentapp.databinding.ItemCastBinding
import com.shalatan.entertainmentapp.model.Cast
import com.shalatan.entertainmentapp.utils.loadImage

class MovieCastAdapter :
    ListAdapter<Cast, MovieCastAdapter.MovieCastViewHolder>(DiffCallBack) {

    class MovieCastViewHolder(private val binding: ItemCastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: Cast) {
            binding.cast = cast
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<Cast>() {
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return newItem === oldItem
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.cast_id == newItem.cast_id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieCastViewHolder {
        return MovieCastViewHolder(ItemCastBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieCastViewHolder, position: Int) {
        val cast = getItem(position)
        holder.itemView.findViewById<ImageView>(R.id.item_movie_cast_photo)
            .loadImage(cast.profile_path)
        holder.bind(cast)
    }
}