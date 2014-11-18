import com.google.common.collect.Lists;
import twitter4j.Status;

import java.util.ArrayList;

/**
 * Created by Jessica on 18/11/2014.
 */
public class ExtractStatusStream {
    StatusStream statusStream = new StatusStream();
    InsertIntoTables insertIntoTables = new InsertIntoTables();

    //extracts data from Status objects returned
    public ArrayList<Tweet> extractData() {
        ArrayList<Tweet> tweets = Lists.newArrayList();
        for (Status status : statusStream.getStatuses()) {
            String url = "twitter.com/" + status.getUser().getScreenName() + "/status/" + status.getId();
            Tweet tweet = new Tweet(status.getId(), status.getText(), status.getUser().getId(), url);
            tweets.add(tweet);

            System.out.println("SOURCE " + status.getSource());
            System.out.println(status.getText());

            twitter4j.User twitterUser = status.getUser();
            boolean verified = twitterUser.isVerified();
            if (verified == true) {
                tweet.createUser(twitterUser.getScreenName(), false, twitterUser.getId());
            }
            else {
                tweet.createUser(twitterUser.getScreenName(), false, twitterUser.getId());
            }

        }
        return tweets;
    }

    public void insertTweets() {
        insertIntoTables.initialiseConnection();
        for (Tweet tweet : extractData()) {
            insertIntoTables.insertIntoTweetTable(tweet);
        }
    }

}
