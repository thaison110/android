package com.sonnv.kidsonline.model

class FullUtilityModel: BaseFeed() {

    override fun getFeedType(): Int {
        return FeedType.FULL_UTILITY
    }
}