package com.dihemat.myapplication.ui.profil.toko.ui.dashboard

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import com.dihemat.myapplication.R
import com.dihemat.myapplication.Session.SessionManager
import com.dihemat.myapplication.Utils.Constant
import com.dihemat.myapplication.databinding.ActivityDetailBarangTokoBinding
import com.dihemat.myapplication.databinding.ActivityTambahBarangBinding
import com.dihemat.myapplication.model.PostResponse
import com.dihemat.myapplication.model.TokoTerdekatModel
import com.dihemat.myapplication.model.produktoko.ProdukTokoModel
import com.dihemat.myapplication.ui.profil.toko.HomeTokoActivity
import com.dihemat.myapplication.webservice.ApiClientBackend
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.toast.toast
import java.io.ByteArrayOutputStream
import java.io.File

class DetailBarangTokoActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailBarangTokoBinding
    //foto
    var filePath: Uri? = null
    var data: ByteArray? = null
    private val REQUEST_PICK_IMAGE = 1
    //server
    var api = ApiClientBackend.instance()

    //loading
    lateinit var progressdialog: ProgressDialog
    lateinit var sessionManager: SessionManager
    var produkmodel : ProdukTokoModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_barang_toko)
        binding.lifecycleOwner = this
        progressdialog = ProgressDialog(this)
        sessionManager = SessionManager(this)
        val gson = Gson()
        produkmodel =
            gson.fromJson(intent.getStringExtra("produkmodel"), ProdukTokoModel::class.java)

        binding.edtnamabarang.setText(produkmodel!!.namaProduk)
        binding.edtharga.setText(produkmodel!!.harga.toString())
        binding.edtketerangan.setText(produkmodel!!.keterangan)
        Picasso.get().load(Constant.STORAGE+produkmodel!!.foto).fit().centerCrop().into(binding.gambarMakanan)

        binding.gambarMakanan.setOnClickListener {
            pilihfile()
        }

        binding.btnupdate.setOnClickListener {
            val nama = binding.edtnamabarang.text.toString().trim()
            val harga = binding.edtharga.text.toString().trim()
            val keterangan = binding.edtketerangan.text.toString().trim()

            if (nama.isNotEmpty() && harga.isNotEmpty() && keterangan.isNotEmpty()) {

                loading(true)
                editdata(it)
            } else {
                Snackbar.make(it, "Jangan kosongi kolom", 3000).show()
            }
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

    fun loading(status: Boolean) {
        if (status) {
            progressdialog.setTitle(Constant.tunggu)
            progressdialog.setCanceledOnTouchOutside(false)
            progressdialog.show()
        } else {
            progressdialog.dismiss()
        }

    }


    private fun editdata(
        view : View
    ) {

        val edtnama = binding.edtnamabarang.text.toString().trim()
        val edtharga = binding.edtharga.text.toString().trim()
        val edtketerangan = binding.edtketerangan.text.toString().trim()
        if (edtnama.isNotEmpty() && edtharga.isNotEmpty() && edtketerangan.isNotEmpty() ) {
            if (data != null) {
                val f: File = File(cacheDir, "foto")
                f.createNewFile()
                //Convert bitmap
                //ini bitmapnya

                val reqFile = RequestBody.create("image/*".toMediaTypeOrNull(), data!!)
                val body = MultipartBody.Part.createFormData("foto", f.name, reqFile)

                val nama: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    edtnama
                )

                val id: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    produkmodel!!.id.toString()
                )
                val harga: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    edtharga
                )
                val keterangan: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    edtketerangan
                )
            
                val oldIimage: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    produkmodel!!.foto.toString()
                )

                api.update_produk(body,nama,harga,keterangan,oldIimage,id)
                    .enqueue(object : Callback<PostResponse> {
                        override fun onResponse(
                            call: Call<PostResponse>,
                            response: Response<PostResponse>
                        ) {
/*                            try {*/
                            if (response.isSuccessful) {
                                loading(false)
                                toast("Edit data berhasil")
                                finish()

                            } else {
                                loading(false)
                                Log.d("Dihemat", "onResponse:  ${response.code()}")
                                Snackbar.make(view, "Silahkan coba lagi", 3000).show()
                            }
/*                            } catch (e: Exception) {
                                loading(false)
                                Log.d("Dihemat", "onResponse:  ${e.message}")

                            }*/

                        }

                        override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                            loading(false)
                            Snackbar.make(view, "Silahkan coba lagi", 3000).show()
                            Log.d("Dihemat", "onResponse:  ${t.message}")

                        }

                    })

            } else {

                val nama: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    edtnama
                )

                val id: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    produkmodel!!.id.toString()
                )
                val harga: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    edtharga
                )
                val keterangan: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    edtketerangan
                )


                api.update_produk_withoutfoto(nama,harga,keterangan,id)
                    .enqueue(object : Callback<PostResponse> {
                        override fun onResponse(
                            call: Call<PostResponse>,
                            response: Response<PostResponse>
                        ) {
/*                            try {*/
                            if (response.isSuccessful) {
                                loading(false)
                                toast("Edit data berhasil")
                                finish()

                            } else {
                                loading(false)
                                Log.d("Dihemat", "onResponse:  ${response.code()}")
                                Snackbar.make(view, "Silahkan coba lagi", 3000).show()
                            }
/*                            } catch (e: Exception) {
                                loading(false)
                                Log.d("Dihemat", "onResponse:  ${e.message}")

                            }*/

                        }

                        override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                            loading(false)
                            Snackbar.make(view, "Silahkan coba lagi", 3000).show()
                            Log.d("Dihemat", "onResponse:  ${t.message}")

                        }

                    })

            }
        }

    }


}