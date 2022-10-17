package com.sonnv.kidsonline.model

import java.io.Serializable

data class TodayActivity(
    val title: String,
    val note: String,
    val time: String
): Serializable
