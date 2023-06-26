package com.beeldi.beelding.model

import android.os.Parcel
import android.os.Parcelable

data class EquipmentModels(
    // Part 1 (of the test)
    var equipmentImage : Int, // Equipment image
    var equipmentName : String, // Equipment name
    var equipmentDomain : String, // Equipment domain
    var numberIssues : Int, // Equipment number of issues

    // Part 2 (of the test)
    var equipmentControlPoint : String, // Equipment control point
    var issueImage : Int, // Equipment image of the issue
    var recommendation : String // Equipment issue recommendation

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(equipmentImage)
        parcel.writeString(equipmentName)
        parcel.writeString(equipmentDomain)
        parcel.writeInt(numberIssues)
        parcel.writeString(equipmentControlPoint)
        parcel.writeInt(issueImage)
        parcel.writeString(recommendation)
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

