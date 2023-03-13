package com.dihemat.myapplication.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.dihemat.myapplication.R
import com.dihemat.myapplication.databinding.ActivityDetailCartBinding
import com.dihemat.myapplication.databinding.ActivitySudahProsesBinding

class SudahProsesActivity : AppCompatActivity() {
    lateinit var binding : ActivitySudahProsesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sudah_proses)
        binding.lifecycleOwner = this

        binding.btnback.setOnClickListener {
            finish()
        }

    }
}