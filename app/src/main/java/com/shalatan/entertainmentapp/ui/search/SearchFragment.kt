package com.shalatan.entertainmentapp.ui.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.shalatan.entertainmentapp.NavGraphDirections
import com.shalatan.entertainmentapp.databinding.FragmentSearchBinding
import com.shalatan.entertainmentapp.ui.overview.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    val viewModel: SearchViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchEditText: TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater)
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchedMoviesFlow.collect {
                adapter.submitList(it)
                binding.emptyImage.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * hide soft-keyboard
     */
    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}