package com.elsa.giantbomb.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elsa.giantbomb.entities.LoadResult
import com.elsa.giantbomb.entities.Response
import com.elsa.giantbomb.repository.GameRepository
import kotlinx.coroutines.Job

class MainViewModel(private val repository: GameRepository): ViewModel() {

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    private val _gameList = MutableLiveData<List<Response.GameItem>>()
    private val _loading = MutableLiveData<LoadResult>()
    private var job: Job? = null
    var keyword = ""

    val gameList: LiveData<List<Response.GameItem>>
        get() = _gameList
    val loading: LiveData<LoadResult>
        get() = _loading


}