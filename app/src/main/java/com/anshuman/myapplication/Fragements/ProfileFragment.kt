package com.anshuman.myapplication.Fragements

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.anshuman.myapplication.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        binding.btnbacktoMainpage.setOnClickListener{
            Toast.makeText(requireContext(), "Back to main page", Toast.LENGTH_SHORT).show()
        }

        binding.btnsetting.setOnClickListener{
            Toast.makeText(requireContext(), "Setting", Toast.LENGTH_SHORT).show()
        }
        binding.btnnotifcation.setOnClickListener{
            Toast.makeText(requireContext() , "Notification", Toast.LENGTH_SHORT).show()

        }
        binding.btnpersoninfo.setOnClickListener{

            Toast.makeText(requireContext(), "Personal Information", Toast.LENGTH_SHORT).show()
        }
        binding.btnreviews.setOnClickListener{
            Toast.makeText(requireContext(), "My Reviews", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }








}