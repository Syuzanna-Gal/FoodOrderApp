package com.example.foodorderapp.basket.adapter

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.coreui.util.IMAGE_CORNER_RADIUS
import com.example.domain.entity.BasketItemUiEntity
import com.example.foodorderapp.R
import com.example.foodorderapp.databinding.ItemInBasketBinding
import com.example.foodorderapp.util.type_alias.RString
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun basketAdapterDelegate(
    onRemoveClick: (BasketItemUiEntity) -> Unit,
    onAddClick: (BasketItemUiEntity) -> Unit
) =
    adapterDelegateViewBinding<BasketItemUiEntity, BasketItemUiEntity, ItemInBasketBinding>({ layoutInflater, root ->
        ItemInBasketBinding.inflate(
            layoutInflater,
            root,
            false
        )
    }) {

        bind {
            with(binding) {
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