import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Created by Jessica on 21/03/2015.
 */
public class TopWordsInTweets {
    Utilities utilities = new Utilities();
    MapUtils mapUtils = new MapUtils();
    RetrieveData retrieveData = new RetrieveData();

    //might have to manually look for trends btwn top ten words & top ten stop words
        // before further doing stuff and things
    public ArrayList<String> topTenWords(Long userId) {
        ArrayList<String> tweets = retrieveData.getTweets(userId);
        HashMap<String, Integer> topWords = topWords(userId);

        ArrayList<String> sortedWords = mapUtils.sortedMap(topWords);
        ArrayList<String> topList = mapUtils.getTopList(sortedWords, 10);

        return topList;
    }

    public HashMap<String, Integer> stopWordsMap(Long userId) {
        HashMap<String, Integer> topWords = topWords(userId);
        HashMap<String, Integer> newTopWords = Maps.newHashMap();

        Iterator it = topWords.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, Integer> current = (Map.Entry) it.next();
            String currentWord = current.getKey();

            if (utilities.isStopWord(currentWord)) {
                newTopWords.put(currentWord, current.getValue());
            }
        }

        return newTopWords;
    }

    public ArrayList<String> topTenStopWords(Long userId) {
        HashMap<String, Integer> newTopWords = stopWordsMap(userId);

        ArrayList<String> sorted = mapUtils.sortedMap(newTopWords);

        if (sorted.size() > 10) {
            return mapUtils.getFirstNElements(sorted, 10);
        }

        return sorted;
    }

    public HashMap<String, Integer> nonStopWordsMap(Long userId) {
        HashMap<String, Integer> topWords = topWords(userId);
        HashMap<String, Integer> newTopWords = Maps.newHashMap();

        Iterator it = topWords.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, Integer> current = (Map.Entry) it.next();
            String currentWord = current.getKey();

            if (!utilities.isStopWord(currentWord)) {
                newTopWords.put(currentWord, current.getValue());
            }
        }

        return newTopWords;
    }

    public ArrayList<String> topTenNonStopWords(Long userId) {
        HashMap<String, Integer> newTopWords = nonStopWordsMap(userId);

        ArrayList<String> sorted = mapUtils.sortedMap(newTopWords);

        if (sorted.size() > 10) {
            return mapUtils.getFirstNElements(sorted, 10);
        }

        return sorted;
    }

    private HashMap<String, Integer> topWords(Long userId) {
        ArrayList<String> tweets = retrieveData.getTweets(userId);
        HashMap<String, Integer> topWords = Maps.newHashMap();

        for (String tweet : tweets) {
            String[] indWords = tweet.split(" ");

            for (String word : indWords) {
                String lowerCase = word.toLowerCase();
                if (topWords.containsKey(lowerCase)) {
                    topWords.put(lowerCase, topWords.get(lowerCase) + 1);
                } else {
                    topWords.put(lowerCase, 1);
                }
            }
        }

        return topWords;
    }

    public String ratio(Long userId) {
        Integer nonStopWord = 0;
        Integer stopWord = 0;

        HashMap<String, Integer> stopWords = nonStopWordsMap(userId);
        HashMap<String, Integer> nonStopWords = stopWordsMap(userId);

        Iterator stopIter = stopWords.entrySet().iterator();

        while (stopIter.hasNext()) {
            Map.Entry<String, Integer> current = (Map.Entry) stopIter.next();
            if (current.getValue() > stopWord) {
                stopWord = current.getValue();
            }
        }

        Iterator nonStopIter = nonStopWords.entrySet().iterator();

        while (nonStopIter.hasNext()) {
            Map.Entry<String, Integer> current = (Map.Entry) nonStopIter.next();
            if (current.getValue() > nonStopWord) {
                nonStopWord = current.getValue();
            }
        }

        FriendsToFollowersRatio f2f = new FriendsToFollowersRatio();
        Double r = f2f.getRatio(new Double(nonStopWord), new Double(stopWord));

        return r.toString();

//        return nonStopWord.toString() + ":" + stopWord.toString();
    }

    public static void main(String[] args) {
        //Test

        TopWordsInTweets topWordsInTweets = new TopWordsInTweets();

        Long user = 14304170L;

//        System.out.println(topWordsInTweets.topTenNonStopWords(user));
//        System.out.println(topWordsInTweets.topTenStopWords(user));
        System.out.println(topWordsInTweets.ratio(user));
    }
}
