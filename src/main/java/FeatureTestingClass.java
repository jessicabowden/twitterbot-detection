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
    MapUtils mapUtils = new MapUtils();

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

    public ArrayList<String> listOfLinksFromUser(String user)  {
        ResponseList<Status> tweets = getTweetsByUser.getTweetsFromUser(100, user);
        ArrayList<String> urllist = Lists.newArrayList();

        for (Status status : tweets) {
            URLEntity[] urls = status.getURLEntities();
            for (URLEntity url : urls) {
                String fullUrl = null;
                String shortenedUrl = url.toString();

                String expandedUrl = url.getExpandedURL();
                urllist.add(expandedUrl);

                //should only happen if necessary otherwise slows program down too much
//                try {
//                    URL full = utilities.expand(new URL(expandedUrl));
//                    fullUrl = full.toString();
//                    urllist.add(fullUrl);
//                }
//                catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
            }
        }
        return urllist;
    }

    public ArrayList<String> mostCommonSites(String user) {
        MapUtils mapTest = new MapUtils();

        HashMap<String, Integer> topSites = Maps.newHashMap();

        ArrayList<String> links = listOfLinksFromUser(user);
        for (String link : links) {
            String baseUrl = utilities.getBaseURL(link);
            if (topSites.containsKey(baseUrl)) {
                topSites.put(baseUrl, topSites.get(baseUrl) + 1);
            }
            else {
                topSites.put(baseUrl, 1);
            }
        }

        ArrayList<String> topList = mapTest.sortedMap(topSites);
        ArrayList<String> topThree = mapTest.getTopList(topList, 3);

        return topThree;
    }

    public ArrayList<String> mostPopularLocation(String user) {
        ResponseList<Status> tweets = getTweetsByUser.getTweetsFromUser(100, user);
        SortedMap<String, Integer> tweetOrigins = Maps.newTreeMap();
        ArrayList<String> topSources = Lists.newArrayList();

        for (Status status : tweets) {
            String tweetOrigin = utilities.extractTextFromSource(status.getSource());
            if (tweetOrigins.containsKey(tweetOrigin)) {
//                tweetOrigins.replace(tweetOrigin, tweetOrigins.get(tweetOrigin) + 1);
                tweetOrigins.put(tweetOrigin, tweetOrigins.get(tweetOrigin) + 1);
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

    public void tweetsWithEmoticons(String user) {
        //Use class EmojiTest
    }

    public ArrayList<Date> getFrequency(String user) {
        ResponseList<Status> tweets = getTweetsByUser.getTweetsFromUser(10, user);
        ArrayList<Date> dates = Lists.newArrayList();

        for (Status status : tweets) {
            Date createdAt = status.getCreatedAt();
            dates.add(createdAt);
        }

        return dates;
    }

    public ArrayList<String> topTenWords(String user) {
        ResponseList<Status> tweets = getTweetsByUser.getTweetsFromUser(10, user);

        for (Status status : tweets) {
            String text = status.getText();
            String[] indWords = text.split(" ");

            HashMap<String, Integer> topWords = Maps.newHashMap();
            for (String word : indWords) {
                if (topWords.containsKey(word)) {
                    topWords.put(word, topWords.get(word) + 1);
                } else {
                    topWords.put(word, 1);
                }
            }

        }

        ArrayList<String> sortedWords = mapUtils.sortedMap(topWords);
        ArrayList<String> topList = mapUtils.getTopList(sortedWords, 10);

        return topList;

    }

    public static void main(String[] args) throws InterruptedException {
        FeatureTestingClass featureTestingClass = new FeatureTestingClass();
        Utilities utilities1 = new Utilities();

        ArrayList<String> bots = utilities1.sampleBots();
        ArrayList<String> nonbots = utilities1.sampleNonBots();

        ArrayList<String> botinfo = Lists.newArrayList();
        ArrayList<String> nonbotinfo = Lists.newArrayList();

        System.out.println(featureTestingClass.getFrequency("MbowdenJessica"));

//        for (String nonbot : nonbots) {
//            nonbotinfo.add(nonbot);
//            nonbotinfo.add(featureTestingClass.mostCommonSites(nonbot).toString());
//            nonbotinfo.add("\n");
//        }
//        utilities1.stringToFile(nonbotinfo, "nonbotinfo.txt");

//        for (String bot : bots) {
//            botinfo.add(bot);
//            botinfo.add(featureTestingClass.mostCommonSites(bot).toString());
//            botinfo.add("\n");
//        }
//        utilities1.stringToFile(botinfo, "botinfo.txt");



        //emoji char: ?

    }
}
