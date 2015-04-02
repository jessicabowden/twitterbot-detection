import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.Maps;

/**
 * Created by Jessica on 21/03/2015.
 */
public class TopWordsInTweets {
    Utilities utilities = new Utilities();
    MapUtils mapUtils = new MapUtils();
    RetrieveData retrieveData = new RetrieveData();

    //might have to manually look for trends btwn top ten words & top ten stop words
    public ArrayList<String> topTenWords(Long userId) {
        ArrayList<String> tweets = retrieveData.getTweets(userId);
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
        ArrayList<String> tweets = retrieveData.getTweets(userId);
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
}
