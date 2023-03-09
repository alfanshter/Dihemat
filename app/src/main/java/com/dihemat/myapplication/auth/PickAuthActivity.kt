package com.dihemat.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.dihemat.myapplication.databinding.ActivityPickAuthBinding
import splitties.activities.start

class PickAuthActivity : AppCompatActivity() {
    lateinit var binding : ActivityPickAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_pick_auth)
        binding.lifecycleOwner = this

        binding.btnsignup.setOnClickListener {
            start<RegisterActivity> {  }
        }

        binding.btnmasuk.setOnClickListener {
            start<LoginActivity> {  }
        }


    }
}