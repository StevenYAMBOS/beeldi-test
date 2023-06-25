package com.beeldi.beelding.model

import android.os.Parcel
import android.os.Parcelable

data class EquipmentModels(
    var equipmentImage : Int,
    var equipmentName : String,
    var equipmentDomain : String,
    var numberDefaults : Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(equipmentImage)
        parcel.writeString(equipmentName)
        parcel.writeString(equipmentDomain)
        parcel.writeInt(numberDefaults)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EquipmentModels> {
        override fun createFromParcel(parcel: Parcel): EquipmentModels {
            return EquipmentModels(parcel)
        }

        override fun newArray(size: Int): Array<EquipmentModels?> {
            return arrayOfNulls(size)
        }
    }
}
