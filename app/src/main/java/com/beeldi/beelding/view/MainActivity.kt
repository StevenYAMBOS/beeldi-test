package com.beeldi.beelding.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beeldi.beelding.R
import com.beeldi.beelding.adapter.EquipmentsAdapter
import com.beeldi.beelding.model.EquipmentModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerview: RecyclerView
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private lateinit var adapter: EquipmentsAdapter
    private var equipmentList = ArrayList<EquipmentModels>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retrieveData()

        // Variable qui contient la liste des équipements (seront ajoutés dans le RecyclerView)
        listOfEquipments()

        recyclerview = findViewById(R.id.recyclerView_EquipmentListScreen)
        searchView = findViewById(R.id.searchView)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(this)
        adapter = EquipmentsAdapter(equipmentList)
        recyclerview.adapter = adapter

        // Va ouvrir l'objet au clique
        adapter.onItemClick = {
            val intent = Intent(this, EquipmentDetails::class.java)
            /*  Le nom de l'ID est libre,
             on viendra le récupérer dans l'activity EquipmentDetails.
             'it' = Déclaré automatiquement en fonction du type attendu */
            intent.putExtra("beeldi", it)
            startActivity(intent)
        }

        searchView.setOnQueryTextListener(object :  SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
    } // FIN onCreate

    private fun filterList(query: String?) {
        if (query != null){
            val filteredList = ArrayList<EquipmentModels>()
            val lowerCaseQuery = query.lowercase(Locale.ROOT)
            val upperCaseQuery = query.uppercase(Locale.ROOT)

            for (equipments in equipmentList) {
                val equipmentName = equipments.equipmentName

                if (equipmentName.contains(lowerCaseQuery) || equipmentName.contains(upperCaseQuery)) {
                    filteredList.add(equipments)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "Aucun équipement correspondant", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setFilteresList(filteredList)
            }
        }
    }


    private fun listOfEquipments() {
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.chaudiere_gaz),
                getString(R.string.cvc),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.armoire_electrique_chaufferie),
                getString(R.string.electricite_courants_forts),
                14
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pot_a_boue_filtre_desemboueur),
                getString(R.string.plomberie_et_assainissement),
                0
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.groupe_de_maintien_de_pression_chauffage),
                getString(R.string.plomberie_et_assainissement),
                10
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.vanne_et_robinetterie_gaz_electrovanne_coupure),
                getString(R.string.plomberie_et_assainissement),
                0
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_charge_chaudiere_1),
                getString(R.string.plomberie_et_assainissement),
                4
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.vanne_et_robinetterie_gaz_chaudiere),
                getString(R.string.plomberie_et_assainissement),
                0
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.compteur_gaz_chaudiere_1),
                getString(R.string.comptage),
                0
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.compteur_gaz_chaudiere_2),
                getString(R.string.comptage),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_charge_chaudiere_2),
                getString(R.string.plomberie_et_assainissement),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.autre_doe),
                getString(R.string.batiment),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.echangeur_a_plaques),
                getString(R.string.cvc),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_doseuse_reseau_ecs),
                getString(R.string.plomberie_et_assainissement),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_secondaire_ecs),
                getString(R.string.plomberie_et_assainissement),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.compteur_eau_de_ville_general),
                getString(R.string.comptage),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_primaire_echangeur_ecs),
                getString(R.string.plomberie_et_assainissement),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_bouclage_ecs_bat_c_d_e_f),
                getString(R.string.plomberie_et_assainissement),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_bouclage_ecs_bat_a_b),
                getString(R.string.cvc),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_de_relevage),
                getString(R.string.plomberie_et_assainissement),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_distribution_ec),
                getString(R.string.plomberie_et_assainissement),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.regulateur_chaudiere),
                getString(R.string.plomberie_et_assainissement),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.ballon_de_stockage_ecs),
                getString(R.string.plomberie_et_assainissement),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.detecteur_gaz),
                getString(R.string.cvc),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.local_chaufferie),
                getString(R.string.batiment),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.compensateur_de_dilatation),
                getString(R.string.plomberie_et_assainissement),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.servomoteur_reseau_chaud),
                getString(R.string.electricite_courants_faibles),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.distribution_hydraulique_chaufferie),
                getString(R.string.plomberie_et_assainissement),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.automate_reseau_chaud),
                getString(R.string.electricite_courants_faibles),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.armoire_automate),
                getString(R.string.electricite_courants_faibles),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.clapet_reseau_ef),
                getString(R.string.plomberie_et_assainissement),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.detendeur),
                getString(R.string.plomberie_et_assainissement),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.centrale_detection_incendie),
                getString(R.string.incendie_et_securite),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.armoire_electrique),
                getString(R.string.electricite_courants_forts),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.baes),
                getString(R.string.electricite_courants_forts),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.extincteur_a_poudre),
                getString(R.string.incendie_et_securite),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_distribution),
                getString(R.string.plomberie_et_assainissement),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.armoire_electrique_s_station),
                getString(R.string.cvc),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.automate_reseau_chaud_batiment_g),
                getString(R.string.electricite_courants_forts),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.local_technique_s_station),
                getString(R.string.batiment),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.echangeur_a_plaques_s_station),
                getString(R.string.cvc),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_primaire_echangeur_s_station),
                getString(R.string.plomberie_et_assainissement),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_doseuse_s_station),
                getString(R.string.plomberie_et_assainissement),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.compteur_eau_de_ville_s_station),
                getString(R.string.comptage),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_distribution_ec_batiment_g),
                getString(R.string.plomberie_et_assainissement),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.servomoteur_reseau_chaud_batiment_g),
                getString(R.string.electricite_courants_faibles),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pot_a_boue_s_station),
                getString(R.string.plomberie_et_assainissement),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_recyclage),
                getString(R.string.plomberie_et_assainissement),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_recyclage_n2),
                getString(R.string.plomberie_et_assainissement),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.extincteur_a_poudre_s_station),
                getString(R.string.incendie_et_securite),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.extincteur_au_co2_s_station),
                getString(R.string.incendie_et_securite),
                9
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.distribution_hydraulique_s_station),
                getString(R.string.plomberie_et_assainissement),
                9
            )
        )
    }






    // FIREBASE
    private fun retrieveData() {
        val database = Firebase.database
        val equipmentsDatabaseReference = database.getReference("Equipments")
        equipmentsDatabaseReference.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
               Log.d(TAG, error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d(TAG, snapshot.toString())
            }
        })
    }

    companion object {
        const val TAG = "MainActivity"
    }
}