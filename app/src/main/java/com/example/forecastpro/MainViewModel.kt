package com.example.forecastpro

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forecastpro.api.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel: ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _currentWeather = MutableLiveData<String>()
    val currentWeather: LiveData<String>
        get() = _currentWeather

    private val _listWeather = MutableLiveData<String>()
    val listWeather: LiveData<String>
        get() = _listWeather

    init {
        loadData()
    }
    fun loadData(){
        val disposable = ApiClient.getApiService().getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("MainViewModel", it.toString())
            },{
                Log.d("MainViewModel", it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}