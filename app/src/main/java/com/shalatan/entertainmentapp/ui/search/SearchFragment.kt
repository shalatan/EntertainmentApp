package com.shalatan.entertainmentapp.ui.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.shalatan.entertainmentapp.NavGraphDirections
import com.shalatan.entertainmentapp.databinding.FragmentSearchBinding
import com.shalatan.entertainmentapp.ui.overview.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchEditText: TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        searchEditText = binding.searchTextField

        searchEditText.setEndIconOnClickListener {
            val query = searchEditText.editText?.text.toString()
            if (query.isNotEmpty()) {
                it.hideKeyboard()
                viewModel.makeQuery(query)
            }
        }

        val searchRecyclerView = binding.recyclerView
        val adapter = MovieAdapter(MovieAdapter.OnClickListener {
            findNavController().navigate(NavGraphDirections.actionGlobalDetailFragment(it))
        })
        searchRecyclerView.adapter = adapter
        viewModel.searchedMovies.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }

    /**
     * hide soft-keyboard
     */
    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}