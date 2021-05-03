package fr.biosh.naturecollection.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import fr.biosh.naturecollection.MainActivity
import fr.biosh.naturecollection.PlantModel
import fr.biosh.naturecollection.PlantRepository
import fr.biosh.naturecollection.R
import java.util.*

class AddPlantFragment(private val context:MainActivity):Fragment() {

    private var uplodedImage:ImageView? = null
    private var file:Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_add_plant,container,false)

        //récup uploadedImage
        uplodedImage = view.findViewById(R.id.preview_image)


        //récup bouton image
        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)
        //click dessus ça ouvre image tel
        pickupImageButton.setOnClickListener {
            pickupImage()
        }

        //récup bouton confirmer
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)
        //click pour confirmer
        confirmButton.setOnClickListener {
            sendForm(view)
        }


        return view
    }

    private fun sendForm(view: View) {

        val repo = PlantRepository()
        repo.uploadImage(file!!){
            val plantName = view.findViewById<EditText>(R.id.name_input).text.toString()
            val plantDescription = view.findViewById<EditText>(R.id.description_input).text.toString()
            val grow = view.findViewById<Spinner>(R.id.grow_spinner).selectedItem.toString()
            val water = view.findViewById<Spinner>(R.id.conso_spinner).selectedItem.toString()
            val downloadImageUrl = PlantRepository.Singleton.downlaodUri

            //créer objet PlantModel

            val plant = PlantModel(
                    UUID.randomUUID().toString(),
                    plantName,
                    plantDescription,
                    downloadImageUrl.toString(),
                    grow,
                    water


            )

            //Envoyer BDD
            repo.insertPlant(plant)



        }
    }

    private fun pickupImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select picture"),47)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 47 && resultCode  == Activity.RESULT_OK){
            //Vérifier si données sont null
            if (data == null  || data.data == null)return
            //récupérer imagine
             file = data.data
            //mettre à jour imagine
            uplodedImage?.setImageURI(file)

            /*
            // heberger Bucket
            val repo = PlantRepository()
            repo.uploadImage(selectedImagine!!)

             */

        }
    }


}