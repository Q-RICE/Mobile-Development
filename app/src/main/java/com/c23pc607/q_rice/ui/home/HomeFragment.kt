package com.c23pc607.q_rice.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.c23pc607.q_rice.R
import com.c23pc607.q_rice.Service
import com.c23pc607.q_rice.ServiceAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var rvServices: RecyclerView
    private val list = ArrayList<Service>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })

        val toolbar: Toolbar = root.findViewById(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        rvServices = root.findViewById(R.id.rv_services)
        rvServices.setHasFixedSize(true)

        list.addAll(getListServices())
        showRecyclerList()

        return root
    }

    @SuppressLint("Recycle")
    private fun getListServices(): ArrayList<Service> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataIcon = resources.obtainTypedArray(R.array.data_icon)
        val listService = ArrayList<Service>()
        for (i in dataName.indices) {
            val service = Service(dataName[i], dataIcon.getResourceId(i, -1))
            listService.add(service)
        }
        return listService
    }

    private fun showRecyclerList() {
        val serviceAdapter = ServiceAdapter(list)
        rvServices.adapter = serviceAdapter
        serviceAdapter.setOnItemClickCallback(object : ServiceAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Service) {
                showSelectedService(data)
            }
        })
    }

    private fun showSelectedService(service: Service) {
        Toast.makeText(requireContext(), "You chose " + service.name, Toast.LENGTH_SHORT).show()
    }
}