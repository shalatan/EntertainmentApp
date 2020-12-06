package com.shalatan.entertainmentapp.ui.moviedetail

import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shalatan.entertainmentapp.R
import com.shalatan.entertainmentapp.model.Poster
import com.shalatan.entertainmentapp.utils.Constants
import kotlinx.android.synthetic.main.view_pager_item.view.*

class PostersAdapter(val application: Application,val images: List<Poster>) :
    RecyclerView.Adapter<PostersAdapter.PosterViewHolder>() {
    inner class PosterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_pager_item, parent, false)
        return PosterViewHolder(view)
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        val image = images[position]
        val link = images[position].file_path
        val url = Constants.IMG_BASE_URL+link
        Log.e("BASE URL",url)
        Glide.with(application)
            .load(url)
            .into(holder.itemView.view_pager_image)
    }

    override fun getItemCount(): Int {
        return images.size
    }

}