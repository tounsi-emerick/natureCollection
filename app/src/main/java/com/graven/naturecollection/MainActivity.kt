package com.graven.naturecollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.graven.naturecollection.fragments.AddPlantFragment
import com.graven.naturecollection.fragments.CollectionFragment
import com.graven.naturecollection.fragments.HomeFragment
import com.graven.naturecollection.repository.PlantRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //accueil au chargement...
        loadFragment(HomeFragment(), R.string.home_page_title)

        //impoter la bottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation_view)


        bottomNavigationView.setOnNavigationItemSelectedListener {

            when(it.itemId){

                R.id.home_apge->{
                    loadFragment(HomeFragment(), R.string.home_page_title)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.collection_page->{
                    loadFragment(CollectionFragment(), R.string.collection_page_title)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.add_plant_page->{
                    loadFragment(AddPlantFragment(), R.string.add_plant_page_title)
                    return@setOnNavigationItemSelectedListener true
                }

                else -> false

            }

        }

    }

    private fun loadFragment(fragment : Fragment, page_title : Int){

        //charger les datas de notre repository
        val repo = PlantRepository()

        //mettre à jour le titre de la page

        findViewById<TextView>(R.id.page_title).text = resources.getString(page_title)

        //metttre à jour la liste de plante

        repo.updateData {
            //injecter le fragment dans notre boite (fragment_container)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }



}