package com.sonnv.kidsonline.model

import java.io.Serializable

data class Fee(
    val feeName: String,
    val feeStatus: String,
    val total: Int,
    val paid: Int,
    val debt: Int,
    val deadline: String,
    val tuitionFee: Int,
    val eatFee: Int,
    val month: Int,
    val learnToolsFee: Int,
    val picnicFee: Int
) : Serializable
