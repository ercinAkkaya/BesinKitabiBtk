package com.example.besinkitabi.roomDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.besinkitabi.model.Besin
import kotlin.concurrent.Volatile


@Database(entities = [Besin::class], version = 1)
abstract class BesinRoomDatabase : RoomDatabase() {
    abstract fun besinDao(): BesinDao

    //burası bu uygulama için gereksiz ama burda yapılan yapını adı DataRace
    //bu yapı aynı anda aynı databaseye yapılacak şilemleri engellliyor ve işlemlerin senkron bir şekilde olmasını sağlıyor
    companion object {

        @Volatile
        private var instance : BesinRoomDatabase? = null

        private val lock = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(lock){
            instance ?: databaseOlustur(context).also {
                instance = it
            }
        }

        private fun  databaseOlustur(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BesinRoomDatabase::class.java,
            "BesinRoomDatabase"
        ).build()
    }



}

