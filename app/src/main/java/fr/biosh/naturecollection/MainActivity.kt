package fr.biosh.naturecollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.biosh.naturecollection.fragments.AddPlantFragment
import fr.biosh.naturecollection.fragments.CollectionFragment
import fr.biosh.naturecollection.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment(this),R.string.home_page_title)

        //importer nav view

        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home_page -> {
                    loadFragment(HomeFragment(this),R.string.home_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.collection_page -> {
                    loadFragment(CollectionFragment(this),R.string.collection_page_tittle)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.add_plant_page -> {
                    loadFragment(AddPlantFragment(this),R.string.add_plant_page_tittle)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

        loadFragment(HomeFragment(this),R.string.home_page_title)
    }

    private fun loadFragment(fragment: Fragment, string: Int) {
        // charger plantRepo
        val repo = PlantRepository()

        //actualiser titre

        findViewById<TextView>(R.id.page_title).text = resources.getString(string)


        // mettre à jour la liste de plant

        repo.updateData {
            // injecter fragment dans boite ( fragment_container)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}
