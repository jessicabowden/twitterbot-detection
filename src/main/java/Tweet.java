/**
 * Created by Jessica on 18/11/2014.
 */
public class Tweet {
    long tweet_id;
    String status;
    long tweeters_id;
    User user;
    String url;

    public Tweet(long tweet_id, String status, long tweeters_id, String url) {
        this.tweet_id = tweet_id;
        this.status = status;
        this.tweeters_id = tweeters_id;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public User getUser() {
        return user;
    }

    public User createUser(String username, boolean isBot, long user_id) {
        System.out.println("creating user");
//        user = new User(username, isBot, user_id);
        return user;
    }

    public long getTweet_id() {
        return tweet_id;
    }

    public void setTweet_id(long tweet_id) {
        this.tweet_id = tweet_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTweeters_id() {
        return tweeters_id;
    }

    public void setTweeters_id(long tweeters_id) {
        this.tweeters_id = tweeters_id;
    }
}
