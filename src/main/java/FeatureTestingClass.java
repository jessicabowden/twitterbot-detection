import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import twitter4j.*;

import java.util.*;

/**
 * Created by Jessica on 19/11/2014.
 */
public class FeatureTestingClass {
    GetTweetsByUser getTweetsByUser = new GetTweetsByUser();
    Utilities utilities = new Utilities();

    public int numberOfRetweetsForUser(String user) {
        //we want to go to the user's profile
        //and analyse if a large portion of their tweets are retweets
        ResponseList<Status> tweets = getTweetsByUser.getTweetsFromUser(1000, user);

        int count = 0;
        for (Status status : tweets) {
            if (status.isRetweet()) {
                count++;
            }
        }
        return count;
    }

    public int numberOfLinksByUser(String user) {
        ResponseList<Status> tweets = getTweetsByUser.getTweetsFromUser(1000, user);

        int urlCount = 0;

        for (Status status : tweets) {
            URLEntity[] urls = status.getURLEntities();
            for (URLEntity url : urls) {
//                String shorturl = url.getURL();
//                String longurl = utilities.expandShortURL(shorturl);
//                System.out.println(longurl);
                urlCount++;
            }
        }
        return urlCount;
    }

    public ArrayList<String> listOfLinksFromUser(String user) {
        ResponseList<Status> tweets = getTweetsByUser.getTweetsFromUser(1000, user);
        ArrayList<String> urllist = Lists.newArrayList();

        for (Status status : tweets) {
            URLEntity[] urls = status.getURLEntities();
            for (URLEntity url : urls) {
                String shorturl = url.getURL();
//                String longurl = utilities.reverseShortenedURL(shorturl);
                String longurl = "";
                try {
                    Thread.sleep(5000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                urllist.add(longurl);
            }
        }
        return urllist;
    }

    public void mostCommonSites() throws InterruptedException {
        ArrayList<String> nonbots = utilities.sampleNonBots();
        ArrayList<String> bots = utilities.sampleBots();

        System.out.println("Non-bots:");
        for (String nonbot : nonbots) {
            System.out.println("----------" + nonbot + "-----------");
            System.out.println("LINKS: " + listOfLinksFromUser(nonbot));
            Thread.sleep(5000);
        }
//
//        System.out.println("Bots:");
//        for (String bot : bots) {
//            System.out.println(bot);
//            System.out.println(listOfLinksFromUser(bot));
//            Thread.sleep(5000);
//        }
    }

    public ArrayList<String> mostPopularLocation(String user) {
        ResponseList<Status> tweets = getTweetsByUser.getTweetsFromUser(10, user);
        SortedMap<String, Integer> tweetOrigins = Maps.newTreeMap();
        ArrayList<String> topSources = Lists.newArrayList();

        for (Status status : tweets) {
            String tweetOrigin = utilities.extractTextFromSource(status.getSource());
            if (tweetOrigins.containsKey(tweetOrigin)) {
                tweetOrigins.replace(tweetOrigin, tweetOrigins.get(tweetOrigin) + 1);
            }
            else {
                tweetOrigins.put(tweetOrigin, 1);
            }
        }

        if (tweetOrigins.size() < 3) {
            topSources.add(tweetOrigins.firstKey());
        }
        else if (tweetOrigins.size() <2) {
            int i = 1;
            while (i < 2) {
                topSources.add(tweetOrigins.lastKey());
                tweetOrigins.remove(tweetOrigins.lastKey());
                i++;
            }
        }
        else {
            int i = 1;
            while (i < 3) {
                topSources.add(tweetOrigins.lastKey());
                tweetOrigins.remove(tweetOrigins.lastKey());
                i++;
            }
        }

        return topSources;
    }

    public ArrayList<Integer> followersToFollowing(String username) {
        twitter4j.User u = getTweetsByUser.getUserObject(username);
        Integer followers = u.getFollowersCount();
        Integer following = u.getFriendsCount();
        ArrayList<Integer> ratio = Lists.newArrayList();
        ratio.add(followers);
        ratio.add(following);
        return ratio;
    }

    public void followersToFollowingAsPerecntage(ArrayList<Integer> counts) {
        Integer followers = counts.get(0);
        Integer following = counts.get(1);
    }

    public static void main(String[] args) throws InterruptedException {
        FeatureTestingClass featureTestingClass = new FeatureTestingClass();
        Utilities utilities1 = new Utilities();

        ArrayList<String> bots = utilities1.sampleBots();
        ArrayList<String> nonbots = utilities1.sampleNonBots();

        ArrayList<String> botinfo = Lists.newArrayList();
        ArrayList<String> nonbotinfo = Lists.newArrayList();

        for (String bot : bots) {
            botinfo.add(bot);
            botinfo.add(featureTestingClass.mostPopularLocation(bot).toString());
            botinfo.add("\n");
        }
        utilities1.stringToFile(botinfo, "botinfo.txt");

        for (String nonbot : nonbots) {
            nonbotinfo.add(nonbot);
            nonbotinfo.add(featureTestingClass.mostPopularLocation(nonbot).toString());
            nonbotinfo.add("\n");
        }
        utilities1.stringToFile(nonbotinfo, "nonbotinfo.txt");

    }
}
