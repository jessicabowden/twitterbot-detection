import com.google.common.collect.Lists;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.URLEntity;

import java.util.ArrayList;

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

    public static void main(String[] args) throws InterruptedException {
        FeatureTestingClass featureTestingClass = new FeatureTestingClass();
//        System.out.println(featureTestingClass.numberOfRetweetsForUser("caindiru"));

//        featureTestingClass.mostCommonSites();

        System.out.println(featureTestingClass.listOfLinksFromUser("TriiSHE"));

    }
}
