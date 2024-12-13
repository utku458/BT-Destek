package com.utkualtinay37.btdestekuygulamasi.userFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utkualtinay37.btdestekuygulamasi.R
import com.utkualtinay37.btdestekuygulamasi.databinding.FragmentUserTalepOlusturBinding
import com.utkualtinay37.btdestekuygulamasi.model.UserTalep
import java.util.*
import kotlin.collections.ArrayList

class UserTalepOlusturFragment : Fragment() {
    private lateinit var list:ArrayList<String>
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var uuid: UUID
    private lateinit var db: FirebaseFirestore

    private var _binding: FragmentUserTalepOlusturBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uuid = UUID.randomUUID()
        auth = Firebase.auth
        database = Firebase.database.reference
        db = Firebase.firestore
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.talepOlusturGonderbtn.setOnClickListener {

            uuid = UUID.randomUUID()
            if (auth.currentUser != null ){

                var talepid= uuid.toString()
                var kullaniciid= auth.currentUser!!.uid.toString()
                var kullanici_name= auth.currentUser!!.email.toString()
                var talepKonu= binding.autoCompleteMainTextView.text.toString()
                var talepIcerik=binding.autoCompleteTextView.text.toString()
                var talepBaslik=binding.textTalepOlusturBaslikText.text.toString()
                var talepKonuYazi=binding.talepOlusturKonuText.text.toString()

                var talep  = UserTalep(talepid,kullanici_name,kullaniciid,talepKonu,talepIcerik,talepBaslik,talepKonuYazi)

                val userTalepMap = hashMapOf<String,Any>()
                userTalepMap.put("talepid",talepid)
                userTalepMap.put("kullaniciid",kullaniciid)
                userTalepMap.put("kullanici_name",kullanici_name)
                userTalepMap.put("talepKonu",talepKonu)
                userTalepMap.put("talepIcerik",talepIcerik)
                userTalepMap.put("talepBaslik",talepBaslik)
                userTalepMap.put("talepKonuYazi",talepKonuYazi)

                if (talepKonu.isEmpty() || talepIcerik.isEmpty() || talepBaslik.isEmpty() || talepKonuYazi.isEmpty() )
                {
                    Toast.makeText(requireContext(), "Lutfen bos alan birakmayiniz", Toast.LENGTH_SHORT).show()
                }
                else{


                    db.collection("Talepler").add(userTalepMap).addOnSuccessListener {
                        Toast.makeText(requireContext(), "Talebiniz Oluşturuldu", Toast.LENGTH_LONG).show()
                    }.addOnFailureListener {
                        Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
                    }

/*
                database.child("butuntalepler").setValue(talep)
                database.child("talepler").child(kullaniciid).child(uuid.toString()).setValue(talep)*/

                    binding.textTalepOlusturBaslikText.setText("")
                    binding.talepOlusturKonuText.text?.clear()

                    val intent = Intent(requireContext(),UserMenuActivity::class.java)
                    startActivity(intent)
                }
            }


        }
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserTalepOlusturBinding.inflate(inflater, container, false)


        var secilenkonu = ""
        val talepKonulari = resources.getStringArray(R.array.talepler)
        val teknikDestekKonulari = resources.getStringArray(R.array.teknik_destek)
        val yazilimKonulari = resources.getStringArray(R.array.yazilim)
        val ekipmanKonulari = resources.getStringArray(R.array.ekipman)
        val hesapKonulari = resources.getStringArray(R.array.hesap)
        val digerKonular = resources.getStringArray(R.array.diger)
        val arrayAdapter =  ArrayAdapter(requireContext(),R.layout.dropdown_item,talepKonulari)
        binding.autoCompleteMainTextView.setAdapter(arrayAdapter)



        binding.autoCompleteMainTextView.setOnItemClickListener { parent, view, position, id ->

            var secilensecim = talepKonulari.get(position)
            arrayAdapter.notifyDataSetChanged()

            if (secilensecim.equals("Teknik Destek")){
                binding.autoCompleteTextView.setText("Seciniz")
                val arrayAdaptersecond =  ArrayAdapter(requireContext(),R.layout.dropdown_item,teknikDestekKonulari)
                binding.autoCompleteTextView.setAdapter(arrayAdaptersecond)
                binding.autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
                    secilenkonu = teknikDestekKonulari.get(position).toString()

                    arrayAdaptersecond.notifyDataSetChanged()
                    arrayAdapter.notifyDataSetChanged()
                }

            }
            else if (secilensecim.equals("Yazilim")){
                binding.autoCompleteTextView.setText("Seciniz")
                val arrayAdaptersecond =  ArrayAdapter(requireContext(),R.layout.dropdown_item,yazilimKonulari)
                binding.autoCompleteTextView.setAdapter(arrayAdaptersecond)
                binding.autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
                    secilenkonu = yazilimKonulari.get(position).toString()

                    arrayAdaptersecond.notifyDataSetChanged()
                    arrayAdapter.notifyDataSetChanged()

                }
            }
            else if (secilensecim.equals("Ekipman")){
                binding.autoCompleteTextView.setText("Seciniz")
                val arrayAdaptersecond =  ArrayAdapter(requireContext(),R.layout.dropdown_item,ekipmanKonulari)
                binding.autoCompleteTextView.setAdapter(arrayAdaptersecond)
                binding.autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
                    secilenkonu = ekipmanKonulari.get(position).toString()


                }
            }
            else if (secilensecim.equals("Hesap")){
                binding.autoCompleteTextView.setText("Seciniz")
                val arrayAdaptersecond =  ArrayAdapter(requireContext(),R.layout.dropdown_item,hesapKonulari)
                binding.autoCompleteTextView.setAdapter(arrayAdaptersecond)
                binding.autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
                    secilenkonu = hesapKonulari.get(position).toString()


                }
            }
            else if (secilensecim.equals("Diger")){
                binding.autoCompleteTextView.setText("Seciniz")
                val arrayAdaptersecond =  ArrayAdapter(requireContext(),R.layout.dropdown_item,digerKonular)
                binding.autoCompleteTextView.setAdapter(arrayAdaptersecond)
                binding.autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
                    secilenkonu = digerKonular.get(position).toString()


                }
            }

        }


        binding.talepOlusturGonderbtn.setOnClickListener {



        }






        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}