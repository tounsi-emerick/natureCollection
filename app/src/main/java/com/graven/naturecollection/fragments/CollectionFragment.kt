package com.graven.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graven.naturecollection.R
import com.graven.naturecollection.adapter.PlantAdapter
import com.graven.naturecollection.repository.PlantRepository

class CollectionFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater?.inflate(R.layout.fragment_collection, container, false)

        val collectionRecyclerView = view.findViewById<RecyclerView>(R.id.collection_recycler_list)
        collectionRecyclerView.adapter = PlantAdapter(PlantRepository.Singleton.plantList.filter { it.liked },  R.layout.item_vertical_plant)
        collectionRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        return view
    }


}