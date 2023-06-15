package com.example.foodorderapp.home.adapter

import coil.load
import com.example.domain.entity.DishUiEntity
import com.example.foodorderapp.databinding.ItemDishBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun dishesAdapterDelegate(onDishClick: (DishUiEntity) -> Unit) =
    adapterDelegateViewBinding<DishUiEntity, DishUiEntity, ItemDishBinding>({ layoutInflater, root ->
        ItemDishBinding.inflate(
            layoutInflater,
            root,
            false
        )
    }) {

        bind {
            with(binding) {
                tvName.text = item.name
                ivDish.load(item.imageUrl)
                itemView.setOnClickListener {
                    onDishClick(item)
                }
            }
        }
    }