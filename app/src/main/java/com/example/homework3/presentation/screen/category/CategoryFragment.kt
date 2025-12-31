package com.example.homework3.presentation.screen.category

import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework3.R
import com.example.homework3.databinding.FragmentCategoryBinding
import com.example.homework3.presentation.common.BaseFragment
import com.example.homework3.presentation.extension.collectFlow
import com.example.homework3.presentation.extension.showSnackBar
import com.example.homework3.presentation.screen.category.adapter.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate) {

    private val viewModel: CategoryViewModel by viewModels()
    private val adapter by lazy { CategoryAdapter() }

    override fun bind() {
        setupRecyclerView()
        observe()
    }

    override fun listeners(): Unit = with(binding) {
        etSearch.doAfterTextChanged { text ->
            viewModel.onEvent(CategoryEvent.SearchQueryChanged(text.toString()))
        }
    }

    private fun setupRecyclerView() = with(binding) {
        rvCategories.adapter = adapter
        rvCategories.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observe() = with(binding) {
        collectFlow(viewModel.state) {
            handleState(it)
        }

        collectFlow(viewModel.sideEffect) { effect ->
            when (effect) {
                is CategorySideEffect.ShowError ->
                    root.showSnackBar(getString(R.string.something_went_wrong))
            }
        }
    }

    private fun handleState(state: CategoryState) = with(binding) {
        adapter.submitList(state.categories)
        progressBar.isVisible = state.isLoading
    }
}