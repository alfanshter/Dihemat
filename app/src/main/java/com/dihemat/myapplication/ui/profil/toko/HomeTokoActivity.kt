package com.dihemat.myapplication.ui.profil.toko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.dihemat.myapplication.R
import com.dihemat.myapplication.model.TokoTerdekatModel
import com.dihemat.myapplication.ui.profil.toko.ui.dashboard.DashboardTokoFragment
import com.dihemat.myapplication.ui.profil.toko.ui.pesanan.PesananTokoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson

class HomeTokoActivity : AppCompatActivity() {


    companion object {
        var tokomodel : TokoTerdekatModel? = null
    }
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_dashboard_toko -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.framehometoko,
                        DashboardTokoFragment()
                    ).commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_pesanan_toko -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.framehometoko,
                        PesananTokoFragment()
                    ).commit()
                    return@OnNavigationItemSelectedListener true

                }

            }

            false
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_toko)

        val gson = Gson()
        tokomodel =
            gson.fromJson(intent.getStringExtra("tokomodel"), TokoTerdekatModel::class.java)
        Log.d("anjani", "onCreate: $tokomodel ")
        val navView: BottomNavigationView = findViewById(R.id.nav_viewhome_toko)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        moveToFragment(DashboardTokoFragment())
    }

    private fun moveToFragment(fragment: Fragment) {
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.framehometoko, fragment)
        fragmentTrans.commit()
    }
}