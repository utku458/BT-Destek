package com.utkualtinay37.btdestekuygulamasi.adminFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utkualtinay37.btdestekuygulamasi.R
import com.utkualtinay37.btdestekuygulamasi.adapter.AdminTalepRecyclerAdapter
import com.utkualtinay37.btdestekuygulamasi.databinding.FragmentAdminTalepGoruntuleBinding
import com.utkualtinay37.btdestekuygulamasi.model.UserTalep


class AdminTalepGoruntuleFragment : Fragment() {
    var retainedChildFragmentManager: FragmentManager? = null
    private var _binding: FragmentAdminTalepGoruntuleBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore
    private lateinit var AdminTalepAdapter: AdminTalepRecyclerAdapter
    private lateinit var database: DatabaseReference
    private lateinit var userTalepList:ArrayList<UserTalep>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database.reference.child("butuntalepler")
        db = Firebase.firestore
        userTalepList = ArrayList()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAdminTalepGoruntuleBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity



        userTalepList = ArrayList()
        binding.recyclerViewAdminTalep.layoutManager= LinearLayoutManager(requireContext())
        getData()
        AdminTalepAdapter=AdminTalepRecyclerAdapter(userTalepList,this@AdminTalepGoruntuleFragment)
        binding.recyclerViewAdminTalep.adapter=AdminTalepAdapter


    }


    // binding.textView4.setText(userTalepList.get(0).talepKonu)



    fun getData(){

        db.collection("Talepler").addSnapshotListener { value, error ->
            if (error!=null){

                Toast.makeText(requireContext(), error.localizedMessage, Toast.LENGTH_SHORT).show()
            }else{
                if (value!=null){
                    if (!value.isEmpty){
                        val documents = value.documents

                        for (document in documents){
                            val talepid = document.get("talepid") as String
                            val kullanici_name = document.get("kullanici_name") as String
                            val kullaniciid = document.get("kullaniciid") as String
                            val talepKonu = document.get("talepKonu") as String
                            val talepIcerik = document.get("talepIcerik") as String
                            val talepBaslik = document.get("talepBaslik") as String
                            val talepKonuYazi = document.get("talepKonuYazi") as String

                            println(kullaniciid)
                            var kullaniciAdi=  kullaniciid.removeSuffix("@gmail.com")
                            val userTalep= UserTalep(talepid,kullanici_name.removeSuffix("@gmail.com"),kullaniciid,talepKonu,talepIcerik,talepBaslik,talepKonuYazi)
                            userTalepList.add(userTalep)
                            //println(userTalepList.get(1).talepBaslik)


                        }
                        AdminTalepAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}