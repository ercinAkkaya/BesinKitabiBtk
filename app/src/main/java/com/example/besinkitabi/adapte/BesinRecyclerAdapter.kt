package com.example.besinkitabi.adapte

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.besinkitabi.databinding.BesinRecyclerRowBinding
import com.example.besinkitabi.model.Besin
import com.example.besinkitabi.util.gorselIndir
import com.example.besinkitabi.util.placeHolderYap
import com.example.besinkitabi.view.BesinListeFragmentDirections

class BesinRecyclerAdapter(val besinListesi : ArrayList<Besin>) : RecyclerView.Adapter<BesinRecyclerAdapter.BesinViewHolder>() {


    class BesinViewHolder(val binding : BesinRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val binding = BesinRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return BesinViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatebesinList(newBesinList : List<Besin>){
        besinListesi.clear()
        besinListesi.addAll(newBesinList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {
        holder.binding.besinIsimTxt.text = besinListesi[position].besinIsim
        holder.binding.besinKaloriTxt.text = besinListesi[position].besinKalori

        holder.itemView.setOnClickListener{
            val action = BesinListeFragmentDirections.actionBesinListeFragment2ToBesinDetayFragment2(besinListesi[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

        besinListesi[position].besinGorsel?.let { holder.binding.besinImageView.gorselIndir(it, placeHolderYap(holder.itemView.context)) }

    }

}