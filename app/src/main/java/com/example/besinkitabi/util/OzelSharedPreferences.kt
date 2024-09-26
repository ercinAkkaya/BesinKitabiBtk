package com.example.besinkitabi.util

import android.content.Context
import android.content.SharedPreferences
import kotlin.concurrent.Volatile

class OzelSharedPreferences {


    //burdaki işlem de olmasa da olur ama burda da datarace gerçekleştirdik ve bu yüzden singleton kullanılır
    companion object{

        private val TIME = "time"
        private var sharedPreferences : SharedPreferences? = null

        @Volatile
        private var instance : OzelSharedPreferences? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: ozelSharedPreferencesOlustur(context.also {
                instance
            })
        }
        private fun ozelSharedPreferencesOlustur(context : Context) : OzelSharedPreferences{
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return OzelSharedPreferences()
        }
    }

    fun zamaniKaydet(zaman : Long){
        sharedPreferences?.edit()?.putLong(TIME,zaman)?.apply()
    }
    fun zamaniAl() = sharedPreferences?.getLong(TIME, 0)
}