package com.blockchain.app.data.model

import com.blockchain.app.data.model.SingleEntityData
import com.google.gson.annotations.SerializedName


data class TransactionInfo (@SerializedName("status") val status : String,
                            @SerializedName("name") val name:String,
                            @SerializedName("unit") val unit: String,
                            @SerializedName("period") val period: String,
                            @SerializedName("description") val description: String,
                            @SerializedName("values") val values : List<SingleEntityData>){

    override fun toString(): String {
        return "TransactionInfo status=$status, name=$name, values=${values}"
    }
}