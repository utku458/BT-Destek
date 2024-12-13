package com.utkualtinay37.btdestekuygulamasi.userFragment

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
import com.utkualtinay37.btdestekuygulamasi.databinding.ActivityUserMenuBinding

class UserMenuActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityUserMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth

        binding.apply {
            toggle= ActionBarDrawerToggle(this@UserMenuActivity,binding.drawerLayout,
                R.string.open,
                R.string.close
            )

            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            supportActionBar?.setDisplayHomeAsUpEnabled(true)



            navView.setNavigationItemSelectedListener {

                it.isChecked = true
                when(it.itemId){

                    R.id.UserAnaSayfaMenu ->
                    {
                        replaceFragment(UserHomePageFragment(),it.title.toString())
                    }


                    R.id.UserTalepOlusturMenu ->
                    {
                        replaceFragment(UserTalepOlusturFragment(),it.title.toString())
                    }

                    R.id.UserTalepGoruntuleMenu ->
                    {
                        replaceFragment(UserTalepGoruntuleFragment(),it.title.toString())
                    }



                    R.id.UserLogoutMenu ->
                    {
                        Firebase.auth.signOut()

                        var intent = Intent(applicationContext,
                            MainActivity::class.java)
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