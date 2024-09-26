package com.example.besinkitabi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Besin(

    //burası şuan hem network işlemlerinde hem de roomDB işlemlerinde kullanılabilir
    @ColumnInfo(name = "isim")
    @SerializedName("isim")
    val besinIsim: String?,
    @ColumnInfo(name = "kalori")
    @SerializedName("kalori")
    val besinKalori: String?,
    @ColumnInfo(name = "karbonhidrat")
    @SerializedName("karbonhidrat")
    val besinKarbonhidrat: String?,
    @ColumnInfo(name = "protein")
    @SerializedName("protein")
    val besinProtein: String?,
    @ColumnInfo(name = "yag")
    @SerializedName("yag")
    val besinYag: String?,
    @ColumnInfo(name = "gorsel")
    @SerializedName("gorsel")
    val besinGorsel: String?
){
    //burda otomatik bbir uuid oluşturduk ve default sıfır verdik ama autogenerate açık olduğu için kendi artacaktır
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
