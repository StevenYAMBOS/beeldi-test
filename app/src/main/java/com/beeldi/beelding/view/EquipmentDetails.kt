package com.beeldi.beelding.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.beeldi.beelding.R
import com.beeldi.beelding.model.EquipmentModels

class EquipmentDetails : AppCompatActivity() {

    /* controlPoint', 'issueImage' and 'issueRecommendation' are in 'activity_equipment_details.xml'.
    They are not referenced in the adapter like the others variables because they are not present (IDs are not present)
    in the 'activity_main.xml'. */
    private lateinit var controlPoint: TextView // Equipment checkpoint (from activity_equipment_details.xml)
    private lateinit var issueImage: ImageView // Equipment issue image (from activity_equipment_details.xml)
    private lateinit var issueRecommendation: TextView // Equipment recommendation (from activity_equipment_details.xml)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipment_details)

        controlPoint  = findViewById((R.id.controlPointDetailedScreen)) // 'controlPoint' ID from activity_equipment_details
        issueImage  = findViewById((R.id.issueImageEquipmentDetailedScreen)) // 'issueImage' ID  from activity_equipment_details
        issueRecommendation  = findViewById((R.id.recommendationEquipmentDetailedScreen)) // 'issueRecommendation' ID from activity_equipment_details

//        I give the same name for the 'currentItem' from the adapter,  it's simplier to understand for me
        val currentItem = intent.getParcelableExtra<EquipmentModels>("beeldi")
        if (currentItem != null) {
            // Creation of variables that take IDs of elements in 'activity_equipment_details.xml'
            val name : TextView = findViewById((R.id.nameEquipmentDetailedScreen)) // 'name' from activity_equipment_details
            val image : ImageView = findViewById((R.id.imageEquipmentDetailedScreen)) // 'image' from activity_equipment_details
            val domain : TextView = findViewById((R.id.domainEquipmentDetailedScreen)) // 'domain' from activity_equipment_details

            // Associate newly created variables with model references
            name.text = currentItem.equipmentName
            image.setImageResource(currentItem.equipmentImage)
            domain.text = currentItem.equipmentDomain
            controlPoint.text = currentItem.equipmentControlPoint
            issueImage.setImageResource(currentItem.issueImage)
            issueRecommendation.text = currentItem.recommendation
        }
    }
}