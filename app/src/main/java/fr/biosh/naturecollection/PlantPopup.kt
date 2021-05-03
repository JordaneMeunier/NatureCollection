package fr.biosh.naturecollection

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import fr.biosh.naturecollection.adapter.PlantAdapter

class PlantPopup(private val adapter: PlantAdapter,  private val currentPlant:PlantModel) : Dialog(adapter.context){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_plants_details)
        setupComponents()
        setupCloseButton()
        setupDeleteButton()
        setupStarButton()
    }


    private fun updateStar(button : ImageView){
        if (currentPlant.liked){
            button.setImageResource(R.drawable.ic_star)
        }else{
            button.setImageResource(R.drawable.ic_unstar)
        }

    }


    private fun setupStarButton() {
        val starButton = findViewById<ImageView>(R.id.star_button)
        updateStar(starButton)
        //interaction
        starButton.setOnClickListener {
            currentPlant.liked = !currentPlant.liked
            val repo = PlantRepository()
            repo.updatePlant(currentPlant)
            updateStar(starButton)
        }
    }

    private fun setupDeleteButton() {
        findViewById<ImageView>(R.id.delete_button).setOnClickListener {
            //supr la plante de la bdd
            val repo = PlantRepository()
            repo.deletePlant(currentPlant)
            dismiss()
        }
    }

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.close_button).setOnClickListener {
            //close popup
            dismiss()
        }
    }

    private fun setupComponents() {
        //actualiser l'image de la plante
        val plantImage = findViewById<ImageView>(R.id.image_item)
        Glide.with(context).load(Uri.parse(currentPlant.imageUrl)).into(plantImage)
        //nom plante
        findViewById<TextView>(R.id.pop_up_plant_name).text = currentPlant.name
        //descripT plante
        findViewById<TextView>(R.id.pop_up_plant_description_subtitle).text = currentPlant.description
        //actualiser la croissance plante
        findViewById<TextView>(R.id.pop_up_plant_grow_subtittle).text = currentPlant.grow
        //actualiser la conso d'eau
        findViewById<TextView>(R.id.pop_up_plant_water_subtitle).text = currentPlant.water
    }

}