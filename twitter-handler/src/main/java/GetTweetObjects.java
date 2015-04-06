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

    public ArrayList<JSONObject> getOneThousandTweets(String username) {
        ArrayList<JSONObject> tweets = Lists.newArrayList();

        if (!(checkIfUserSuspended(username))) {
            int pagesRequired = 10;
            int i = 1;

            while (i <= pagesRequired) {
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

    public Status getTweet(long tweetId) {
        Status status = null;

        try {
            status = twitter.showStatus(tweetId);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return status;
    }

    public void test() {
        Paging pg = new Paging();
        String userName = "ionlyretweetok";

            int numberOfTweets = 100;
            long lastID = Long.MAX_VALUE;
            ArrayList<Status> tweets = new ArrayList<Status>();
            while (tweets.size () < numberOfTweets) {
                try {
                    tweets.addAll(twitter.getUserTimeline(userName,pg));
                    System.out.println(("Gathered " + tweets.size() + " tweets"));
                    for (Status t: tweets)
                        if(t.getId() < lastID) lastID = t.getId();
                }
                catch (TwitterException te) {
                    System.out.println("Couldn't connect: " + te);
                }
                pg.setMaxId(lastID-1);
            }
    }

    public static void main(String[] args) {
        GetTweetObjects getTweetObjects = new GetTweetObjects();
        TwitterbotUtils twitterbotUtils = new TwitterbotUtils();
        Utilities utilities = new Utilities();

        System.out.println(getTweetObjects.getTweet(583987735528083457L));

    }
}
