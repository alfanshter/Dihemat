package com.dihemat.myapplication.ui.profil

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
import com.dihemat.myapplication.databinding.FragmentPesananBinding
import com.dihemat.myapplication.databinding.FragmentProfilBinding
import com.dihemat.myapplication.model.LoginResponse
import com.dihemat.myapplication.model.profil.ProfilResponse
import com.dihemat.myapplication.ui.profil.toko.BuatTokoActivity
import com.dihemat.myapplication.webservice.ApiClientBackend
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.fragments.start
import splitties.toast.toast

class ProfilFragment : Fragment() {

    lateinit var binding: FragmentProfilBinding
    lateinit var sessionManager: SessionManager
    var api = ApiClientBackend.instance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profil, container, false)
        binding.lifecycleOwner = this
        sessionManager = SessionManager(requireContext().applicationContext)

        profil()
        binding.btnlogout.setOnClickListener {
            start<PickAuthActivity> { }
            requireActivity().finish()
            sessionManager.setLogin(false)
        }

        return binding.root
    }

    fun profil() {
        api.profil(sessionManager.getuid()!!).enqueue(object : Callback<ProfilResponse> {
            override fun onResponse(
                call: Call<ProfilResponse>,
                response: Response<ProfilResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    binding.txtnama.text = data!!.data!!.nama
                    if (data.data!!.alamat != null) {
                        binding.txtalamat.text = data.data.alamat.toString()
                    }
                    binding.btntokosaya.setOnClickListener {
                        if (data.data.isStatusToko == 1){
                            toast("Berhasil masuk")
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


}