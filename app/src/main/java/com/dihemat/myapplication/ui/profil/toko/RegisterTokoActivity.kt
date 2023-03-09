package com.dihemat.myapplication.ui.profil.toko

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.databinding.DataBindingUtil
import com.dihemat.myapplication.R
import com.dihemat.myapplication.databinding.ActivityRegisterBinding
import com.dihemat.myapplication.databinding.ActivityRegisterTokoBinding
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class RegisterTokoActivity : AppCompatActivity() {
    //foto
    var filePath: Uri? = null
    var data: ByteArray? = null
    private val REQUEST_PICK_IMAGE = 1
    lateinit var binding: ActivityRegisterTokoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_toko)
        binding.lifecycleOwner = this


        binding.gambarMakanan.setOnClickListener {
            pilihfile()
        }

    }

    private fun pilihfile() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_PICK_IMAGE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PICK_IMAGE) {
                filePath = data?.data
                Picasso.get().load(filePath).fit().centerCrop().into(binding.gambarMakanan)
                convert()
            }
        }
    }


    fun convert() {
        val bmp = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 80, baos)
        data = baos.toByteArray()

    }



}