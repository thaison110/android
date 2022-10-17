package com.sonnv.kidsonline.model

import java.io.Serializable

abstract class BaseFeed: Serializable {
    abstract fun getFeedType(): Int
}