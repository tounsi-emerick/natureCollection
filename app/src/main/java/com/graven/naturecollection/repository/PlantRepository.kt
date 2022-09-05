package com.graven.naturecollection.repository

import android.app.appsearch.BatchResultCallback
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.graven.naturecollection.model.PlantModel
import com.graven.naturecollection.repository.PlantRepository.Singleton.databaseRef
import com.graven.naturecollection.repository.PlantRepository.Singleton.downloadUri
import com.graven.naturecollection.repository.PlantRepository.Singleton.plantList
import com.graven.naturecollection.repository.PlantRepository.Singleton.storageReference
import java.util.*

class PlantRepository {

    object Singleton{

        private val BUCKET_URL : String = "gs://naturecollection-d95f1.appspot.com"


        //se connecter à ref

        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)


        //se connecter à la ref plants sur firebase
        val database = Firebase.database
        val databaseRef = database.getReference("plants")

        //mettre à jour la liste de données
        val plantList  = arrayListOf<PlantModel>()

        //contenir l'image courate
        var downloadUri : Uri? = null




    }

    fun updateData(callback: () -> Unit){
        //absorder les données depuis la databaseRef

        databaseRef.addValueEventListener( object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                //retirer les anciennes valeurs
                plantList.clear()

                for(ds in snapshot.children){
                    //construire un objet plante


                    val plant = ds.getValue(PlantModel::class.java)

                    //verifie si la plante n'est pas nul
                    if(plant !=null){
                        plantList.add(plant)

                    }
                }


                //actionner le callBack
                callback()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


    }


    //créer une fonction pour envoyer une fonction sur le storage


    fun uploadImage(file : Uri, callback : () -> Unit){

        //verifier si le fichier n'est pas nul

        if(file !=null){
            val fileName = UUID.randomUUID().toString() + ".jpg"

            val ref = storageReference.child(fileName)
            val uploadTask = ref.putFile(file)


            //demarrer la tache d'envoie
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>
            {
                task ->

                //si il y'a eu un problème lors de l'envoie
                if(!task.isSuccessful){
                    task.exception?.let{throw it}
                }

                return@Continuation ref.downloadUrl

            }).addOnCompleteListener { task->

                /*vérifier si tout à bien fonctionné*/


                if(task.isSuccessful){
                    /*récuperer l'image (le lien meme)*/
                    downloadUri = task.result
                    callback()
            }

            }
        }
    }






    //mettre à jour un objet plant en bdd
    fun updatePlant(plantModel : PlantModel) = databaseRef.child(plantModel.id).setValue(plantModel)


    //inserer un objet plant en bdd
    fun insertPlant(plantModel : PlantModel) = databaseRef.child(plantModel.id).setValue(plantModel)


    //supprimer une photo de la base données

    fun deletePlant(plantModel: PlantModel) = databaseRef.child(plantModel.id).removeValue()










}