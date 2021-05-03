package fr.biosh.naturecollection

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import fr.biosh.naturecollection.PlantRepository.Singleton.databaseRef
import fr.biosh.naturecollection.PlantRepository.Singleton.downlaodUri
import fr.biosh.naturecollection.PlantRepository.Singleton.plantList
import fr.biosh.naturecollection.PlantRepository.Singleton.storageRef
import java.net.URI
import java.util.*
import kotlin.coroutines.Continuation

class PlantRepository {

    object Singleton {
        // se co à la ref " plants"
        val databaseRef = FirebaseDatabase.getInstance().getReference("plants")

        //créer liste qui va contenir nos plantes
        val plantList = arrayListOf<PlantModel>()

        //lien bucket
        private val BUCKET_URL: String = "gs://nature-collection-22f9b.appspot.com"

        //co à l'espace de stockage
        val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        //contenir lien image courante
        var downlaodUri: Uri?  = null

    }

    fun updateData(callback: () -> Unit) {
        //absorber les données depuis la database -> liste de plantes
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //retirer les anciennes
                plantList.clear()
                //récolter la liste
                for (ds in snapshot.children) {
                    //Construire un objet plante
                    val plant = ds.getValue(PlantModel::class.java)

                    //Vérifier que la plante n'est pas null
                    if (plant != null) {
                        //ajouter plante à liste
                        plantList.add(plant)
                    }

                }
                //actionner le callbac
                callback()
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }

    //mettre à jour un objet plant en BDD
    fun updatePlant(plant: PlantModel) = databaseRef.child(plant.id).setValue(plant)

    //insérer new plant en bdd
    fun insertPlant(plant: PlantModel) = databaseRef.child(plant.id).setValue(plant)

    //DeletePlant
    fun deletePlant(plant: PlantModel) = databaseRef.child(plant.id).removeValue()

    //créer une fonction pour envoyer fichiers storage
    fun uploadImage(file: Uri,callback: () -> Unit) {
        //vérifier qu'il est pas null ( fichier)
        if (file != null) {
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = storageRef.child(fileName)
            val uploadTask = ref.putFile(file)

            //démarrer tache d'envoi
            uploadTask.continueWithTask(com.google.android.gms.tasks.Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                // si y'a eu un bleme pendant l'envoi
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                return@Continuation ref.downloadUrl
            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //récup image sur bucket
                    downlaodUri = task.result
                    callback()
                }
            }

        }
    }


}