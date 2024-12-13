package com.utkualtinay37.btdestekuygulamasi.userFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utkualtinay37.btdestekuygulamasi.R
import com.utkualtinay37.btdestekuygulamasi.adapter.AdminTalepRecyclerAdapter
import com.utkualtinay37.btdestekuygulamasi.databinding.FragmentUserTalepGoruntuleBinding
import com.utkualtinay37.btdestekuygulamasi.model.UserTalep


class UserTalepGoruntuleFragment : Fragment() {
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var UserTalepAdapter: AdminTalepRecyclerAdapter
    private lateinit var userTalepList:ArrayList<UserTalep>
    private var _binding: FragmentUserTalepGoruntuleBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Firebase.firestore

        auth = Firebase.auth


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserTalepGoruntuleBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userTalepList = ArrayList()

        binding.recyclerView.layoutManager= LinearLayoutManager(requireContext())
        getData()
        UserTalepAdapter=AdminTalepRecyclerAdapter(userTalepList, this@UserTalepGoruntuleFragment)
        binding.recyclerView.adapter=UserTalepAdapter

    }

    fun getData(){

        db.collection("Talepler").addSnapshotListener { value, error ->
            if (error!=null){

                Toast.makeText(requireContext(), error.localizedMessage, Toast.LENGTH_SHORT).show()
            }else{
                if (value!=null){
                    if (!value.isEmpty){
                        val documents = value.documents

                        for (document in documents){
                            val kullanici_name = document.get("kullanici_name") as String
                            val talepid = document.get("talepid") as String
                            val kullaniciid = document.get("kullaniciid") as String
                            val talepKonu = document.get("talepKonu") as String
                            val talepIcerik = document.get("talepIcerik") as String
                            val talepBaslik = document.get("talepBaslik") as String
                            val talepKonuYazi = document.get("talepKonuYazi") as String

                            println(kullaniciid)
                            var kullaniciAdi=  kullanici_name.removeSuffix("@gmail.com")
                            val userTalep= UserTalep(talepid,kullaniciAdi,kullaniciid,talepKonu,talepIcerik,talepBaslik,talepKonuYazi)

                            if (kullanici_name.equals(auth.currentUser!!.email.toString())){
                                userTalepList.add(userTalep)
                            }




                            //println(userTalepList.get(1).talepBaslik)


                        }
                        UserTalepAdapter.notifyDataSetChanged()
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