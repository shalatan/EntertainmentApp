package com.shalatan.entertainmentapp.ui.moviesection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.shalatan.entertainmentapp.database.MovieDatabase
import com.shalatan.entertainmentapp.databinding.FragmentWatchLaterMoviesBinding

class WatchLaterMoviesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentWatchLaterMoviesBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application
        val dataSource = MovieDatabase.getInstance(application).movieDAO
        val viewModelFactory = SavedContentViewModelFactory(dataSource, application)

        val viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(SavedContentViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.savedContentRecyclerView.adapter =
            SavedContentAdapter(SavedContentAdapter.OnClickListener {
                viewModel.displayMovieDetails(it)
            })
        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val directions = WatchLaterMoviesFragmentDirections.actionShowDetail(it)
                this.findNavController().navigate(directions)
                viewModel.displayMovieDetailsComplete()
            }
        })

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
                viewModel.removeFromWatchLater(position)
            }
        }).attachToRecyclerView(binding.savedContentRecyclerView)

        return binding.root
    }
}