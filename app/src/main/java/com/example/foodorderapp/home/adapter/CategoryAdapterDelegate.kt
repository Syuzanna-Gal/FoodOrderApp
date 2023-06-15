package com.example.foodorderapp.home.adapter

import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.coreui.util.IMAGE_CORNER_RADIUS
import com.example.domain.entity.CategoryUiEntity
import com.example.foodorderapp.databinding.ItemCategoryBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun categoryAdapterDelegate(onCategoryClick: (String) -> Unit) =
    adapterDelegateViewBinding<CategoryUiEntity, CategoryUiEntity, ItemCategoryBinding>({ layoutInflater, root ->
        ItemCategoryBinding.inflate(
            layoutInflater,
            root,
            false
        )
    }) {

        bind {
            with(binding) {
                tvCategoryName.text = item.title
                ivCategoryImage.load(item.imageUrl) {
                    transformations(RoundedCornersTransformation((IMAGE_CORNER_RADIUS * itemView.context.resources.displayMetrics.density)))
                }
                itemView.setOnClickListener {
                    onCategoryClick(item.title)
                }
            }
        }
    }