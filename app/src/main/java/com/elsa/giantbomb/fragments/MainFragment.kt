package com.elsa.giantbomb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elsa.giantbomb.adapters.GameListAdapter
import com.elsa.giantbomb.databinding.FragmentMainBinding

class MainFragment: Fragment() {

    private var dataFetched: Boolean = false;
    private lateinit var binding: FragmentMainBinding
    private val adapter = GameListAdapter()

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
            searchGameResult()
        }
        binding.btnRetry.setOnClickListener {
            searchGameResult()
        }
    }

    private fun searchGameResult() {
        val keyword = binding.searchEditText.text.toString()
    }
}