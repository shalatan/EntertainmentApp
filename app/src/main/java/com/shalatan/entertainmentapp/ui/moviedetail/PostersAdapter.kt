package com.shalatan.entertainmentapp.ui.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shalatan.entertainmentapp.database.SavedMovie
import com.shalatan.entertainmentapp.databinding.ViewPagerItemBinding
import com.shalatan.entertainmentapp.model.Backdrop


class PostersAdapter :
    ListAdapter<Backdrop, PostersAdapter.PosterViewHolder>(DiffCallBack) {

    class PosterViewHolder(private val binding: ViewPagerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(backdrop: Backdrop) {
            binding.backdrop = backdrop
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        return PosterViewHolder(ViewPagerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        val backdrop = getItem(position)
        holder.bind(backdrop)
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<Backdrop>() {
        override fun areItemsTheSame(oldItem: Backdrop, newItem: Backdrop): Boolean {
            return newItem === oldItem
        }

        override fun areContentsTheSame(oldItem: Backdrop, newItem: Backdrop): Boolean {
            return oldItem.file_path == newItem.file_path
        }
    }
}