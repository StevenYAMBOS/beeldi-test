package com.beeldi.beelding.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.beeldi.beelding.R
import com.beeldi.beelding.model.EquipmentModels

class EquipmentDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipment_details)


        val currentItem = intent.getParcelableExtra<EquipmentModels>("beeldi")
        if (currentItem != null) {
            // Variables des ID pour activity_equipment_details
            val name : TextView = findViewById((R.id.nameEquipmentDetailedScreen)) // 'name' dans activity_equipment_details
            val image : ImageView = findViewById((R.id.imageEquipmentDetailedScreen)) // 'image' dans activity_equipment_details
            val domain : TextView = findViewById((R.id.domainEquipmentDetailedScreen)) // 'domain' dans activity_equipment_details

            // associer les variables nouvellements créées aux id XML de l'activity du model
            name.text = currentItem.equipmentName
            image.setImageResource(currentItem.equipmentImage)
            domain.text = currentItem.equipmentDomain
        }
    }
}