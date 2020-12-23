package com.shalatan.entertainmentapp.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.shalatan.entertainmentapp.databinding.FragmentOverviewBinding


class OverviewFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.popularRecyclerView.adapter = MovieAdapter(MovieAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })
        binding.topRatedRecyclerView.adapter = MovieAdapter(MovieAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })
        binding.upcomingRecyclerView.adapter = MovieAdapter(MovieAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })
        binding.upcomingRecyclerView2.adapter = MovieAdapter(MovieAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })

        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer {
            if (null != it) {
//                val movieCardDetailTransitionName = getString(R.string.movie_card_detail_transition_name)
//                val extras = FragmentNavigatorExtras(movie_item to movieCardDetailTransitionName)
//                val directions = OverviewFragmentDirections.actionShowDetail(it)
//                this.findNavController().navigate(directions,extras)
                this.findNavController().navigate(OverviewFragmentDirections.actionShowDetail(it))
                viewModel.displayMovieDetailsComplete()
            }
        })
        return binding.root
    }
}