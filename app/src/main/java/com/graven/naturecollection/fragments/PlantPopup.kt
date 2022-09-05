package com.graven.naturecollection.fragments

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.graven.naturecollection.R
import com.graven.naturecollection.adapter.PlantAdapter
import com.graven.naturecollection.model.PlantModel
import com.graven.naturecollection.repository.PlantRepository

class PlantPopup (private val currentPlant :PlantModel, private val adapter : PlantAdapter) : Dialog (adapter.context){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //on ne veut pas de titre
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_plants_details)

        setupComponents()
        setupDeleteButton()
        setupCloseButton()
        setupStarButton()
    }

    private fun setupDeleteButton() {
        findViewById<ImageView>(R.id.delete_button).setOnClickListener{
            //supprimer la plante de la base de données
            val repo = PlantRepository()
            repo.deletePlant(currentPlant)

            dismiss()

        }
    }

    private fun updateStar(image_button : ImageView){
        if(currentPlant.liked){
            image_button.setImageResource(R.drawable.ic_star)
        }else {
            image_button.setImageResource(R.drawable.ic_unstar)
        }

    }

    private fun setupStarButton(){
        val startIcon = findViewById<ImageView>(R.id.star_icon)
        updateStar(startIcon)

        startIcon.setOnClickListener{
            currentPlant.liked = !currentPlant.liked
            val repo  = PlantRepository()
            repo.updatePlant(currentPlant)

            updateStar(startIcon)
        }

    }



    private fun setupComponents() {
        //mettre à jour l'image de la plante
        val plantImage = findViewById<ImageView>(R.id.image_item)

        Glide.with(adapter.context).load(Uri.parse(currentPlant.imageUrl)).into(plantImage)

        findViewById<TextView>(R.id.popup_plant_name).text = currentPlant.name
        findViewById<TextView>(R.id.popup_plant_description_subtitle).text = currentPlant.description
        findViewById<TextView>(R.id.popup_plant_grow_subtitle).text = currentPlant.grow
        findViewById<TextView>(R.id.popup_plant_water_subtitle).text = currentPlant.water

    }


    private fun setupCloseButton(){
        findViewById<ImageView>(R.id.close_button).setOnClickListener{
            //fermer la fenêtre
            dismiss()
        }
    }

}

