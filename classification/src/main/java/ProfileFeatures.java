import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Created by Jessica on 05/02/2015.
 */
public class ProfileFeatures {
    RetrieveData retrieveDataDB = new RetrieveData();
    MapUtils mapUtils = new MapUtils();
    Utilities utilities = new Utilities();

    public Integer numberOfRetweets(Long userId) {
        String sql = "SELECT COUNT(1) FROM tweet WHERE is_retweet = 1 AND tweeters_id = " + userId;
        Integer retweets = retrieveDataDB.queryForCount(sql);
        return retweets;
    }

    public Integer numberOfLinks(Long userId) {
    String sql = "SELECT COUNT(1) FROM tweet, url, user WHERE tweet.tweet_id = url.tweet_id AND tweet.tweeters_id = " +
        "user.user_id AND tweet.tweeters_id = " + userId + " ORDER BY tweet.tweeters_id";
        Integer links = retrieveDataDB.queryForCount(sql);
        return links;
    }

    //returns everything that is stored about a URL so that domain + expanded url can be extracted from this for use in other methods
    public void listOfUrls(Long userId) {
        String sql = "SELECT url, expanded_url, domain, tweet_id FROM tweet, url, user WHERE  tweet.tweet_id = url.tweet_id AND "
                + "tweet.tweeters_id = ? ORDER BY tweet.tweeters_id";
    }

    public void mostCommonDomain(Long userId) {
        String sql = "SELECT COUNT(DISTINCT domain) FROM tweet, url, user WHERE tweet.tweet_id = url.tweet_id AND "
                + "tweet.tweeters_id = ? ORDER BY tweet.tweeters_id";
    }

    public void mostCommonSource(Long userId) {
        String sql = "SELECT source, COUNT(source) FROM tweet WHERE tweeters_id = ? GROUP BY source;";
    }

    public ArrayList<String> emoticons(Long userId) {
        ArrayList<String> tweets = retrieveDataDB.getTweets(userId);

        EmojiFinder emojiFinder = new EmojiFinder();
        ArrayList<String> tweetsWithEmoticons = Lists.newArrayList();

        for (String tweet : tweets) {
            if (emojiFinder.findEmojiTweets(tweet) != null) {
                tweetsWithEmoticons.add(tweet);
            }
        }

        return tweetsWithEmoticons;
    }

    //might have to manually look for trends btwn top ten words & top ten stop words
    public ArrayList<String> topTenWords(Long userId) {
        ArrayList<String> tweets = retrieveDataDB.getTweets(userId);
        HashMap<String, Integer> topWords = topWords(userId);

        ArrayList<String> sortedWords = mapUtils.sortedMap(topWords);
        ArrayList<String> topList = mapUtils.getTopList(sortedWords, 10);

        return topList;
    }

    public ArrayList<String> topTenStopWords(Long userId) {
        ArrayList<String> stopWords = utilities.getStopWords();
        HashMap<String, Integer> topWords = topWords(userId);

        for(String key : topWords.keySet()) {
            Integer value = topWords.get(key);

            boolean isStopWord = false;
            for (String stopWord : stopWords) {
                if (stopWord.equals(key)) {
                    isStopWord = true;
                }
            }
            if (!isStopWord) {
                topWords.remove(key);
            }
        }

        ArrayList<String> sortedStopWords = mapUtils.sortedMap(topWords);
        ArrayList<String> topTen = mapUtils.getTopList(sortedStopWords, 10);

        return topTen;
    }

    public ArrayList<String> topTenNonStopWords(Long userId) {
        ArrayList<String> stopWords = utilities.getStopWords();
        HashMap<String, Integer> topWords = topWords(userId);

        for(String key : topWords.keySet()) {
            Integer value = topWords.get(key);

            boolean isStopWord = false;
            for (String stopWord : stopWords) {
                if (stopWord.equals(key)) {
                    isStopWord = true;
                }
            }
            if (isStopWord) {
                topWords.remove(key);
            }
        }

        ArrayList<String> sortedStopWords = mapUtils.sortedMap(topWords);
        ArrayList<String> topTen = mapUtils.getTopList(sortedStopWords, 10);

        return topTen;
    }

    private HashMap<String, Integer> topWords(Long userId) {
        ArrayList<String> tweets = retrieveDataDB.getTweets(userId);
        HashMap<String, Integer> topWords = Maps.newHashMap();

        for (String tweet : tweets) {
            String[] indWords = tweet.split(" ");

            for (String word : indWords) {
                if (topWords.containsKey(word)) {
                    topWords.put(word, topWords.get(word) + 1);
                } else {
                    topWords.put(word, 1);
                }
            }
        }

        return topWords;
    }

    //top word in an individual tweet
    //top stop word in an individual tweet



    public static void main(String[] args) {
        ProfileFeatures profileFeatures = new ProfileFeatures();
        Long user = new Long(16335974);
    }
}
