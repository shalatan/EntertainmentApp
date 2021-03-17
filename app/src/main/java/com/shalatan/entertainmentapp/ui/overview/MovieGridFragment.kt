package com.shalatan.entertainmentapp.ui.overview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shalatan.entertainmentapp.databinding.FragmentMovieGridBinding

class MovieGridFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    val args: MovieGridFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val listNumber = args.listNumber
        Log.e("LIST",listNumber.toString())
        val binding = FragmentMovieGridBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = MovieAdapter(MovieAdapter.OnClickListener {
            findNavController().navigate(
                MovieGridFragmentDirections.actionMovieGridFragmentToDetailFragment(it)
            )
        })
        binding.moviesGridRecyclerView.adapter = adapter

        return binding.root
    }

}