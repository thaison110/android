package com.sonnv.kidsonline.model

class HomeNewsFeedModel: BaseFeed() {
    override fun getFeedType(): Int {
        return FeedType.HOME_NEWS_FEED
    }

}
