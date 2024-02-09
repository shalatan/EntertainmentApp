package com.shalatan.entertainmentapp.ui.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shalatan.entertainmentapp.databinding.FragmentWatchlistBinding
import com.shalatan.entertainmentapp.ui.ui.theme.EntertainmentAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchlistFragment : Fragment() {

    private val viewModel: WatchlistViewModel by viewModels()

    private var _binding: FragmentWatchlistBinding? = null
    private val binding get() = _binding!!

    private var destination = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        destination = WatchlistFragmentArgs.fromBundle(requireArguments()).destination
        viewModel.fetchWatchLaterMovies(destination)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWatchlistBinding.inflate(inflater).apply {
            composeView.setContent {
                EntertainmentAppTheme {
                    SavedMoviesScreen()
                }
            }
        }

        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner) {
            if(it!=null){
                findNavController().navigate(WatchlistFragmentDirections.actionGlobalDetailFragment(it!!))
            }
        }

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        viewModel.displayMovieDetailsCompleted()
    }

    @Preview(showBackground = true)
    @Composable
    fun WatchlistFragmentPreview() {
        EntertainmentAppTheme {
            SavedMoviesScreen()
        }
    }

    @Composable
    fun SavedMoviesScreen(watchlistViewModel: WatchlistViewModel = viewModel()) {
        Scaffold(topBar = {
            SavedMoviesTopAppBar(header = watchlistViewModel.destinationHeading.collectAsState().value)
        }) { contentPadding ->
            SavedMoviesList(
                watchlistViewModel, modifier = Modifier.padding(contentPadding)
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SavedMoviesTopAppBar(header: String, modifier: Modifier = Modifier) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = header,
                    style = MaterialTheme.typography.displayLarge,
                )
            }, modifier = modifier
        )
    }

    @Composable
    fun SavedMoviesList(
        watchlistViewModel: WatchlistViewModel, modifier: Modifier = Modifier
    ) {
        val watchedMovies by watchlistViewModel.savedMoviesFlow.collectAsState()
        LazyColumn(modifier = modifier) {
            items(watchedMovies) { item ->
                SavedItem(
                    destination = destination,
                    savedMovie = item,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    onClick = {
                        watchlistViewModel.displayMovieDetails(it)
                    }
                )
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}