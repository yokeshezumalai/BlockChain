package com.blockchain.app.data.model

import android.os.Parcel
import android.os.Parcelable
import com.blockchain.app.data.model.SingleEntityData
import com.google.gson.annotations.SerializedName

class TransactionInfo() : Parcelable {

    var status : String? = null
    var name:String? = null
    var unit: String? = null
    var period: String? = null
    var description: String? = null
    var values : List<SingleEntityData>? = null

    constructor(parcel: Parcel) : this() {
        status = parcel.readString()
        name = parcel.readString()
        unit = parcel.readString()
        period = parcel.readString()
        description = parcel.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(status)
        dest.writeString(name)
        dest.writeString(unit)
        dest.writeString(period)
        dest.writeString(description)
    }

    companion object {

        @JvmField
        val CREATOR = object : Parcelable.Creator<TransactionInfo> {
            override fun createFromParcel(parcel: Parcel) = TransactionInfo(parcel)

            override fun newArray(size: Int) = arrayOfNulls<TransactionInfo>(size)
        }

        fun prepare(period: String, name: String, description: String, status: String, unit: String) : TransactionInfo{

            val transactionInfo = TransactionInfo()
            transactionInfo.period = period
            transactionInfo.name = name
            transactionInfo.description = description
            transactionInfo.status = status
            transactionInfo.unit = unit

            return transactionInfo
        }
    }

    override fun toString(): String {
        return "TransactionInfo status=$status, name=$name, values=${values}"
    }
}