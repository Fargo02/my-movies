package com.practicum.mymovies.ui.names

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.mymovies.databinding.FragmentNamesBinding
import com.practicum.mymovies.domain.models.Person
import com.practicum.mymovies.presentation.names.NamesSearchViewModel
import com.practicum.mymovies.presentation.names.NamesState
import org.koin.androidx.viewmodel.ext.android.viewModel

class NamesFragment(): Fragment() {

    private lateinit var binding: FragmentNamesBinding

    private val adapter = NamesAdapter()

    private lateinit var textWatcher: TextWatcher

    private val viewModel by viewModel<NamesSearchViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNamesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.locations.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.locations.adapter = adapter

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                viewModel.searchDebounce(
                    changedText = s?.toString() ?: ""
                )
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        textWatcher.let { binding.queryInput.addTextChangedListener(it) }

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        viewModel.observeShowToast().observe(viewLifecycleOwner) { toast ->
            showToast(toast)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        textWatcher.let { binding.queryInput.removeTextChangedListener(it) }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun render(state: NamesState) {
        when (state) {
            is NamesState.Loading -> showLoading()
            is NamesState.Content -> showContent(state.people)
            is NamesState.Error -> showError(state.message)
            is NamesState.Empty -> showEmpty(state.message)
        }
    }

    private fun showLoading() {
        binding.locations.visibility = View.GONE
        binding.placeholderMessage.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        binding.locations.visibility = View.GONE
        binding.placeholderMessage.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE

        binding.placeholderMessage.text = errorMessage
    }

    private fun showEmpty(emptyMessage: String) {
        showError(emptyMessage)
    }

    private fun showContent(people: List<Person>) {
        binding.locations.visibility = View.VISIBLE
        binding.placeholderMessage.visibility = View.GONE
        binding.progressBar.visibility = View.GONE

        adapter.people.clear()
        adapter.people.addAll(people)
        adapter.notifyDataSetChanged()
    }

    companion object {

    }
}