package com.dihemat.myapplication.ui.profil

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dihemat.myapplication.PickAuthActivity
import com.dihemat.myapplication.R
import com.dihemat.myapplication.Session.SessionManager
import com.dihemat.myapplication.Utils.Constant
import com.dihemat.myapplication.databinding.FragmentPesananBinding
import com.dihemat.myapplication.databinding.FragmentProfilBinding
import com.dihemat.myapplication.model.LoginResponse
import com.dihemat.myapplication.model.profil.ProfilResponse
import com.dihemat.myapplication.ui.profil.toko.BuatTokoActivity
import com.dihemat.myapplication.ui.profil.toko.HomeTokoActivity
import com.dihemat.myapplication.webservice.ApiClientBackend
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.fragments.start
import splitties.toast.toast

class ProfilFragment : Fragment() {

    lateinit var binding: FragmentProfilBinding
    lateinit var sessionManager: SessionManager
    var api = ApiClientBackend.instance()
    lateinit var progressdialog: ProgressDialog
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profil, container, false)
        binding.lifecycleOwner = this
        sessionManager = SessionManager(requireContext().applicationContext)
        progressdialog = ProgressDialog(requireActivity())
        loading(true)

        binding.btnlogout.setOnClickListener {
            start<PickAuthActivity> { }
            requireActivity().finish()
            sessionManager.setLogin(false)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        profil()
    }
    fun profil() {
        api.profil(sessionManager.getuid()!!).enqueue(object : Callback<ProfilResponse> {
            override fun onResponse(
                call: Call<ProfilResponse>,
                response: Response<ProfilResponse>
            ) {
                loading(false)
                if (response.isSuccessful) {
                    val data = response.body()
                    binding.txtnama.text = data!!.data!!.nama
                    if (data.data!!.namaToko!=null){
                        sessionManager.setnamatoko(data.data!!.namaToko.toString())
                    }
                    if (data.data.alamat != null) {
                        binding.txtalamat.text = data.data.alamat.toString()
                        sessionManager.setalamat(data.data.alamat.toString())
                    }

                    if (data.data.isStatusToko!=null){
                        sessionManager.setis_status_toko(data.data.isStatusToko)
                    }

                    binding.btntokosaya.setOnClickListener {
                        if (data.data.isStatusToko == 1){

                            val gson = Gson()
                            val noteJson = gson.toJson(data.data)

                            val i = Intent(
                                activity!!.baseContext,
                                HomeTokoActivity::class.java
                            )

                            i.putExtra("tokomodel", noteJson)
                            startActivity(i)

                        }else{
                            start<BuatTokoActivity> {  }
                        }
                    }
                } else {
                    splitties.toast.toast("Kesalahan response silahkan restart halaman")
                }
            }

            override fun onFailure(call: Call<ProfilResponse>, t: Throwable) {
                splitties.toast.toast("Kesalahan Jaringan silahkan restart halaman")
            }

        })
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