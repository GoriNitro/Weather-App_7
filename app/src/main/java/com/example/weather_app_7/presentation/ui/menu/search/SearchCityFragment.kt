package com.example.weather_app_7.presentation.ui.menu.search

import android.app.ActionBar
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.marginTop
import androidx.navigation.fragment.findNavController
import com.example.weather_app_7.R
import com.example.weather_app_7.databinding.FragmentSearchCityBinding

@Suppress("NAME_SHADOWING")
class SearchCityFragment : Fragment() {

    private val binding by lazy {
        FragmentSearchCityBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            val city = binding.editText.text.toString()
            if (city.isEmpty()){
                Toast.makeText(requireContext(), "Введите название города!!", Toast.LENGTH_SHORT).show()
            }else{
                val bundle = Bundle().apply {
                    putString("City", city)
                }
                findNavController().navigate(R.id.menuFragment, bundle)
            }
        }
    }
}