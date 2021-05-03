package fr.biosh.naturecollection.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.biosh.naturecollection.*

class PlantAdapter(private val layoutId: Int, private val plantList : List<PlantModel>, val context : MainActivity) : RecyclerView.Adapter<PlantAdapter.ViewHolder>() {

    //Boite pour ranger les composants à contrôler
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        //image de la plante
        val plantImage = view.findViewById<ImageView>(R.id.image_item)
        val plantName:TextView? = view.findViewById(R.id.item_name)
        val plantDescription:TextView? = view.findViewById(R.id.description_item)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent,false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // récup repo
        val repo = PlantRepository()
        //récupérer info plante
        val currentPlant = plantList[position]
        //glide recup image
        Glide.with(context).load(Uri.parse(currentPlant.imageUrl)).into(holder.plantImage)

        //nom plante
        holder.plantName?.text = currentPlant.name
        //description
        holder.plantDescription?.text = currentPlant.description
        //star liked or not
        if (currentPlant.liked) {
            holder.starIcon.setImageResource(R.drawable.ic_star)
        } else {
            holder.starIcon.setImageResource(R.drawable.ic_unstar)
        }

        // add interaction sur étoile
        holder.starIcon.setOnClickListener {
            //like-unlike
            currentPlant.liked = !currentPlant.liked
            //mettre à jour la plant
            repo.updatePlant(currentPlant)
        }
        //interaction onClick plant
        holder.itemView.setOnClickListener{
            //afficher popup
            PlantPopup(this,currentPlant).show()
        }
    }

    override fun getItemCount(): Int = plantList.size

}