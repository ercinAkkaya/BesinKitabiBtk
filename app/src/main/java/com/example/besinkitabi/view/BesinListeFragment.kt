package com.example.besinkitabi.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.besinkitabi.adapte.BesinRecyclerAdapter
import com.example.besinkitabi.databinding.FragmentBesinListeBinding
import com.example.besinkitabi.service.NetworkRepository
import com.example.besinkitabi.viewmodel.BesinlisteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class BesinListeFragment : Fragment() {

    private var _binding: FragmentBesinListeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewmodel : BesinlisteViewModel
    private val besinRecyclerAdapter = BesinRecyclerAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBesinListeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel = ViewModelProvider(this)[BesinlisteViewModel::class]

        binding.besinRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.besinRecyclerView.adapter = besinRecyclerAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.besinRecyclerView.visibility = View.GONE
            binding.yuklenmeHataMesaji.visibility = View.GONE
            binding.yukleniyorProgressBar.visibility = View.VISIBLE
            viewmodel.refreshFromInternet()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()

    }

    private fun observeLiveData(){
        viewmodel.besinler.observe(viewLifecycleOwner) {
            besinRecyclerAdapter.updatebesinList(it)
            //adapterin içine besinleri vercez ve güncelleyecez
            binding.besinRecyclerView.visibility = View.VISIBLE
        }
        viewmodel.besinHataMesaji.observe(viewLifecycleOwner){
            if(it){
                binding.yuklenmeHataMesaji.visibility = View.VISIBLE
                binding.besinRecyclerView.visibility = View.GONE
            }else{
                binding.yuklenmeHataMesaji.visibility = View.GONE
            }
        }
        viewmodel.besinYukleniyor.observe(viewLifecycleOwner){
            if(it){
                binding.besinRecyclerView.visibility = View.GONE
                binding.yuklenmeHataMesaji.visibility = View.GONE
                binding.yukleniyorProgressBar.visibility = View.VISIBLE
            }else{
                binding.yukleniyorProgressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}