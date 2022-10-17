package com.sonnv.kidsonline.model

class TodayActivityModel: BaseFeed() {
    override fun getFeedType(): Int {
        return FeedType.TODAY_ACTIVITY
    }

}
