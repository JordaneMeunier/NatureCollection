package fr.biosh.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.biosh.naturecollection.MainActivity
import fr.biosh.naturecollection.PlantModel
import fr.biosh.naturecollection.PlantRepository.Singleton.plantList
import fr.biosh.naturecollection.R
import fr.biosh.naturecollection.adapter.PlantAdapter
import fr.biosh.naturecollection.adapter.PlantItemDecoration

class HomeFragment(private val context : MainActivity) : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)


        //créer une liste qui va stocker les plantes

        //val plantList = arrayListOf<PlantModel>()

        /*
        // enregistrer une première plante dans notre liste (pissenlit)

        plantList.add(PlantModel("Pissenlit",
                "Jaune Soleil",
                "https://cdn.pixabay.com/photo/2014/09/27/17/35/dandelion-463928_1280.jpg",
                false))

        // enregistrer seconde plante dans notre liste (rose)

        plantList.add(PlantModel("Rose",
                "Rouge Sang",
                "https://cdn.pixabay.com/photo/2018/11/08/12/02/rose-3802424_1280.jpg",
                false))

        // 3ème

        plantList.add(PlantModel("Cactus",
                "Aie",
                "https://cdn.pixabay.com/photo/2014/07/29/08/55/cactus-404362_960_720.jpg",
                false))

        // 4ème

        plantList.add(PlantModel("Chardon",
                "Beau violet",
                "https://cdn.pixabay.com/photo/2017/06/18/17/48/thistle-2416587_1280.jpg",
                false))

         */

        //récupérer la recyclerview

        val horizontalRecyclerView =  view.findViewById<RecyclerView>(R.id.horizontal_recyclerView)
        horizontalRecyclerView.adapter = PlantAdapter(R.layout.item_horizontal_plant,plantList.filter { !it.liked },context)

        // récupérer 2nd recyclerview

        val verticalRecyclerView =  view.findViewById<RecyclerView>(R.id.vertical_recyclerView)
        verticalRecyclerView.adapter = PlantAdapter(R.layout.item_vertical_plant,plantList,context)
        verticalRecyclerView.addItemDecoration(PlantItemDecoration())
        return view
    }
}