package com.example.foodorderapp.basket.adapter

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.domain.entity.BasketItemUiEntity
import com.example.foodorderapp.R
import com.example.foodorderapp.databinding.ItemInBasketBinding
import com.example.foodorderapp.util.RString
import me.ibrahimyilmaz.kiel.adapterOf
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder

class BasketItemsAdapter(
    private val onRemoveClick: (BasketItemUiEntity) -> Unit,
    private val onAddClick: (BasketItemUiEntity) -> Unit
) {

    companion object {
        private const val IMAGE_CORNER_RADIUS = 10F
    }

    val adapter = adapterOf<BasketItemUiEntity> {
        diff(
            areItemsTheSame = { old, new ->
                old.id == new.id
            },
            areContentsTheSame = { old, new ->
                old == new
            }
        )
        register(
            layoutResource = R.layout.item_in_basket,
            viewHolder = {
                CategoryViewHolder(it, onRemoveClick, onAddClick)
            },
            onBindViewHolder = { vh, _, p -> vh.onBind(p) }
        )
    }

    class CategoryViewHolder(
        view: View,
        private val onRemoveClick: (BasketItemUiEntity) -> Unit,
        private val onAddClick: (BasketItemUiEntity) -> Unit
    ) : RecyclerViewHolder<BasketItemUiEntity>(view) {

        private val binding = ItemInBasketBinding.bind(view)

        fun onBind(item: BasketItemUiEntity) = with(binding) {
            tvName.text = item.name
            ivDish.load(item.imageUrl) {
                transformations(RoundedCornersTransformation((IMAGE_CORNER_RADIUS * itemView.context.resources.displayMetrics.density)))
            }

            btnAdd.setOnClickListener {
                onAddClick(item)
            }

            btnRemove.setOnClickListener {
                onRemoveClick(item)
            }

            val priceAndWeightTitle =
                itemView.context.getString(RString.price_and_weight)
                    .format(item.price.toInt().toString(), item.weight.toInt().toString())
            val outPutColoredText: Spannable = SpannableString(priceAndWeightTitle)
            outPutColoredText.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(itemView.context, R.color.black)),
                0,
                priceAndWeightTitle.indexOf("₽") + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            tvPriceAndWeight.text = outPutColoredText
            tvAmount.text = item.quantity.toString()
        }
    }
}