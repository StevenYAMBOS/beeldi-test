package com.beeldi.beelding.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.beeldi.beelding.R
import com.beeldi.beelding.model.EquipmentModels
import com.beeldi.beelding.view.EquipmentDetails

class EquipmentsAdapter (private var equipmentList : ArrayList<EquipmentModels>) :
    RecyclerView.Adapter<EquipmentsAdapter.ViewHolder>() {

    // We'll use 'onItemClick' in Main Activity and give it an intent to open a new activity
    var onItemClick : ((EquipmentModels) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image : ImageView = itemView.findViewById(R.id.imageEquipmentModel) // Equipment image (from equipment_models.xml)
        val title : TextView = itemView.findViewById(R.id.nameEquipmentModel) // Equipment name (from equipment_models.xml)
        val domain : TextView = itemView.findViewById(R.id.domainEquipmentModel) // Equipment domain (from equipment_models.xml)
        val nbIssues : TextView = itemView.findViewById(R.id.nbIssuesEquipmentModel) // Equipment number of issues (from equipment_models.xml)
    }

//    To filter equipment by names, I use the function in Main Activity inside the 'filterList' function
    fun setFilteredList(equipmentList: ArrayList<EquipmentModels>) {
        this.equipmentList = equipmentList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.equipment_models, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = equipmentList[position]
        holder.image.setImageResource(currentItem.equipmentImage)
        holder.title.text = currentItem.equipmentName
        holder.domain.text = currentItem.equipmentDomain
        holder.nbIssues.text = currentItem.numberIssues.toString()

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return equipmentList.size
    }
}