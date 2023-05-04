package com.elsa.giantbomb.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.elsa.giantbomb.R
import com.elsa.giantbomb.databinding.FragmentDetailBinding
import com.elsa.giantbomb.entities.Response
import com.elsa.giantbomb.utility.onEndLoading
import com.elsa.giantbomb.utility.swapVisibility

class DetailFragment : Fragment() {
    companion object {
        const val ARG_GAME_ITEM = "arg_game_item"
    }

    private lateinit var binding: FragmentDetailBinding
    private var gameItem: Response.GameItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gameItem = arguments?.getParcelable(ARG_GAME_ITEM)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater)

        showGameDetail()
        return binding.root
    }

    private fun showGameDetail() {
        gameItem?.let {
            binding.name.text = it.name
            binding.dateAdded.text = it.dateAdded
            binding.dateLastUpdated.text = it.dateLastUpdated

            Glide.with(requireContext())
                .load(it.image.mediumUrl)
                .error(ColorDrawable(Color.GREEN))
                .fallback(ColorDrawable(Color.GREEN))
                .onEndLoading {
                    swapVisibility(
                        500,
                        binding.image,
                        binding.loadingLayout
                    )
                }
                .into(binding.image)

            if (it.description.isNullOrBlank()) {
                binding.description.text = getString(R.string.no_description)
            } else {
                binding.description.text = Html.fromHtml(
                    it.description,
                    Html.FROM_HTML_MODE_LEGACY
                )
            }
        }

    }
}