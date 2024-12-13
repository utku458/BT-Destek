package com.utkualtinay37.btdestekuygulamasi.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.utkualtinay37.btdestekuygulamasi.R
import com.utkualtinay37.btdestekuygulamasi.adminFragment.AdminTalepDetayFragment
import com.utkualtinay37.btdestekuygulamasi.databinding.AdminTalepRecyclerRowBinding
import com.utkualtinay37.btdestekuygulamasi.model.UserTalep

class AdminTalepRecyclerAdapter(val talepList:ArrayList<UserTalep>, val parentFragment: Fragment): RecyclerView.Adapter<AdminTalepRecyclerAdapter.TalepHolder>()
{

    class TalepHolder(val binding: AdminTalepRecyclerRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalepHolder {
        val binding = AdminTalepRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TalepHolder(binding)
    }

    override fun getItemCount(): Int {
        return talepList.size
    }

    override fun onBindViewHolder(holder: TalepHolder, position: Int) {
        holder.binding.adminTalepGoruntuleKullaniciAditxt.setText(talepList.get(position).username)
        holder.binding.adminTalepGoruntuleTalepKonutxt.setText(talepList.get(position).talepKonu)
        holder.binding.adminTalepGoruntuleTalepBasliktxt.setText(talepList.get(position).talepBaslik)
        val talepid= talepList.get(position).talepid



        holder.binding.adminMenuBtn.setOnClickListener {


            val result = Bundle()
            result.putString("kullaniciadi", holder.binding.adminTalepGoruntuleKullaniciAditxt.text.toString())
            result.putString("konu",holder.binding.adminTalepGoruntuleTalepKonutxt.text.toString())
            result.putString("icerik", holder.binding.adminTalepGoruntuleTalepBasliktxt.text.toString())
            result.putString("konudetay", talepList.get(position).talepKonuYazi.toString())
            result.putString("talepid", talepid.toString())
            result.putString("receiverid", talepList.get(position).userid.toString())

            parentFragment.parentFragmentManager.setFragmentResult("datafrom1",result)




            val activity = it!!.context as AppCompatActivity
            val TalepDetayFragment = AdminTalepDetayFragment()

            activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,TalepDetayFragment).commit()


        }
    }
}