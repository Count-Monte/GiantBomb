package com.elsa.giantbomb.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elsa.giantbomb.entities.DataResult
import com.elsa.giantbomb.entities.LoadResult
import com.elsa.giantbomb.entities.Response
import com.elsa.giantbomb.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    fun fetchGameResult(keyword: String) {
        job?.cancel()

        this.keyword = keyword
        job = viewModelScope.launch {
            _loading.value = LoadResult.LOADING
            withContext(Dispatchers.IO) {
                when (val result = repository.getGameResult(keyword)) {
                    is DataResult.Success -> {
                        Log.w(TAG, "Data: " + result.data)
                        _gameList.postValue(result.data)
                        _loading.postValue(LoadResult.SUCCESS)
                    }
                    is DataResult.Failure -> {
                        Log.w(TAG, "Error: " + result.exception)
                        _loading.postValue(LoadResult.FAILURE)
                    }
                }
            }
        }
    }

}