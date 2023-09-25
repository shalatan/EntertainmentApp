package com.shalatan.entertainmentapp.ui.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shalatan.entertainmentapp.databinding.ItemVideoBinding
import com.shalatan.entertainmentapp.model.Result

class VideoAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Result, VideoAdapter.VideoViewHolder>(DiffCallBack) {

    class VideoViewHolder(private val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            binding.itemVideoTitle.text = result.name
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return newItem === oldItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            ItemVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = getItem(position)
        holder.itemView.setOnClickListener {
            (onClickListener.onClick(video))
        }
        holder.bind(video)
    }

    class OnClickListener(val clickListener: (result: Result) -> Unit) {
        fun onClick(result: Result) = clickListener(result)
    }
}