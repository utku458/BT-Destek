package com.utkualtinay37.btdestekuygulamasi.adminFragment

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentResultListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utkualtinay37.btdestekuygulamasi.R
import com.utkualtinay37.btdestekuygulamasi.databinding.FragmentAdminTalepDetayBinding
import com.utkualtinay37.btdestekuygulamasi.model.UserTalep


class AdminTalepDetayFragment : Fragment() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth;
    lateinit var kullaniciadi:String
    private lateinit var userTalepList:ArrayList<UserTalep>
    private lateinit var talepid:String
    private lateinit var receiverid:String
    private lateinit var icerik:String
    private var _binding: FragmentAdminTalepDetayBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Firebase.firestore
        auth = Firebase.auth
        userTalepList = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminTalepDetayBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.setFragmentResultListener("datafrom1",this, FragmentResultListener { requestKey, result ->
            receiverid = result.getString("receiverid").toString()
            kullaniciadi = result.getString("kullaniciadi").toString()
            binding.adminTalepDetayOlusturanTxt.setText(kullaniciadi)
            val konu = result.getString("konu")
            binding.adminTalepDetayKonuTxt.setText(konu)
            icerik = result.getString("icerik").toString()
            binding.adminTalepDetayIcerikTxt.setText(icerik)
            val konudetay = result.getString("konudetay")
            binding.AdminTalepDetayKonuDetayText.setText(konudetay)
            talepid = result.getString("talepid").toString()







        })

        binding.silbtn.setOnClickListener {

            val builder: AlertDialog.Builder= AlertDialog.Builder(requireContext())

            builder.setTitle("Emin Misiniz")
            builder.setMessage("Bu Talebi kapatmak/silmek istediginize emin misiniz")


            builder.setPositiveButton("Evet", DialogInterface.OnClickListener { dialog, which ->
                getData()

                val intent = Intent(requireContext(), AdminMenuActivity::class.java)
                startActivity(intent)
            })

            builder.setNegativeButton("Hayir", DialogInterface.OnClickListener { dialog, which ->

            })

            val alertDialog: AlertDialog =builder.create()

            alertDialog.show()

        }

        binding.messagebtn.setOnClickListener {



            val activity = it!!.context as AppCompatActivity
            val MessageFragment = AdminMesajFragment()

            val resultformessage = Bundle()

            resultformessage.putString("icerik",icerik)
            resultformessage.putString("aliciname",kullaniciadi)
            resultformessage.putString("kullaniciid",auth.currentUser!!.uid.toString())
            resultformessage.putString("kullanicimail",auth.currentUser!!.email.toString())
            resultformessage.putString("talepid",talepid)
            resultformessage.putString("receiverid",receiverid.toString())

            fragmentManager?.setFragmentResult("datafrom2",resultformessage)

            activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,MessageFragment).commit()
        }
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
                            val talepidd = document.get("talepid") as String
                            val kullaniciid = document.get("kullaniciid") as String
                            val talepKonu = document.get("talepKonu") as String
                            val talepIcerik = document.get("talepIcerik") as String
                            val talepBaslik = document.get("talepBaslik") as String
                            val talepKonuYazi = document.get("talepKonuYazi") as String

                            println(kullaniciid)
                            var kullaniciAdi=  kullaniciid.removeSuffix("@gmail.com")
                            val userTalep= UserTalep(talepid,kullanici_name,kullaniciid,talepKonu,talepIcerik,talepBaslik,talepKonuYazi)
                            userTalepList.add(userTalep)

                            for (talepler in userTalepList){ }


                            if (talepidd.equals(talepid)){

                                db.collection("Talepler").document(document.id).delete().addOnSuccessListener {


                                    Toast.makeText(requireContext(), "Talep basariyla silindi", Toast.LENGTH_SHORT).show()
                                }
                            }






                            //println(userTalepList.get(1).talepBaslik)


                        }

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