package com.example.foodorderapp.home.adapter

import android.view.View
import coil.load
import com.example.domain.entity.DishUiEntity
import com.example.foodorderapp.R
import com.example.foodorderapp.databinding.ItemDishBinding
import me.ibrahimyilmaz.kiel.adapterOf
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder

class DishesAdapter(
    private val onDishClick: (id: DishUiEntity) -> Unit
) {

    val adapter = adapterOf<DishUiEntity> {
        diff(
            areItemsTheSame = { old, new ->
                old.id == new.id
            },
            areContentsTheSame = { old, new ->
                old == new
            }
        )
        register(
            layoutResource = R.layout.item_dish,
            viewHolder = {
                DishViewHolder(it, onDishClick)
            },
            onBindViewHolder = { vh, _, p -> vh.onBind(p) }
        )
    }

    class DishViewHolder(
        view: View,
        private val onDishClick: (id: DishUiEntity) -> Unit
    ) : RecyclerViewHolder<DishUiEntity>(view) {

        private val binding = ItemDishBinding.bind(view)

        fun onBind(item: DishUiEntity) = with(binding) {
            tvName.text = item.name
            ivDish.load(item.imageUrl)
            itemView.setOnClickListener {
                onDishClick(item)
            }
        }
    }
}