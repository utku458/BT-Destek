package com.utkualtinay37.btdestekuygulamasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.utkualtinay37.btdestekuygulamasi.adminFragment.AdminMenuActivity
import com.utkualtinay37.btdestekuygulamasi.userFragment.UserMenuActivity

private lateinit var auth: FirebaseAuth
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)
        auth = Firebase.auth

        supportActionBar?.hide()
        Handler().postDelayed({

            val user = Firebase.auth.currentUser
            if (user != null) {

                if (user.uid.equals("mqk210w2HHSRKCBkUV7dPq01Fgx2")){
                    var intent = Intent(this@SplashScreenActivity, AdminMenuActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    var intent = Intent(this@SplashScreenActivity, UserMenuActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } else {
                val intent = Intent(this@SplashScreenActivity,
                    MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        },2000)
    }
}