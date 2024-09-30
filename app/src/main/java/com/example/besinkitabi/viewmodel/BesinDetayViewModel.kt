package com.example.besinkitabi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.besinkitabi.model.Besin
import com.example.besinkitabi.roomDb.BesinRoomDatabase
import kotlinx.coroutines.launch

class BesinDetayViewModel(application : Application) : AndroidViewModel(application){

    val besinLiveData = MutableLiveData<Besin>()

    fun roomVerisiniAl(uuid: Int) {
        viewModelScope.launch {
            val dao = BesinRoomDatabase(getApplication()).besinDao()
            val besin = dao.getBesin(uuid)
            besinLiveData.value = besin
        }
    }


}