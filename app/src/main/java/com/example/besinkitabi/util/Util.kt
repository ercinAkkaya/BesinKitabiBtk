package com.example.besinkitabi.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.besinkitabi.R

/*
fun String.myExtension(parametre: String){
    println(parametre)
}

 */

fun ImageView.gorselIndir(url : String, placeHolder : CircularProgressDrawable) {
    val options = RequestOptions().placeholder(placeHolder).error(R.drawable.baseline_error_24)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

fun placeHolderYap(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}