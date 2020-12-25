package com.shalatan.entertainmentapp.ui.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.shalatan.entertainmentapp.databinding.FragmentMovieGridBinding

class MovieGridFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val movies = MovieGridFragmentArgs.fromBundle(requireArguments()).moviesList.toMutableList()
        val binding = FragmentMovieGridBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = MovieAdapter(MovieAdapter.OnClickListener {
            findNavController().navigate(
                MovieGridFragmentDirections.actionMovieGridFragmentToDetailFragment(
                    it
                )
            )
        })
        binding.moviesGridRecyclerView.adapter = adapter
        adapter.submitList(movies)

        return binding.root
    }

}