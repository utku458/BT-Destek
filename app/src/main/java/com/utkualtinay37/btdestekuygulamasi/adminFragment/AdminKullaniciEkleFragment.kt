package com.utkualtinay37.btdestekuygulamasi.adminFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utkualtinay37.btdestekuygulamasi.R
import com.utkualtinay37.btdestekuygulamasi.databinding.FragmentAdminKullaniciEkleBinding
import com.utkualtinay37.btdestekuygulamasi.model.User
import java.util.*


class AdminKullaniciEkleFragment : Fragment() {
    private lateinit var uuid: UUID
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var _binding: FragmentAdminKullaniciEkleBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        database = Firebase.database.reference
        uuid = UUID.randomUUID()
        db = Firebase.firestore
    }


    ///removePrefix(string : String)
    //padEnd(index : Int, char : Char)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAdminKullaniciEkleBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.AdminKullaniciEkleBtn.setOnClickListener {








            var kullanici_adi =binding.textAdminUserEkleNameEditText.text.toString()
            var sifre =   binding.textAdminUserEkleSifreEditText.text.toString()
            var kullanici_id = auth.currentUser!!.uid
            var user = User(kullanici_adi,sifre,kullanici_id)
            //  var deneme =   kullanici_adi.removePrefix("@gmail.com").toString()
            // binding.textView3.setText( kullanici_adi.removeSuffix("@gmail.com"))
            val userMap = hashMapOf<String,Any>()
            userMap.put("kullanici_adi",kullanici_adi)
            userMap.put("sifre",sifre)
            userMap.put("kullanici_id",kullanici_id)



            if (kullanici_adi.isEmpty() || sifre.isEmpty()){
                Toast.makeText(requireContext(), "Kullanici adi veya sifre bos birakilamaz", Toast.LENGTH_SHORT).show()

            }else{



                auth.createUserWithEmailAndPassword(kullanici_adi,sifre).addOnCompleteListener {
                    if (it.isSuccessful){
                        db.collection("Kullanicilar").add(userMap).addOnSuccessListener {

                            Toast.makeText(requireContext(), "Kullanici basariyla eklendi", Toast.LENGTH_LONG).show()
                            val intent = Intent(requireContext(), AdminMenuActivity::class.java)
                            startActivity(intent)
                        }.addOnFailureListener {
                            Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
                        }

                        binding.textAdminUserEkleSifreEditText.setText("")
                        binding.textAdminUserEkleNameEditText.setText("")

                    }else
                    {
                        Toast.makeText(requireContext(), "Bir hata meydana geldi ", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

/*
      */




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}