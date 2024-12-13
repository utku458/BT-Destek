package com.utkualtinay37.btdestekuygulamasi.adminFragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.utkualtinay37.btdestekuygulamasi.MainActivity
import com.utkualtinay37.btdestekuygulamasi.R
import com.utkualtinay37.btdestekuygulamasi.databinding.ActivityAdminMenuBinding

class AdminMenuActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityAdminMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth



        binding.apply {
            toggle= ActionBarDrawerToggle(this@AdminMenuActivity,binding.drawerLayout,
                R.string.open,
                R.string.close
            )

            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            supportActionBar?.setDisplayHomeAsUpEnabled(true)



            navView.setNavigationItemSelectedListener {

                it.isChecked = true
                when(it.itemId){

                    R.id.AdminAnaSayfaMenu ->
                    {
                        replaceFragment(AdminHomePageFragment(),it.title.toString())
                    }


                    R.id.AdminTalepGoruntuleMenu ->
                    {
                        replaceFragment(AdminTalepGoruntuleFragment(),it.title.toString())

                    }

                    R.id.AdminKullaniciEkleMenu ->
                    {
                        replaceFragment(AdminKullaniciEkleFragment(),it.title.toString())
                    }


                    R.id.AdminLogoutMenu ->
                    {
                        Firebase.auth.signOut()


                        var intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    }


                }
                true
            }

        }



    }
    private fun replaceFragment(fragment: Fragment, title:String){

        val fragmentManager= supportFragmentManager
        val fragmentTransaction =fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView,fragment)

        fragmentTransaction.commit()
        binding.drawerLayout.closeDrawers()
        setTitle(title)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){

            true
        }
        return super.onOptionsItemSelected(item)


    }
}