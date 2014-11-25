import twitter4j.*;

/**
 * Created by Jessica on 10/11/2014.
 */
public class GetTweetsByUser {
    Twitter twitter = new TwitterFactory().getInstance();

    public ResponseList<Status> getTweetsFromUser(int numberOfTweets, String username) {
        Paging paging = new Paging(1,numberOfTweets);

        try {
            return twitter.getUserTimeline(username, paging);
        }
        catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        GetTweetsByUser getTweetsByUser = new GetTweetsByUser();
        ResponseList<Status> statuses = getTweetsByUser.getTweetsFromUser(100, "jessicambowden");
        for (Status status : statuses) {
            System.out.println(status);
        }


    }
}
