package com.loyaltyglobal.data.model.response.transactionData

data class TransactionData(
    val data: List<Data>,
    val error: String? =null,
    val message: String? =null,
    val success: Boolean? =null
)
