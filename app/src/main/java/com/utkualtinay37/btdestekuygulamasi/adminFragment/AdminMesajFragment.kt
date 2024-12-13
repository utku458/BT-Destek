package com.utkualtinay37.btdestekuygulamasi.adminFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utkualtinay37.btdestekuygulamasi.R
import com.utkualtinay37.btdestekuygulamasi.adapter.ChatRecyclerAdapter
import com.utkualtinay37.btdestekuygulamasi.databinding.FragmentAdminMesajBinding
import com.utkualtinay37.btdestekuygulamasi.model.Message
import com.utkualtinay37.btdestekuygulamasi.model.UserTalep
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class AdminMesajFragment : Fragment() {
    private lateinit var  talepid:String
    private lateinit var messageList:ArrayList<Message>
    private lateinit var auth: FirebaseAuth
    private lateinit var SenderRoom: String
    private lateinit var RecieverRoom: String
    private lateinit var mDbRef: DatabaseReference
    private lateinit var recieveruid: String
    private lateinit var senderuid: String

    private lateinit var db: FirebaseFirestore
    private lateinit var talep_id: String
    private lateinit var userTalepList:ArrayList<UserTalep>
    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter : ChatRecyclerAdapter
    var receiverRoom : String? = null
    var senderRoom : String? = null
    private var _binding: FragmentAdminMesajBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        messageList= ArrayList()

        activity?.actionBar?.title="Message"

        firestore= Firebase.firestore
        auth = Firebase.auth


        mDbRef= FirebaseDatabase.getInstance().getReference()




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminMesajBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= ChatRecyclerAdapter()
        binding.recyclerView2.adapter = adapter
        binding.recyclerView2.layoutManager= LinearLayoutManager(requireContext())
        val adminid="mqk210w2HHSRKCBkUV7dPq01Fgx2"
        requireFragmentManager().setFragmentResultListener("datafrom2",this, FragmentResultListener { requestKey, result ->
            talep_id  = result.getString("talepid").toString()
            recieveruid = result.getString("receiverid").toString()
            val kullanicimail = result.getString("kullanicimail")
            val icerik = result.getString("icerik")
            var aliciname= result.getString("aliciname").toString()
            activity?.setTitle(aliciname.removeSuffix("@gmail.com")+" /" + icerik)
            senderuid= FirebaseAuth.getInstance().currentUser!!.uid.toString()

            senderRoom = recieveruid+talep_id+adminid
            receiverRoom=adminid+talep_id+recieveruid





            mDbRef.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapshot in snapshot.children){

                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                        adapter.messages = messageList
                    }
                    adapter.notifyDataSetChanged()

                }


                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })










            //binding.AdminMesajKutusu.setText(kullaniciid)

        })













        binding.button.setOnClickListener {
            if (binding.AdminMesajKutusuEdtText.text.isEmpty()){

            }
            else{


                auth.currentUser?.let {

                    val dateFormat: DateFormat = SimpleDateFormat("HH:mm:ss")
                    val datee = Date()
                    val dateformatted: String = dateFormat.format(datee)
                    println(dateformatted)

                    val user = it.email.toString()
                    val useruid = auth.currentUser!!.uid.toString()
                    val chatText = binding.AdminMesajKutusuEdtText.text.toString()
                    val date = FieldValue.serverTimestamp()
                    val mesajzamani = dateformatted.toString()


                    var messageObject = Message(user,chatText,mesajzamani,senderuid)



                    mDbRef.child("chats").child(senderRoom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                        mDbRef.child("chats").child(receiverRoom!!).child("messages").push().setValue(messageObject)

                    }.addOnFailureListener {
                        Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
                    }

                    val MessageData = HashMap<String,Any>()
                    MessageData.put("useruid" ,useruid)
                    MessageData.put("text" ,chatText)
                    MessageData.put("user",user!!.toString())
                    MessageData.put("date",date)
                    MessageData.put("mesajzamani",mesajzamani)
                    MessageData.put("senderuid",senderuid)

                    binding.AdminMesajKutusuEdtText.setText("")
                }
            }





        }



/*
        firestore.collection("Chats").orderBy("date",Query.Direction.ASCENDING).addSnapshotListener { value, error ->

            if (error!=null){
                Toast.makeText(requireContext(), error.localizedMessage, Toast.LENGTH_SHORT).show()
            }else{
                if (value!=null){

                    if (value.isEmpty){
                        Toast.makeText(requireContext(), "Mesaj bulunamadi", Toast.LENGTH_SHORT).show()
                    }else{


                        val documents = value.documents
                        messageList.clear()
                        for (document in documents){
                            val useruid = document.get("useruid") as String
                            val user = document.get("user") as String
                            val text = document.get("text") as String
                            val mesajzamani = document.get("mesajzamani") as String

                            val message = com.example.bt_support.model.Message(user,text,mesajzamani,senderuid)
                            messageList.add(message)
                            adapter.messages = messageList
                        }
                        adapter.notifyDataSetChanged()
                    }
                }


            }
        }*/
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}