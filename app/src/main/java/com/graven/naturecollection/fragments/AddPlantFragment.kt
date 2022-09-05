package com.graven.naturecollection.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graven.naturecollection.R
import com.graven.naturecollection.adapter.PlantAdapter
import com.graven.naturecollection.model.PlantModel
import com.graven.naturecollection.repository.PlantRepository
import com.graven.naturecollection.repository.PlantRepository.Singleton.downloadUri
import java.util.*

class AddPlantFragment : Fragment(){


    private var selectedImage : Uri? = null
    lateinit var  uploadedImage : ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater?.inflate(R.layout.fragment_add_plant, container, false)


        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)

        //recuperer l'image

        uploadedImage = view.findViewById(R.id.preview_image)

        pickupImageButton.setOnClickListener{pickupImage()}



        //recuperer le bouton confirmer

        val confirm_button = view.findViewById<Button>(R.id.confirm_button)

        confirm_button.setOnClickListener{sendForm(view)}


        return view

    }

    private fun pickupImage(){
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),47)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    if(requestCode == 47 && resultCode == Activity.RESULT_OK){

        //vérifies si les données sont nulles

        if(data == null || data.data ==null){
            return
        }

        //recupérer l'image
        selectedImage = data.data

        //mettre à jour l'appercu de l'image
        uploadedImage?.setImageURI(selectedImage)


        //heberger sur le bucket

        /*
        val repo = PlantRepository()
        repo.uploadImage(selectedImage!!)*/


    }
    }


    private fun sendForm(view: View){


        val repo = PlantRepository()
        repo.uploadImage(selectedImage!!){
            val plantName = view.findViewById<TextView>(R.id.name_input).text.toString()
            val plantDescription = view.findViewById<TextView>(R.id.description_input).text.toString()
            val grow = view.findViewById<Spinner>(R.id.grow_spinner).selectedItem.toString()
            val water = view.findViewById<Spinner>(R.id.water_spinner).selectedItem.toString()


            val downloadImageUrl = downloadUri


            //créer un nouvel objet plantModel à envoyer en BD

            var plantModel  = PlantModel(

                UUID.randomUUID().toString(),
                plantName,
                plantDescription,
                downloadImageUrl.toString(),
                grow,
                water
            )

            //inserer l'objet en base de données

            repo.insertPlant(plantModel)


        }


    }









}


