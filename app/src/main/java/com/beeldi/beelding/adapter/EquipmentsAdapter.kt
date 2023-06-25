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

    var onItemClick : ((EquipmentModels) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image : ImageView = itemView.findViewById(R.id.imageEquipmentModel)
        val title : TextView = itemView.findViewById(R.id.nameEquipmentModel)
        val domain : TextView = itemView.findViewById(R.id.domainEquipmentModel)
        val defaults : TextView = itemView.findViewById(R.id.nbDefaultsEquipmentModel)
    }

//    VA filtrer les équipements par nom
    fun setFilteresList(equipmentList: ArrayList<EquipmentModels>) {
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
        holder.defaults.text = currentItem.numberDefaults.toString()

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return equipmentList.size
    }
}