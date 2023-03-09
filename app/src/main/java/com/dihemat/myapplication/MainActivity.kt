package com.dihemat.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dihemat.myapplication.Session.SessionManager
import splitties.activities.start

class MainActivity : AppCompatActivity() {
    lateinit var handler : Handler
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sessionManager = SessionManager(this)
        handler = Handler()
        if (sessionManager.getLogin()==true){
            handler = Handler()
            handler.postDelayed({
                start<HomeActivity> {  }
                finish()
            }, 1000)
        }else{
            handler = Handler()
            handler.postDelayed({
                start<PickAuthActivity> {  }
                finish()
            }, 1000)
        }
    }
}