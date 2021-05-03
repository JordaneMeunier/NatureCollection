package fr.biosh.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.biosh.naturecollection.MainActivity
import fr.biosh.naturecollection.PlantRepository.Singleton.plantList
import fr.biosh.naturecollection.R
import fr.biosh.naturecollection.adapter.PlantAdapter
import fr.biosh.naturecollection.adapter.PlantItemDecoration

class CollectionFragment(private val context: MainActivity) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_collection,container,false)

        //RecyclerView récup
        val collectionRecyclerview = view.findViewById<RecyclerView>(R.id.collection_recycler_list)
        collectionRecyclerview.adapter = PlantAdapter(R.layout.item_vertical_plant,plantList.filter { it.liked },context)
        collectionRecyclerview.layoutManager = LinearLayoutManager(context)
        collectionRecyclerview.addItemDecoration(PlantItemDecoration())
        return view
    }
}