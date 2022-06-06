package com.shalatan.entertainmentapp.ui.moviesection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shalatan.entertainmentapp.NavGraphDirections
import com.shalatan.entertainmentapp.R
import com.shalatan.entertainmentapp.database.MovieDatabase
import com.shalatan.entertainmentapp.databinding.FragmentWatchedMoviesBinding

class WatchedMoviesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentWatchedMoviesBinding.inflate(inflater)
        val dataSource = MovieDatabase.getInstance(requireContext()).movieDAO
        val repository = SavedContentRepository(dataSource)
        val viewModelFactory = SavedContentViewModelFactory(repository)

        val viewModel = ViewModelProvider(
            this, viewModelFactory
        )[SavedContentViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

//        if(viewModel.watchedMovies.value.isEmpty()){
//            binding.savedContentText.visibility = View.GONE
//            binding.savedContentRecyclerView.visibility = View.VISIBLE
//        }
//        else{
//            binding.savedContentText.visibility = View.VISIBLE
//            binding.savedContentRecyclerView.visibility = View.GONE
//        }

        binding.savedContentRecyclerView.adapter =
            SavedContentAdapter(SavedContentAdapter.OnClickListener {
                viewModel.displayMovieDetails(it)
            })

        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.findNavController().navigate(NavGraphDirections.actionGlobalDetailFragment(it))
                viewModel.displayMovieDetailsCompleted()
            }
        })

        //show or hide recyclerView is there's data or not
        viewModel.watchedMovies.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                binding.savedContentRecyclerView.visibility = View.GONE
                binding.savedContentText.visibility = View.VISIBLE
            } else {
                binding.savedContentRecyclerView.visibility = View.VISIBLE
                binding.savedContentText.visibility = View.GONE
            }
        })

        //divider for recyclerView
        val decoration =
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.recycler_view_divider)?.let {
            decoration.setDrawable(
                it
            )
        }
        binding.savedContentRecyclerView.addItemDecoration(decoration)

        //swipe to delete
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.layoutPosition
                viewModel.removeFromWatched(position)
            }
        }).attachToRecyclerView(binding.savedContentRecyclerView)
        return binding.root
    }
}