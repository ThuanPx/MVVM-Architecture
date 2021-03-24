package com.thuanpx.mvvm_architecture.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.thuanpx.ktext.context.addFragmentToActivity
import com.thuanpx.mvvm_architecture.R
import com.thuanpx.mvvm_architecture.base.BaseFragment
import com.thuanpx.mvvm_architecture.databinding.FragmentHomeBinding
import com.thuanpx.mvvm_architecture.databinding.ItemHomeBinding
import com.thuanpx.mvvm_architecture.model.entity.Pokemon
import com.thuanpx.mvvm_architecture.ui.pokemonDetail.PokemonDetailFragment
import com.thuanpx.mvvm_architecture.utils.extension.generateGridLayoutAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/8/20.
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(HomeViewModel::class) {

    private val homeListAdapter by lazy {
        viewBinding.rvPokemon.generateGridLayoutAdapter(
            ItemPokemonViewHolder::class.java,
            2,
            ItemHomeBinding::inflate,
            { old: Pokemon, new: Pokemon -> old.name == new.name }
        ) { item, holder, _, _ ->
            holder.onBind(item)
        }
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initialize() {
        homeListAdapter.onItemClickListener = object : (Pokemon, Int) -> Unit {
            override fun invoke(p1: Pokemon, p2: Int) {
                activity?.addFragmentToActivity(
                    R.id.fragmentContainerView,
                    PokemonDetailFragment.newInstance(p1.name)
                )
            }
        }
        viewModel.fetchPokemons()
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
        viewModel.run {
            pokemons.observe(viewLifecycleOwner) {
                homeListAdapter.submitList(it)
            }
        }
    }
}
