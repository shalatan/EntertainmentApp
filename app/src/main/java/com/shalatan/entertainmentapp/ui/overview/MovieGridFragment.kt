package com.shalatan.entertainmentapp.ui.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.shalatan.entertainmentapp.R
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

        val binding = FragmentMovieGridBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.moviesGridRecyclerView.adapter = MovieAdapter(MovieAdapter.OnClickListener{
//            findNavController().navigate(Dire)
        })

        return binding.root
    }

}