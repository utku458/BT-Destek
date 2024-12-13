package com.utkualtinay37.btdestekuygulamasi

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
//import com.bt_destekkk37.bt_support.userFragment.UserTalepOlusturFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import com.utkualtinay37.btdestekuygulamasi.adminFragment.AdminMenuActivity
import com.utkualtinay37.btdestekuygulamasi.databinding.ActivityMainBinding
import com.utkualtinay37.btdestekuygulamasi.userFragment.UserMenuActivity

class MainActivity : AppCompatActivity() {
    lateinit var toggle:ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth;

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.

        val currentUser = auth.currentUser





    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth
        val user = Firebase.auth.currentUser
        if (user != null) {

            if (user.uid.equals("mqk210w2HHSRKCBkUV7dPq01Fgx2")){
                var intent = Intent(this@MainActivity,AdminMenuActivity::class.java)
                startActivity(intent)
            }
            else{
                var intent = Intent(this@MainActivity,UserMenuActivity::class.java)
                startActivity(intent)
            }
        } else {
            // No user is signed in
        }



        var intent:Intent
        supportActionBar?.hide()

        binding.girisbtn.setOnClickListener {
            var kullaniciAdi = binding.textUserNameEditText.text.toString()
            var sifre = binding.textPasswordEditText.text.toString()


            if (kullaniciAdi.isEmpty() || sifre.isEmpty()){
                Toast.makeText(this@MainActivity, "Kullanici adi veya sifre bos birakilamaz", Toast.LENGTH_SHORT).show()

            }
            else{



                auth.signInWithEmailAndPassword(kullaniciAdi,sifre).addOnCompleteListener {
                    if (it.isSuccessful){
                        binding.textUserNameEditText.setText("")
                        binding.textPasswordEditText.setText("")
                        if (kullaniciAdi=="admin@gmail.com" && sifre=="73b623f2-06ce-11ee-be56-0242ac120002"){
                            intent = Intent(this@MainActivity, AdminMenuActivity::class.java)
                            startActivity(intent)
                        }else{
                            intent = Intent(this@MainActivity, UserMenuActivity::class.java)
                            startActivity(intent)
                        }

                    }else
                    {
                        Toast.makeText(this, "Boyle bir kullanici yok", Toast.LENGTH_SHORT).show()
                    }

                }



            }
        }



/*


        binding.apply {
            toggle= ActionBarDrawerToggle(this@MainActivity,binding.drawerLayout,R.string.open,R.string.close)

            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            supportActionBar?.setDisplayHomeAsUpEnabled(true)



            navView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.talepOlusturMenu->
                    {
                       var intent = Intent(this@MainActivity,UserMenuFragment::class.java)
                        startActivity(intent)
                    }


                    R.id.mesajGonderMenu->
                    {
                      /*  val action = UserMenuFragmentDirections.actionUserMenuFragmentToMesajFragment()
                        findNavController().navigate(action)*/
                    }

                    R.id.teknikArizaMenu->
                    {
                       /* val action = UserMenuFragmentDirections.actionUserMenuFragmentToTeknikArizaMenuFragment()
                        findNavController().navigate(action)*/
                    }


                }
                true
            }

        } */
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)


    }


}



