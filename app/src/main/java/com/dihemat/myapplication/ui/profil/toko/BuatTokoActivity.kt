package com.dihemat.myapplication.ui.profil.toko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.dihemat.myapplication.R
import com.dihemat.myapplication.databinding.ActivityBuatTokoBinding
import com.dihemat.myapplication.databinding.ActivityPickAuthBinding
import splitties.activities.start

class BuatTokoActivity : AppCompatActivity() {
    lateinit var binding : ActivityBuatTokoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_buat_toko)
        binding.lifecycleOwner = this

        binding.btnback.setOnClickListener {
            finish()
        }

        binding.btnbuat.setOnClickListener {
            start<RegisterTokoActivity> {  }
        }


    }
}