package com.boubyan.util

import com.blockchain.app.data.model.TransactionInfo
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader


object TestUtil {
    val transactionInfo: TransactionInfo? by lazy {
        val inputStream = javaClass.classLoader
                ?.getResourceAsStream("api-response/get_chart_data.json")

        val parser = JsonParser().parse(InputStreamReader(inputStream))

        val type = object : TypeToken<TransactionInfo>() {}.type
        val gson = Gson().fromJson<TransactionInfo>(parser, type)

        gson
    }


}