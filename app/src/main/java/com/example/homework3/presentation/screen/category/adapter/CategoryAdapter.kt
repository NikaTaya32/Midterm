package com.example.homework3.presentation.screen.category.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework3.databinding.ItemCategoryBinding
import com.example.homework3.presentation.screen.category.model.CategoryModel

class CategoryAdapter :
    ListAdapter<CategoryModel, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int,
    ) {
        holder.bind()
    }

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val dots = listOf(binding.dot1, binding.dot2, binding.dot3, binding.dot4)
        fun bind() = with(binding) {
            val item = getItem(bindingAdapterPosition)
            tvName.text = item.name

            dots.forEach { it.visibility = View.GONE }

            val showCount = if (item.depth > MAX_VISIBLE_DOTS) MAX_VISIBLE_DOTS else item.depth

            for (i in 0 until showCount) {
                dots[i].visibility = View.VISIBLE
            }
        }
    }

    companion object {
        private const val MAX_VISIBLE_DOTS = 4
    }
}

private class CategoryDiffCallback : DiffUtil.ItemCallback<CategoryModel>() {
    override fun areItemsTheSame(
        oldItem: CategoryModel,
        newItem: CategoryModel,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CategoryModel,
        newItem: CategoryModel,
    ): Boolean {
        return oldItem == newItem
    }
}