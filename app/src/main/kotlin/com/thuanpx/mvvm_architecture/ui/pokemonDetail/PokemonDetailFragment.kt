package com.thuanpx.mvvm_architecture.ui.pokemonDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.thuanpx.ktext.context.goBackFragment
import com.thuanpx.ktext.context.withArgs
import com.thuanpx.mvvm_architecture.common.base.BaseFragment
import com.thuanpx.mvvm_architecture.databinding.FragmentPokemonDetailBinding
import com.thuanpx.mvvm_architecture.utils.extension.clicks
import dagger.hilt.android.AndroidEntryPoint

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/16/20.
 */
@AndroidEntryPoint
class PokemonDetailFragment :
    BaseFragment<PokemonDetailViewModel, FragmentPokemonDetailBinding>(PokemonDetailViewModel::class) {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPokemonDetailBinding {
        return FragmentPokemonDetailBinding.inflate(inflater, container, false)
    }

    override fun initialize() {
        viewBinding.arrow.clicks {
            activity?.goBackFragment()
        }
        viewModel.fetchPokemon(arguments?.getString(EXTRA_NAME) ?: return)
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
        viewModel.run {
            pokemonInfo.observe(viewLifecycleOwner) {
                viewBinding.name.text = it.name
                viewBinding.index.text = it.id.toString()
                Glide.with(requireContext())
                    .load(it.getImageUrl())
                    .listener(
                        GlidePalette.with(it.getImageUrl())
                            .use(BitmapPalette.Profile.MUTED_LIGHT)
                            .intoCallBack { palette ->
                                palette?.dominantSwatch?.rgb?.let { rgb ->
                                    viewBinding.header.setBackgroundColor(rgb)
                                }
                            }
                            .crossfade(true))
                    .into(viewBinding.image)
            }
        }
    }

    companion object {
        private const val EXTRA_NAME = "EXTRA_NAME"
        fun newInstance(name: String?) = PokemonDetailFragment().withArgs {
            putString(EXTRA_NAME, name)
        }
    }
}
