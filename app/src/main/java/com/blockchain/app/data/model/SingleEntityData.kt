package com.blockchain.app.data.model

import com.google.gson.annotations.SerializedName


data class SingleEntityData(@SerializedName("x") val x : Long,
                            @SerializedName("y") val y : Double)