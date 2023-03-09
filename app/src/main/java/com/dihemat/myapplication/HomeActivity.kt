package com.dihemat.myapplication

import android.app.ProgressDialog
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dihemat.myapplication.databinding.ActivityHomeBinding
import com.dihemat.myapplication.ui.dashboard.DashboardFragment
import com.dihemat.myapplication.ui.pesanan.PesananFragment
import com.dihemat.myapplication.ui.profil.ProfilFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding


    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_dashboard -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.framehome,
                        DashboardFragment()
                    ).commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_pesanan -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.framehome,
                        PesananFragment()
                    ).commit()
                    return@OnNavigationItemSelectedListener true

                }
                R.id.navigation_person -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.framehome,
                        ProfilFragment()
                    ).commit()
                    return@OnNavigationItemSelectedListener true

                }


            }

            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navView: BottomNavigationView = findViewById(R.id.nav_viewhome)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        moveToFragment(DashboardFragment())


    }

    private fun moveToFragment(fragment: Fragment) {
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.framehome, fragment)
        fragmentTrans.commit()
    }
}