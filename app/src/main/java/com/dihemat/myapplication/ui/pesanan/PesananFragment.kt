package com.dihemat.myapplication.ui.pesanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dihemat.myapplication.R
import com.dihemat.myapplication.databinding.FragmentPesananBinding

class PesananFragment : Fragment() {

    lateinit var binding : FragmentPesananBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pesanan,container,false)
        binding.lifecycleOwner = this

        return  binding.root

    }

}