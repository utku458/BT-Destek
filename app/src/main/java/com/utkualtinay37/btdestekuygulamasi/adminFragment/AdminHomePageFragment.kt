package com.utkualtinay37.btdestekuygulamasi.adminFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utkualtinay37.btdestekuygulamasi.R
import com.utkualtinay37.btdestekuygulamasi.databinding.FragmentAdminHomePageBinding




class AdminHomePageFragment : Fragment() {
    private var _binding: FragmentAdminHomePageBinding? = null
    private val binding get() = _binding!!
    lateinit var recyclerView : RecyclerView
    // lateinit var adapter:CustomAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
/*
        //var manager = RequestManager
        var listener = object:OnFetchDataListener {
            override fun onFetchData(list: List<Article>, message: String) {
               // showNews(list)
            }

            override fun onError(message: String) {
                TODO("Not yet implemented")
            }
        }
       // manager.getNewsHeadlines(listener,"technology",null)
*/
    }
/*
   private fun showNews(list: List<Article>) {
        recyclerView = binding.HomePageRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        adapter = CustomAdapter(list as ArrayList<Article>)
        recyclerView.adapter = adapter

    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminHomePageBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}