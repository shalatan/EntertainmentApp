package com.shalatan.entertainmentapp.ui.overview.moviegrid

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
import com.shalatan.entertainmentapp.ui.overview.MovieAdapter
import com.shalatan.entertainmentapp.ui.overview.OverviewViewModel

class MovieGridFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }
//
//    private lateinit var viewModel: MovieGridViewModel
    val args: MovieGridFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        val tmdbApiService = TmdbApi.RETROFIT_SERVICE
//        val repository = MovieGridRepository(tmdbApiService)
//        val factory = MovieGridViewModelFactory(repository)
//        viewModel = ViewModelProvider(this,factory).get(MovieGridViewModel::class.java)
//        viewModel.searchMovies()
//        viewModel.movies.observe(viewLifecycleOwner, Observer {
//            Log.e("FRAGMENT Paging Data",it.toString())
//        })
//
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