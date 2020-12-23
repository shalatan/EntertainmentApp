package com.shalatan.entertainmentapp.ui.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shalatan.entertainmentapp.databinding.ItemViewPagerPosterBinding
import com.shalatan.entertainmentapp.model.Backdrop

class PostersAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Backdrop, PostersAdapter.PosterViewHolder>(DiffCallBack) {

    class PosterViewHolder(private val binding: ItemViewPagerPosterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(backdrop: Backdrop) {
            binding.backdrop = backdrop
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<Backdrop>() {
        override fun areItemsTheSame(oldItem: Backdrop, newItem: Backdrop): Boolean {
            return newItem === oldItem
        }

        override fun areContentsTheSame(oldItem: Backdrop, newItem: Backdrop): Boolean {
            return oldItem.file_path == newItem.file_path
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        return PosterViewHolder(
            ItemViewPagerPosterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        val backdrop = getItem(position)
        holder.itemView.setOnClickListener {
            (onClickListener.onCLick(backdrop.file_path))
        }
        holder.bind(backdrop)
    }


    class OnClickListener(val clickListener: (posterUrl: String) -> Unit) {
        fun onCLick(posterUrl: String) = clickListener(posterUrl)
    }
}