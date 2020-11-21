package com.shalatan.entertainmentapp.adapters

//class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
//
//    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val moviePosterImage: ImageView
//
//        init {
//            moviePosterImage = itemView.findViewById(R.id.movie_poster)
//        }
//    }
//
//    private val differCallback = object : DiffUtil.ItemCallback<MovieResult>() {
//        override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//    val differ = AsyncListDiffer(this, differCallback)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
//        return MovieViewHolder(
//                LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
//        )
//    }
//
//    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
//        val movieResult = differ.currentList[position]
//        holder.itemView.apply {
//
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return differ.currentList.size
//    }
//
//}