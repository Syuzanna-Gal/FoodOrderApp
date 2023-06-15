package com.example.foodorderapp.home.dishes

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.coreui.delegate.viewBinding
import com.example.coreui.extensions.addOnTabSelectedListener
import com.example.coreui.extensions.changeTabParams
import com.example.coreui.extensions.collectWhenStarted
import com.example.coreui.extensions.dp
import com.example.coreui.util.AdaptiveSpacingItemDecoration
import com.example.coreui.util.USER_PIC_IMAGE_RADIUS
import com.example.coreui.util.USER_PIC_URL
import com.example.foodorderapp.R
import com.example.foodorderapp.core.base.BaseFragment
import com.example.foodorderapp.databinding.FragmentDishesBinding
import com.example.foodorderapp.home.adapter.dishesAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DishesFragment : BaseFragment<DishesViewModel>(R.layout.fragment_dishes) {

    private val binding by viewBinding(FragmentDishesBinding::bind)
    override val viewModel: DishesViewModel by viewModels()
    private val navArgs by navArgs<DishesFragmentArgs>()

    private val dishesAdapter by lazy {
        ListDelegationAdapter(
            dishesAdapterDelegate {
                viewModel.navigateToDishDetails(it)
            })
    }

    override fun initView() = with(binding) {
        ivUserPic.load(USER_PIC_URL) {
            transformations(RoundedCornersTransformation(USER_PIC_IMAGE_RADIUS))
        }
        tvTitle.text = navArgs.title
        rvDishes.adapter = dishesAdapter
        rvDishes.addItemDecoration(AdaptiveSpacingItemDecoration(8.dp, edgeEnabled = true))
        tlFilters.addOnTabSelectedListener(
            onTabSelected = { tab ->
                viewModel.updateDishesAccordingTag(tab.text.toString())
            }
        )

        btnBack.setOnClickListener { activity?.onBackPressedDispatcher?.onBackPressed() }
    }

    override fun initObservers() {
        collectWhenStarted(viewModel.dishesList) { list ->
            list?.let {
                dishesAdapter.items = it
                dishesAdapter.notifyDataSetChanged()
            }
        }

        collectWhenStarted(viewModel.tagsList) {
            it?.forEach { title ->
                val tab = binding.tlFilters.newTab()
                tab.text = title
                binding.tlFilters.addTab(tab)
            }
            binding.tlFilters.changeTabParams()
        }
    }
}