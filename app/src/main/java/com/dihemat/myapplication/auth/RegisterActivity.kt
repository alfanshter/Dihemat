package com.dihemat.myapplication


import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.dihemat.myapplication.R
import com.dihemat.myapplication.Session.SessionManager
import com.dihemat.myapplication.Utils.Constant
import com.dihemat.myapplication.databinding.ActivityPickAuthBinding
import com.dihemat.myapplication.databinding.ActivityRegisterBinding
import com.dihemat.myapplication.model.LoginResponse
import com.dihemat.myapplication.webservice.ApiClientBackend
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.activities.start

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    lateinit var sessionManager: SessionManager
    var api = ApiClientBackend.instance()
    lateinit var progressdialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.lifecycleOwner = this

        progressdialog = ProgressDialog(this)
        sessionManager = SessionManager(this)

        binding.btnmasuk.setOnClickListener {
            finish()
        }

        binding.btnsignup.setOnClickListener {
            signup(it)
        }


    }

    fun signup(view: View) {
        var email = binding.edtemail.text.toString().trim()
        var password = binding.edtpassword.text.toString().trim()
        var nomor_telepon = binding.edtnotelp.text.toString().trim()
        var nama = binding.edtnama.text.toString().trim()
        var confirm_password = binding.edtconfirmPassword.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty() && nomor_telepon.isNotEmpty() && nama.isNotEmpty() && confirm_password.isNotEmpty()) {
            loading(true)
            if (password == confirm_password){
                api.register(email,nama, password,nomor_telepon).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful) {
                            val data = response.body()
                            //ketika berhasil
                            if (data!!.status == 1) {
                                sessionManager.setLogin(true)
                                sessionManager.setid(data.loginModel!!.id!!)
                                start<HomeActivity> { }
                                finish()
                            }
                            //ketika user sudah terdaftar
                            else if (data.status == 501) {
                                loading(false)
                                Snackbar.make(view, "Email sudah terdaftar", 3000).show()
                            }

                        } else {
                            loading(false)
                            Snackbar.make(view, "Kesalahan response", 3000).show()

                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        loading(false)
                        Snackbar.make(view, "Kesalahan jaringan", 3000).show()

                    }

                })

            }else{
                loading(false)
                Snackbar.make(view, "Password tidak sama", 3000).show()

            }
         } else {
            loading(false)
            Snackbar.make(view, "Jangan kosongi kolom", 3000).show()
        }
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