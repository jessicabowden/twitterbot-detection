import java.util.ArrayList;

import org.json.JSONObject;

import com.google.common.collect.Lists;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.json.DataObjectFactory;

/**
 * Created by Jessica on 10/11/2014.
 */
public class GetTweetObjects {
    Twitter twitter = new TwitterFactory().getInstance();

    public ArrayList<JSONObject> getTweetsFromUser(int numberOfTweets, String username) {
        ArrayList<JSONObject> tweets = Lists.newArrayList();

        if (!(checkIfUserSuspended(username))) {
            int pagingsRequired = numberOfTweets / 100;
            int i = 1;

            if (pagingsRequired >= 100) {
                while (i <= pagingsRequired) {
                    Paging currPager = new Paging(i, 100);
                    ResponseList<Status> current;
                    try {
                        current = twitter.getUserTimeline(username, currPager);
                        for (Status status : current) {
                            String rawJSON = DataObjectFactory.getRawJSON(status);
                            JSONObject tweetAsObj = new JSONObject(rawJSON);
                            tweets.add(tweetAsObj);
                        }
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
            } else {
                ResponseList<Status> current;
                try {
                    current = twitter.getUserTimeline(username);
                    for (Status status : current) {
                        String rawJSON = DataObjectFactory.getRawJSON(status);
                        JSONObject tweetAsObj = new JSONObject(rawJSON);
                        tweets.add(tweetAsObj);
                    }
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
        }

        return tweets;
    }

    public User getUserObject(String username) {
        User user = null;
        try {
            user = twitter.showUser(username);
        }
        catch (TwitterException e) {
            e.printStackTrace();
        }

        return user;
    }

    public Status getStatusFromId(long statusid) {
        Status status = null;
        try {
            status = twitter.showStatus(statusid);
        } catch (TwitterException e) {
            System.out.println("Couldn't create status from given id");
            e.printStackTrace();
        }

        return status;
    }

    public boolean checkIfUserSuspended(String username) {
        try {
            twitter.showUser(username);
        }
        catch (TwitterException e) {
            if (e.getErrorMessage().equals("User has been suspended.")) {
                return true;
            }
            else {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void main(String[] args) {
        GetTweetObjects getTweetObjects = new GetTweetObjects();

        String suspendedUser = "iqegolubileje22";
        String nonSuspended = "jessicambowden";

        ArrayList<String> users = Lists.newArrayList();
        users.add(suspendedUser);
        users.add(nonSuspended);

//        System.out.println(getTweetObjects.checkIfUserSuspended(nonSuspended));

        for (String user : users) {
            System.out.println(user + getTweetObjects.checkIfUserSuspended(user));
        }

    }
}
