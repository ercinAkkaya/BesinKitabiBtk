package com.example.besinkitabi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.besinkitabi.model.Besin
import com.example.besinkitabi.roomDb.BesinRoomDatabase
import com.example.besinkitabi.service.NetworkService
import com.example.besinkitabi.util.OzelSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//androidviewmodel ve viewmodel paketlerinn farkı android viewmodel içinde contexte ulaşabilmemizdir
//contexte ulaşmamız gereken viewmodel classlarında androidviewmodel kullanırız

class BesinlisteViewModel(application: Application) : AndroidViewModel(application) {

    val besinler = MutableLiveData<List<Besin>>()
    val besinHataMesaji = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()

    private val networkService = NetworkService()
    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())


    private var guncellemeZamani = 10 * 60 * 1000 * 1000 * 1000L

    fun refreshData() {
        val kaydedilmeZamani = ozelSharedPreferences.zamaniAl()

        if (kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime() - kaydedilmeZamani < guncellemeZamani) {
            getDataFromRoom()
        } else {
            getDataFromInternet()
        }
    }

    fun refreshFromInternet(){
        getDataFromInternet()
    }

    private fun getDataFromRoom() {
        besinYukleniyor.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val besinListesi = BesinRoomDatabase(getApplication()).besinDao().getAllBesin()
            withContext(Dispatchers.Main) {
                besinleriGoster(besinListesi)
                Toast.makeText(getApplication(), "Besinleri Roomdan aldık", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun getDataFromInternet() {
        besinYukleniyor.value = true
        //burda dispatcher vermesek de çalışır
        viewModelScope.launch(Dispatchers.IO) {
            val besinListesi = networkService.getData()
            withContext(Dispatchers.Main) {
                besinYukleniyor.value = false
                saveToRoom(besinListesi)
                Toast.makeText(getApplication(), "Besinleri internetten aldık", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun besinleriGoster(besinListesi: List<Besin>) {

        besinler.value = besinListesi
        besinHataMesaji.value = false
        besinYukleniyor.value = false

    }

    private fun saveToRoom(besinListesi: List<Besin>) {

        viewModelScope.launch {
            val dao = BesinRoomDatabase(getApplication()).besinDao()
            dao.deleteAll()

            //burdaki döngünün amacı biz bu daoda vararg olarak tanımladığımız için bize o fonksiyon bir uuid listesi dönüyor ve bu uuidleri manuel olarak besinlere vermemiz gerekiyor

            val uuidListesi = dao.insertAllBesin(*besinListesi.toTypedArray())
            var i = 0
            while (i < besinListesi.size) {
                besinListesi[i].uuid = uuidListesi[i].toInt()
                i++
            }
            besinleriGoster(besinListesi)
        }
        ozelSharedPreferences.zamaniKaydet(System.nanoTime())

    }


}