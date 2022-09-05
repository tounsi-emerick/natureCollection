package com.graven.naturecollection.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graven.naturecollection.R
import com.graven.naturecollection.fragments.PlantPopup
import com.graven.naturecollection.model.PlantModel
import com.graven.naturecollection.repository.PlantRepository


class PlantAdapter ( val plantList  : List<PlantModel>, private val layoutId : Int) : RecyclerView.Adapter<PlantAdapter.ViewHolder> (){

    lateinit var context : Context


    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val plantImage = view.findViewById<ImageView>(R.id.image_item)
        val plantName : TextView?  = view.findViewById(R.id.name_item)
        val plantDescription : TextView? = view.findViewById(R.id.description_item)
        val startIcon : ImageView = view.findViewById(R.id.star_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(layoutId,parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPlant  = plantList[position]

        //recupérer le repository
        val repo  = PlantRepository()

        //utiliser pour glide pour récupérer l'image à partir de son lien
        Glide.with(context).load(Uri.parse(currentPlant.imageUrl)).into(holder.plantImage)

        //mettre à jour le nom de la plante
        holder.plantName?.text = currentPlant.name
        //mettre à jour la description de la plante
        holder.plantDescription?.text = currentPlant.description


        //mettre à jour la valeur de l'étoile ...
        if(currentPlant.liked){
            holder.startIcon.setImageResource(R.drawable.ic_star)
        }else {
            holder.startIcon.setImageResource(R.drawable.ic_unstar)
        }

        //ajouter une interaction sur l'étoile
        holder.startIcon.setOnClickListener{

         //inverse la valeur du bouton like ou non
                currentPlant.liked = !currentPlant.liked
                //mettre à jour l'objet plante
                repo.updatePlant(currentPlant)
        }


        holder.itemView.setOnClickListener{
            PlantPopup(currentPlant,this).show()
        }


    }

    override fun getItemCount(): Int = plantList.size


}

