package com.dihemat.myapplication.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dihemat.myapplication.R
import com.dihemat.myapplication.databinding.FragmentDashboardBinding
import com.dihemat.myapplication.databinding.FragmentProfilBinding

class DashboardFragment : Fragment() {

    lateinit var binding : FragmentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard,container,false)
        binding.lifecycleOwner = this

        return  binding.root
    }

}