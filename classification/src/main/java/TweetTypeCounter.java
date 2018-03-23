/**
 * Created by Jessica on 21/03/2015.
 */
public class TweetTypeCounter {
    RetrieveData retrieveData = new RetrieveData();

    public Integer numberOfRetweets(Long userId) {
        String sql = "SELECT COUNT(1) FROM tweet WHERE is_retweet = 1 AND tweeters_id = " + userId;
        Integer retweets = retrieveData.queryForCount(sql);
        return retweets;
    }

    public Integer numberOfLinks(Long userId) {
        String sql = "SELECT COUNT(1) FROM tweet, url, user WHERE tweet.tweet_id = url.tweet_id AND tweet.tweeters_id = " +
                "user.user_id AND tweet.tweeters_id = " + userId + " ORDER BY tweet.tweeters_id";
        Integer links = retrieveData.queryForCount(sql);
        return links;
    }

    public Integer numberOfHashtags(Long userId) {
        String sql = "select count(1) from tweet, hashtag, user WHERE tweet.tweet_id = hashtag.tweet_id AND tweet.tweeters_id "
                + "= user.user_id AND tweet.tweeters_id = " + userId + " ORDER BY tweet.tweeters_id";

        Integer hashtags = retrieveData.queryForCount(sql);
        return hashtags;
    }

    public Integer numberOfTweets(Long userId) {
        String sql = "select count(1) from tweet where tweet.tweeters_id = " + userId;

        Integer tweets = retrieveData.queryForCount(sql);
        return tweets;
    }
}
