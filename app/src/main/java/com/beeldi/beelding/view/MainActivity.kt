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

        // Variable containing the list of equipment (added to the RecyclerView)
        listOfEquipments()

        recyclerview = findViewById(R.id.recyclerView_EquipmentListScreen)
        searchView = findViewById(R.id.searchView)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(this)
        adapter = EquipmentsAdapter(equipmentList)
        recyclerview.adapter = adapter

        // Variable Click to open the object
        adapter.onItemClick = {
            val intent = Intent(this, EquipmentDetails::class.java)
            /*
            The ID name is free, I've put 'Beeldi'.
            It will be retrieved from the EquipmentDetails activity.
            'it' = Automatically declared according to expected type.
            */
            intent.putExtra("beeldi", it)
            startActivity(intent)
        }

        // Search bar
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
    } // END onCreate

    // String filter for search bar
    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<EquipmentModels>()
            val searchQuery = query.trim().lowercase(Locale.ROOT)

            for (equipment in equipmentList) {
                val equipmentName = equipment.equipmentName.lowercase(Locale.ROOT)

                if (equipmentName.contains(searchQuery)) {
                    filteredList.add(equipment)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "Aucun équipement correspondant", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        } else {
            adapter.setFilteredList(equipmentList)
        }
    }

    /*
    Variable that contains the list Of Equipments.
    Elements are oredered in the same from the model (EquipmentModels.kt)
    */
    private fun listOfEquipments() {
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.chaudiere_gaz),
                getString(R.string.cvc),
                9,
                getString(R.string.checkpoint1),
                R.drawable.logo1,
                getString(R.string.chaudiere_gaz_recommendation)

            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.armoire_electrique_chaufferie),
                getString(R.string.electricite_courants_forts),
                14,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                "L'équipement présente peu de risque. Il manque des documentations et informations liées à la prévention pour assurer une bonne maintenance de l'équipement (pictogramme, schéma électrique, etc…). Nous conseillons la mise en place d'un compteur électrique"
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pot_a_boue_filtre_desemboueur),
                getString(R.string.plomberie_et_assainissement),
                0,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                "Solution de clarification. S'assurer de la bonne réalisation de la maintenance et du nettoyage du filtre. Il est possible de mettre en place en complément : - un coffret de détection d'encrassement avec pressostat - Une régulation automatique du pH"
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.groupe_de_maintien_de_pression_chauffage),
                getString(R.string.plomberie_et_assainissement),
                10,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.groupe_de_maintien_de_pression_chauffage_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.vanne_et_robinetterie_gaz_electrovanne_coupure),
                getString(R.string.plomberie_et_assainissement),
                0,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.vanne_et_robinetterie_gaz_electrovanne_coupure_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_charge_chaudiere_1),
                getString(R.string.plomberie_et_assainissement),
                4,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                "Nous conseillons d'analyser et comparer le débit de la pompe recyclage à la somme des débits des réseaux secondaires pour assurer un retour de température minimale aux chaudières (amélioration de la condensation des chaudières)"
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.vanne_et_robinetterie_gaz_chaudiere),
                getString(R.string.plomberie_et_assainissement),
                4,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.vanne_et_robinetterie_gaz_chaudiere_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.compteur_gaz_chaudiere_1),
                getString(R.string.comptage),
                0,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                "Il est conseillé d'étalonner régulièrement les compteurs gaz. Nous préconisons de réaliser un relevé et suivi hebdomadaire du compteur gaz pour améliorer la performance énergétique de l'installation"
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.compteur_gaz_chaudiere_2),
                getString(R.string.comptage),
                0,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                "Il est conseillé d'étalonner régulièrement les compteurs gaz. Nous préconisons de réaliser un relevé et suivi hebdomadaire du compteur gaz pour améliorer la performance énergétique de l'installation"
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_charge_chaudiere_2),
                getString(R.string.comptage),
                0,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                "Nous conseillons d'analyser et comparer le débit de la pompe recyclage à la somme des débits des réseaux secondaires pour assurer un retour de température minimale aux chaudières (amélioration de la condensation des chaudières)"
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.autre_doe),
                getString(R.string.batiment),
                0,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.autre_doe_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.echangeur_a_plaques),
                getString(R.string.cvc),
                2,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.echangeur_a_plaques_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_doseuse_reseau_ecs),
                getString(R.string.plomberie_et_assainissement),
                0,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.pompe_doseuse_reseau_ecs_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_secondaire_ecs),
                getString(R.string.plomberie_et_assainissement),
                3,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.pompe_secondaire_ecs_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.compteur_eau_de_ville_general),
                getString(R.string.comptage),
                4,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                "Qn 25 m3/h ; 6 bars. Il est conseillé d'étalonner tous les 15 ans les compteurs eau de ville"
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_primaire_echangeur_ecs),
                getString(R.string.plomberie_et_assainissement),
                5,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.pompe_primaire_echangeur_ecs_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_bouclage_ecs_bat_c_d_e_f),
                getString(R.string.plomberie_et_assainissement),
                5,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.pompe_bouclage_ecs_bat_c_d_e_f_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_bouclage_ecs_bat_a_b),
                getString(R.string.cvc),
                4,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.pompe_bouclage_ecs_bat_a_b_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_de_relevage),
                getString(R.string.plomberie_et_assainissement),
                4,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.pompe_de_relevage_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_distribution_ec),
                getString(R.string.plomberie_et_assainissement),
                11,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.pompe_distribution_ec_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.regulateur_chaudiere),
                getString(R.string.plomberie_et_assainissement),
                5,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.regulateur_chaudiere_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.ballon_de_stockage_ecs),
                getString(R.string.plomberie_et_assainissement),
                9,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.ballon_de_stockage_ecs_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.detecteur_gaz),
                getString(R.string.cvc),
                1,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.detecteur_gaz_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.local_chaufferie),
                getString(R.string.batiment),
                8,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.local_chaufferie_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.compensateur_de_dilatation),
                getString(R.string.plomberie_et_assainissement),
                0,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.compensateur_de_dilatation_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.servomoteur_reseau_chaud),
                getString(R.string.electricite_courants_faibles),
                0,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.servomoteur_reseau_chaud_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.distribution_hydraulique_chaufferie),
                getString(R.string.plomberie_et_assainissement),
                1,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.distribution_hydraulique_chaufferie_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.automate_reseau_chaud),
                getString(R.string.electricite_courants_faibles),
                5,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.automate_reseau_chaud_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.armoire_automate),
                getString(R.string.electricite_courants_faibles),
                6,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.armoire_automate_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.clapet_reseau_ef),
                getString(R.string.plomberie_et_assainissement),
                2,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.clapet_reseau_ef_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.detendeur),
                getString(R.string.plomberie_et_assainissement),
                2,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.detendeur_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.centrale_detection_incendie),
                getString(R.string.incendie_et_securite),
                3,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.centrale_detection_incendie_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.armoire_electrique),
                getString(R.string.electricite_courants_forts),
                14,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                "Aucun contrôle réglementaire effectué sur l'équipement, présence de multiples défauts de sécurité et conformité (absence d'un arrêt d'urgence, etc.)"
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.baes),
                getString(R.string.electricite_courants_forts),
                6,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.baes_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.extincteur_a_poudre),
                getString(R.string.incendie_et_securite),
                2,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.extincteur_a_poudre_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_distribution),
                getString(R.string.plomberie_et_assainissement),
                6,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.pompe_distribution_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.armoire_electrique_s_station),
                getString(R.string.cvc),
                19,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.armoire_electrique_s_station_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.automate_reseau_chaud_batiment_g),
                getString(R.string.electricite_courants_forts),
                8,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.automate_reseau_chaud_batiment_g_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.local_technique_s_station),
                getString(R.string.batiment),
                4,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.local_technique_s_station_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.echangeur_a_plaques_s_station),
                getString(R.string.cvc),
                11,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.echangeur_a_plaques_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_primaire_echangeur_s_station),
                getString(R.string.plomberie_et_assainissement),
                9,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                getString(R.string.pompe_primaire_echangeur_s_station_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_doseuse_s_station),
                getString(R.string.plomberie_et_assainissement),
                3,
                getString(R.string.checkpoint2),
                R.drawable.logo1,
                "Produit utilisé : PEP 04 (produit anti orrosion pour réseau d\\'eaux chaudes sanitaires). Dosage actuel : 60 / 90 ml/m3. Nous préconisons de réaliser une analyse physico chimique du réseau d'eau chaude"
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.compteur_eau_de_ville_s_station),
                getString(R.string.comptage),
                4,
                getString(R.string.checkpoint3),
                R.drawable.logo1,
                "Il est conseillé d\\'étalonner les compteurs eau (tous les 15 ans à minima). Nous préconisons de réaliser un relevé et suivi hebdommadaire du compteur d\\'eau de ville pour améliorer la performance énergétique de l'installation"
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_distribution_ec_batiment_g),
                getString(R.string.plomberie_et_assainissement),
                7,
                getString(R.string.checkpoint3),
                R.drawable.logo1,
                getString(R.string.pompe_distribution_ec_batiment_g_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.servomoteur_reseau_chaud_batiment_g),
                getString(R.string.electricite_courants_faibles),
                2,
                getString(R.string.checkpoint3),
                R.drawable.logo1,
                "Contrôler les positions \"début et fin\" de course de l\'équipement"
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pot_a_boue_s_station),
                getString(R.string.plomberie_et_assainissement),
                2,
                getString(R.string.checkpoint3),
                R.drawable.logo1,
                getString(R.string.pot_a_boue_s_station_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_recyclage),
                getString(R.string.plomberie_et_assainissement),
                9,
                getString(R.string.checkpoint3),
                R.drawable.logo1,
                getString(R.string.pompe_de_relevage_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.pompe_recyclage_n2),
                getString(R.string.plomberie_et_assainissement),
                11,
                getString(R.string.checkpoint4),
                R.drawable.logo1,
                getString(R.string.pompe_recyclage_n2_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.extincteur_a_poudre_s_station),
                getString(R.string.incendie_et_securite),
                4,
                getString(R.string.checkpoint4),
                R.drawable.logo1,
                getString(R.string.extincteur_a_poudre_s_station_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.extincteur_au_co2_s_station),
                getString(R.string.incendie_et_securite),
                4,
                getString(R.string.checkpoint4),
                R.drawable.logo1,
                getString(R.string.extincteur_au_co2_s_station_recommendation)
            )
        )
        equipmentList.add(
            EquipmentModels(
                R.drawable.logo2,
                getString(R.string.distribution_hydraulique_s_station),
                getString(R.string.plomberie_et_assainissement),
                4,
                getString(R.string.checkpoint4),
                R.drawable.logo1,
                getString(R.string.distribution_hydraulique_s_station_recommendation)
            )
        )
    }



    // FIREBASE configuration
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