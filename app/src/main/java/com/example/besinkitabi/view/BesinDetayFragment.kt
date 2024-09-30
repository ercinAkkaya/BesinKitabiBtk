package com.example.besinkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.besinkitabi.databinding.FragmentBesinDetayBinding
import com.example.besinkitabi.util.gorselIndir
import com.example.besinkitabi.util.placeHolderYap
import com.example.besinkitabi.viewmodel.BesinDetayViewModel


class BesinDetayFragment : Fragment() {

    private var _binding: FragmentBesinDetayBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewmodel: BesinDetayViewModel
    private var besinId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBesinDetayBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel = ViewModelProvider(this)[BesinDetayViewModel::class.java]

        arguments?.let {
            besinId = BesinDetayFragmentArgs.fromBundle(it).besinId
        }

        viewmodel.roomVerisiniAl(besinId)

        observeLivedata()

    }

    private fun observeLivedata() {
        viewmodel.besinLiveData.observe(viewLifecycleOwner) {
            binding.besinIsimTxt.text = it.besinIsim.toString()
            binding.besinKaloriTxt.text = it.besinKalori
            binding.besinKarbonhidratTxt.text = it.besinKarbonhidrat
            binding.besinProteinTxt.text = it.besinProtein
            binding.besinYaTxt.text = it.besinYag
            it.besinGorsel?.let { it1 ->
                binding.besinImage.gorselIndir(
                    it1,
                    placeHolderYap(requireContext()))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}