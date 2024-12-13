package com.utkualtinay37.btdestekuygulamasi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.utkualtinay37.btdestekuygulamasi.R
import com.utkualtinay37.btdestekuygulamasi.model.Message

class ChatRecyclerAdapter : RecyclerView.Adapter<ChatRecyclerAdapter.ChatHolder>() {

    private val VIEW_TYPE_MESSAGE_SENT = 1
    private val VIEW_TYPE_MESSAGE_RECEIVE = 2
    class ChatHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    private val diffUtil = object : DiffUtil.ItemCallback<Message>(){
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return  oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.equals(newItem)
        }

    }
    private val recylerListDiffer = AsyncListDiffer(this,diffUtil)

    var messages:List<Message>
        get() = recylerListDiffer.currentList
        set(value) = recylerListDiffer.submitList(value)


    override fun getItemViewType(position: Int): Int {


        val message = messages[position]

        if (message.username.equals(FirebaseAuth.getInstance().currentUser?.email.toString())){
            return VIEW_TYPE_MESSAGE_SENT
        }

        else{
            return VIEW_TYPE_MESSAGE_RECEIVE

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {

        if (viewType == VIEW_TYPE_MESSAGE_SENT){

            val view = LayoutInflater.from(parent.context).inflate(R.layout.sender_message_layout,parent,false)
            return ChatHolder(view)

        }else{

            val view = LayoutInflater.from(parent.context).inflate(R.layout.reciever_message_layout,parent,false)
            return ChatHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {



        val yazi = holder.itemView.findViewById<TextView>(R.id.senderMessage)
        val zaman = holder.itemView.findViewById<TextView>(R.id.timemsg)
        val ad = holder.itemView.findViewById<TextView>(R.id.username)
        yazi.setText(messages.get(position).message.toString())
        zaman.setText(messages.get(position).mesajzamani).toString()
        ad.setText(messages.get(position).username!!.removeSuffix("@gmail.com").toString())
    }

}