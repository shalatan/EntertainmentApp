package com.shalatan.entertainmentapp.ui.moviesection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shalatan.entertainmentapp.NavGraphDirections
import com.shalatan.entertainmentapp.R
import com.shalatan.entertainmentapp.databinding.FragmentWatchLaterMoviesBinding
import com.shalatan.entertainmentapp.databinding.FragmentWatchedMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchLaterMoviesFragment : Fragment() {

    val viewModel: SavedContentViewModel by viewModels()

    private var _binding: FragmentWatchLaterMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWatchLaterMoviesBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

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
        viewModel.watchLaterMovies.observe(viewLifecycleOwner, Observer {
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
                viewModel.deleteWatchLaterMovie(position)
            }
        }).attachToRecyclerView(binding.savedContentRecyclerView)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}