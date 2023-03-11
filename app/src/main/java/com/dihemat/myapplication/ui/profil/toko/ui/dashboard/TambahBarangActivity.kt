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
import androidx.databinding.DataBindingUtil
import com.dihemat.myapplication.R
import com.dihemat.myapplication.Session.SessionManager
import com.dihemat.myapplication.Utils.Constant
import com.dihemat.myapplication.databinding.ActivityBuatTokoBinding
import com.dihemat.myapplication.databinding.ActivityTambahBarangBinding
import com.dihemat.myapplication.model.PostResponse
import com.dihemat.myapplication.ui.profil.toko.BuatTokoActivity
import com.dihemat.myapplication.ui.profil.toko.HomeTokoActivity
import com.dihemat.myapplication.webservice.ApiClientBackend
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.activities.start
import splitties.toast.toast
import java.io.ByteArrayOutputStream
import java.io.File

class TambahBarangActivity : AppCompatActivity() {
    //foto
    var filePath: Uri? = null
    var data: ByteArray? = null
    private val REQUEST_PICK_IMAGE = 1


    //server
    var api = ApiClientBackend.instance()

    //loading
    lateinit var progressdialog: ProgressDialog
    lateinit var sessionManager: SessionManager
    lateinit var binding : ActivityTambahBarangBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_tambah_barang)
        binding.lifecycleOwner = this

        sessionManager = SessionManager(this)
        progressdialog = ProgressDialog(this)

        binding.gambarMakanan.setOnClickListener {
            pilihfile()
        }

        binding.btninsert.setOnClickListener {
            insertproduk(it)
        }
    }

    private fun insertproduk(view: View) {
        val f: File = File(cacheDir, "foto")
        f.createNewFile()
        //Convert bitmap
        //ini bitmapnya

        val reqFile = RequestBody.create("image/*".toMediaTypeOrNull(), data!!)
        val body = MultipartBody.Part.createFormData("foto", f.name, reqFile)

        val namaproduk = binding.edtnamabarang.text.toString().trim()
        val harga = binding.edtharga.text.toString().trim()
        val keterangan = binding.edtketerangan.text.toString().trim()

        if (namaproduk.isNotEmpty() && harga.isNotEmpty() && keterangan.isNotEmpty()  && data!=null){
            loading(true)
            val body_namaproduk: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                namaproduk
            )
            val body_harga: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                harga
            )
            val body_keterangan: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                keterangan
            )

            val body_userid: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                sessionManager.getuid().toString()
            )

            api.tambah_produk(body,body_namaproduk,body_harga,body_keterangan,body_userid).enqueue(object :
                Callback<PostResponse> {
                override fun onResponse(
                    call: Call<PostResponse>,
                    response: Response<PostResponse>
                ) {
                    try {
                        if (response.isSuccessful){
                            if (response.body()!!.status==1){
                                loading(false)
                                toast("berhasil menambahkan produk")
                                finish()

                            }else{
                                loading(false)
                                toast("Silahkan coba lagi")
                            }
                        }else{
                            loading(false)
                            toast("kesalahan response Silahkan coba lagi")
                        }
                    }catch (e :Exception){
                        loading(false)
                        Log.d("dinda", "Trycatch: ${e.message} ")
                    }
                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    loading(false)
                    Log.d("dihemat", "onFailure: ${t.message}")
                    toast("kesalahan response Silahkan coba lagi")
                }

            })


        }else {
            Snackbar.make(view,"Jangan kosongi kolom",3000).show()
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
}