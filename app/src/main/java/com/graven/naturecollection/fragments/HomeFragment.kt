package com.graven.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.graven.naturecollection.R
import com.graven.naturecollection.adapter.PlantAdapter
import com.graven.naturecollection.model.PlantModel
import com.graven.naturecollection.repository.PlantRepository.Singleton.plantList

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view : View = inflater?.inflate(R.layout.fragment_home, container, false)


        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter = PlantAdapter(plantList.filter{!it.liked},  R.layout.item_horizontal_plant)

        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = PlantAdapter(plantList, R.layout.item_vertical_plant)

        return view
    }




}
