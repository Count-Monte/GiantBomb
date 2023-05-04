package com.elsa.giantbomb.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.elsa.giantbomb.R
import com.elsa.giantbomb.adapters.GameListAdapter
import com.elsa.giantbomb.databinding.FragmentMainBinding
import com.elsa.giantbomb.entities.LoadResult
import com.elsa.giantbomb.viewModels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment: Fragment() {

    private var dataFetched: Boolean = false;
    private lateinit var binding: FragmentMainBinding
    private val adapter = GameListAdapter()
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        binding.btnSearch.setOnClickListener {
            Log.w("1", "Search Clicked")
            searchGameResult()
        }
        binding.btnRetry.setOnClickListener {
            searchGameResult()
        }

        adapter.setItemClickListener {
            Log.w("!@#","!@#");
            val bundle = Bundle().apply {
                putParcelable(DetailFragment.ARG_GAME_ITEM, it)
            }
            Log.w("debug", "Click on Item");
            Log.w("debug", bundle.toString());
            findNavController().navigate(R.id.action_detailFragment, bundle)
        }

        viewModel.gameList.observe(viewLifecycleOwner) {
            dataFetched = true
            adapter.updateKeyword(viewModel.keyword)
            adapter.submitList(it)
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it == LoadResult.LOADING
            binding.recyclerView.isVisible = it == LoadResult.SUCCESS
            binding.errorViews.isVisible = it == LoadResult.FAILURE
        }

        if (savedInstanceState == null && !dataFetched) {
            viewModel.fetchGameResult("")
        }
    }

    private fun searchGameResult() {
        val keyword = binding.searchEditText.text.toString()
        viewModel.fetchGameResult(keyword)
    }
}