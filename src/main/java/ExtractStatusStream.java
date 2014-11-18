import com.google.common.collect.Lists;
import twitter4j.Status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jessica on 18/11/2014.
 */
public class ExtractStatusStream {
    StatusStream statusStream = new StatusStream();
    InsertIntoTables insertIntoTables = new InsertIntoTables();
    Utilities utilities = new Utilities();
    Set<String> whitelist = utilities.getSourceWhitelist();

    //extracts data from Status objects returned
    public ArrayList<Tweet> extractData() {
        ArrayList<Tweet> tweets = Lists.newArrayList();
        for (Status status : statusStream.getStatuses()) {
            String url = "twitter.com/" + status.getUser().getScreenName() + "/status/" + status.getId();
            Tweet tweet = new Tweet(status.getId(), status.getText(), status.getUser().getId(), url);
            tweets.add(tweet);

            String source = utilities.extractTextFromSource(status.getSource());
            BotStatus botStatus = BotStatus.UNDECIDED;

            if (whitelist.contains(source) || status.getUser().isVerified()) {
                botStatus = BotStatus.FALSE;
            }

//            User user = new User(status.getUser().getScreenName(), isBot, )

        }
        return tweets;
    }

    public void insertTweets() {
        insertIntoTables.initialiseConnection();
        for (Tweet tweet : extractData()) {
            insertIntoTables.insertIntoTweetTable(tweet);
        }
    }

    public static void main(String[] args) {
        ExtractStatusStream extractStatusStream = new ExtractStatusStream();
        BotStatus botStatus = BotStatus.FALSE;
        System.out.println(botStatus);

    }

}
