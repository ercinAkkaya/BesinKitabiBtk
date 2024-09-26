package com.example.besinkitabi.roomDb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.besinkitabi.model.Besin


//burası bir veritabanı değil de bit cache gibi kullanılacağı için her veri geldiğinde bir öncekileri silip yenilerini yükleyeceğiz

@Dao
interface BesinDao {

    @Query("SELECT * FROM besin")
    suspend fun  getAllBesin() : List<Besin>

    //burdaki besin otomatik  geliyor entity tagıyla açtığımız modeller geliyor ve birini seçiyoruz
    @Query("DELETE FROM besin")
    suspend fun deleteAll()

    // burda :besinId değeri diye bir değerimiz modelimizde yok ama bu değeri suspend fonksiyon içinde kullanıcıdan istiyoruz int olarak
    @Query("SELECT * FROM besin WHERE uuid = :besinId")
    suspend fun getBesin(besinId: Int) : Besin


    //vararg bize şunuu sağlıyor: ben şuan bu fonksiyonun içinde istediğim kadar besin objesi verebilirim
    //
    @Insert
    suspend fun insertAllBesin( vararg besin: Besin) : List <Long>


}