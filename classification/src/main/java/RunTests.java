import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.common.collect.Lists;
import opennlp.tools.doccat.DocumentCategorizerME;

/**
 * Created by Jessica on 02/04/2015.
 */
public class RunTests {
    GatherUsersForTesting testing = new GatherUsersForTesting();
    EmoticonFinder emoticonFinder = new EmoticonFinder();
    TweetTypeCounter tweetTypeCounter = new TweetTypeCounter();
    Utilities utilities = new Utilities();
    RetrieveData retrieveData = new RetrieveData();

    ArrayList<Long> bots = testing.gatherBots();
    ArrayList<Long> humans = testing.gatherHumans();

    ArrayList<Long> halfBots = testing.gatherFirstHalfBots();
    ArrayList<Long> halfHumans = testing.gatherFirstHalfHumans();

    ArrayList<Long> testingBots = testing.gatherBotsTesting();
    ArrayList<Long> testingHumans = testing.gatherHumansTesting();

    public void hashtags() {
        for (Long human : humans) {
            int totalTweets = tweetTypeCounter.numberOfTweets(human);
            int totalHashtags = tweetTypeCounter.numberOfHashtags(human);

            Float percentage = (float) ((totalHashtags * 100) / totalTweets);

            utilities.writeToExistingFile(percentage.toString(), "humanHashtags.txt");

        }

        for (Long bot : bots) {
            int totalTweets = tweetTypeCounter.numberOfTweets(bot);
            int totalHashtags = tweetTypeCounter.numberOfHashtags(bot);

            Float percentage = (float) ((totalHashtags * 100) / totalTweets);

            utilities.writeToExistingFile(percentage.toString(), "botHashtags.txt");
        }
    }

    public void links() {
        for (Long human : humans) {
            int totalTweets = tweetTypeCounter.numberOfTweets(human);
            int numberOfLinks = tweetTypeCounter.numberOfLinks(human);

            Float percentage = (float) ((numberOfLinks * 100) / totalTweets);

            utilities.writeToExistingFile(percentage.toString(), "humanLinks.txt");

        }

        for (Long bot : bots) {
            int totalTweets = tweetTypeCounter.numberOfTweets(bot);
            int numberOfLinks = tweetTypeCounter.numberOfLinks(bot);

            Float percentage = (float) ((numberOfLinks * 100) / totalTweets);

            utilities.writeToExistingFile(percentage.toString(), "botLinks.txt");
        }
    }

    public void retweets() {
        for (Long human : humans) {
            int totalTweets = tweetTypeCounter.numberOfTweets(human);
            int numberOfRetweets = tweetTypeCounter.numberOfRetweets(human);

            Float percentage = (float) ((numberOfRetweets * 100) / totalTweets);

            utilities.writeToExistingFile(percentage.toString(), "humanRetweets..txt");

        }

        for (Long bot : bots) {
            int totalTweets = tweetTypeCounter.numberOfTweets(bot);
            int numberOfRetweets = tweetTypeCounter.numberOfRetweets(bot);

            Float percentage = (float) ((numberOfRetweets * 100) / totalTweets);

            utilities.writeToExistingFile(percentage.toString(), "botRetweets.txt");
        }
    }

    public void doSources() {
        Source source = new Source();
        MapUtils mapUtils = new MapUtils();

        ArrayList<String> humanSources = Lists.newArrayList();
        ArrayList<String> botSources = Lists.newArrayList();

        for (Long human : humans) {
            String currSource = source.mostCommonSource(human);
            humanSources.add(currSource);
        }

        for (Long bot : bots) {
            String currSource = source.mostCommonSource(bot);
            botSources.add(currSource);
        }

        HashMap<String, Integer> humanSourcesMap = mapUtils.getIndividualCounts(humanSources);
        HashMap<String, Integer> botSourcesMap = mapUtils.getIndividualCounts(botSources);

        HashMap<String, Integer> orderedHumanSources = mapUtils.sortByValueLargestFirst(humanSourcesMap);
        HashMap<String, Integer> orderedBotSources = mapUtils.sortByValueLargestFirst(botSourcesMap);


        Iterator it = orderedHumanSources.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String pairString = pair.toString();

            utilities.writeToExistingFile(pairString, "humanSources.txt");
        }

        System.out.println(orderedBotSources);

        Iterator it2 = orderedBotSources.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry pair = (Map.Entry) it2.next();
            String pairString = pair.toString();

            utilities.writeToExistingFile(pairString, "botSources.txt");
        }
    }

    public void doDomains() {
        UrlAnalyzer urlAnalyzer = new UrlAnalyzer();
        MapUtils mapUtils = new MapUtils();

        ArrayList<String> humanSources = Lists.newArrayList();
        ArrayList<String> botSources = Lists.newArrayList();

        for (Long human : humans) {
            String currSource = urlAnalyzer.mostCommonDomain(human);
            humanSources.add(currSource);
        }

        for (Long bot : bots) {
            String currSource = urlAnalyzer.mostCommonDomain(bot);
            botSources.add(currSource);
        }

        HashMap<String, Integer> humanSourcesMap = mapUtils.getIndividualCounts(humanSources);
        HashMap<String, Integer> botSourcesMap = mapUtils.getIndividualCounts(botSources);

        HashMap<String, Integer> orderedHumanSources = mapUtils.sortByValueLargestFirst(humanSourcesMap);
        HashMap<String, Integer> orderedBotSources = mapUtils.sortByValueLargestFirst(botSourcesMap);

        Iterator it = orderedHumanSources.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String pairString = pair.toString();

            utilities.writeToExistingFile(pairString, "humanDomains.txt");
        }

        Iterator it2 = orderedBotSources.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry pair = (Map.Entry) it2.next();
            String pairString = pair.toString();

            utilities.writeToExistingFile(pairString, "botDomains.txt");
        }
    }

    public void topWords() {
        GatherUsersForTesting testing = new GatherUsersForTesting();
        MapUtils mapUtils = new MapUtils();
        Utilities utilities = new Utilities();
        ArrayList<Long> bots = testing.gatherBots();
        ArrayList<Long> humans = testing.gatherHumans();

        TopWordsInTweets topWordsInTweets = new TopWordsInTweets();

        ArrayList<String> humanResults = Lists.newArrayList();
        ArrayList<String> botResults = Lists.newArrayList();

        for (Long human : humans) {
            String topWord = topWordsInTweets.topTenWords(human).get(0);
            humanResults.add(topWord);
        }

        HashMap<String, Integer> humanWordsMap = mapUtils.getIndividualCounts(humanResults);
        HashMap<String, Integer> orderedHumanWords = mapUtils.sortByValueLargestFirst(
                humanWordsMap);

        Iterator it = orderedHumanWords.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String pairString = pair.toString();

            utilities.writeToExistingFile(pairString, "humanWords.txt");
        }

        for (Long bot : bots) {
            String topWord = topWordsInTweets.topTenWords(bot).get(0);
            botResults.add(topWord);
        }

        HashMap<String, Integer> botWordsMap = mapUtils.getIndividualCounts(botResults);
        HashMap<String, Integer> orderedBotWords = mapUtils.sortByValueLargestFirst(
                botWordsMap);

        Iterator it2 = orderedBotWords.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry pair = (Map.Entry) it2.next();
            String pairString = pair.toString();

            utilities.writeToExistingFile(pairString, "botWords.txt");
        }
    }

    public void topStopWords() {
        MapUtils mapUtils = new MapUtils();

        TopWordsInTweets topWordsInTweets = new TopWordsInTweets();

        ArrayList<String> humanResults = Lists.newArrayList();
        ArrayList<String> botResults = Lists.newArrayList();

        for (Long human : humans) {
            String topWord = topWordsInTweets.topTenStopWords(human).get(0);
            humanResults.add(topWord);
        }

        HashMap<String, Integer> humanWordsMap = mapUtils.getIndividualCounts(humanResults);
        HashMap<String, Integer> orderedHumanWords = mapUtils.sortByValueLargestFirst(
                humanWordsMap);

        Iterator it = orderedHumanWords.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String pairString = pair.toString();

            utilities.writeToExistingFile(pairString, "humanStopWords.txt");
        }

        for (Long bot : bots) {
            if (topWordsInTweets.topTenStopWords(bot).size() != 0) {
                String topWord = topWordsInTweets.topTenStopWords(bot).get(0);
                botResults.add(topWord);
            }
        }

        HashMap<String, Integer> botWordsMap = mapUtils.getIndividualCounts(botResults);
        HashMap<String, Integer> orderedBotWords = mapUtils.sortByValueLargestFirst(
                botWordsMap);

        Iterator it2 = orderedBotWords.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry pair = (Map.Entry) it2.next();
            String pairString = pair.toString();

            utilities.writeToExistingFile(pairString, "botStopWords.txt");
        }
    }

    public void topNonStopWords() {
        MapUtils mapUtils = new MapUtils();

        TopWordsInTweets topWordsInTweets = new TopWordsInTweets();

        ArrayList<String> humanResults = Lists.newArrayList();
        ArrayList<String> botResults = Lists.newArrayList();

        for (Long human : humans) {
            String topWord = topWordsInTweets.topTenNonStopWords(human).get(0);
            humanResults.add(topWord);
        }

        HashMap<String, Integer> humanWordsMap = mapUtils.getIndividualCounts(humanResults);
        HashMap<String, Integer> orderedHumanWords = mapUtils.sortByValueLargestFirst(
                humanWordsMap);

        Iterator it = orderedHumanWords.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String pairString = pair.toString();

            utilities.writeToExistingFile(pairString, "humanNonStopWords.txt");
        }

        for (Long bot : bots) {
            String topWord = topWordsInTweets.topTenNonStopWords(bot).get(0);
            botResults.add(topWord);
        }

        HashMap<String, Integer> botWordsMap = mapUtils.getIndividualCounts(botResults);
        HashMap<String, Integer> orderedBotWords = mapUtils.sortByValueLargestFirst(
                botWordsMap);

        Iterator it2 = orderedBotWords.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry pair = (Map.Entry) it2.next();
            String pairString = pair.toString();

            utilities.writeToExistingFile(pairString, "botNonStopWords.txt");
        }
    }

    public void stopWordToNonRatio() {
        TopWordsInTweets topWordsInTweets = new TopWordsInTweets();

        for (Long bot : bots) {
            utilities.writeToExistingFile(topWordsInTweets.ratio(bot), "botRatio.txt");
        }

        for (Long human : humans) {
            utilities.writeToExistingFile(topWordsInTweets.ratio(human), "humanRatio.txt");
        }
    }

    public void emoticonStats() {
        ArrayList<Long> bots = testing.gatherBots();
        ArrayList<Long> humans = testing.gatherHumans();

        for (Long bot : bots) {
            System.out.println(bot);
            int count = emoticonFinder.emoticons(bot).size();

            int totalTweets = tweetTypeCounter.numberOfTweets(bot);

            Float percentage = (float) ((count * 100) / totalTweets);
            System.out.println(percentage);

            utilities.writeToExistingFile(percentage.toString() + "%", "botEmoticons.txt");
        }

        for (Long human : humans) {
            int count = emoticonFinder.emoticons(human).size();

            int totalTweets = tweetTypeCounter.numberOfTweets(human);

            Float percentage = (float) ((count * 100) / totalTweets);

            utilities.writeToExistingFile(percentage.toString() + "%", "humanEmoticons.txt");
        }
     }

    public void ratioTests() {
        FriendsToFollowersRatio friendsToFollowersRatio = new FriendsToFollowersRatio();

        for (Long bot : bots) {
            Double ratio = friendsToFollowersRatio.getFriendshipRatio(bot);
            utilities.writeToExistingFile(ratio.toString(), "botFriendshipRatio.txt");
        }

        for (Long human : humans) {
            Double ratio = friendsToFollowersRatio.getFriendshipRatio(human);

            utilities.writeToExistingFile(ratio.toString(), "humanFriendshipRatio.txt");
        }
    }

    public void friendsFollowers() {
        for (Long bot : bots) {
            ArrayList<Long> counts = retrieveData.getFriendsAndFollowers(bot);
            utilities.writeToExistingFile(counts.get(0) + " " + counts.get(1),"botFriends.txt");
        }

        for (Long human : humans) {
            ArrayList<Long> counts = retrieveData.getFriendsAndFollowers(human);
            utilities.writeToExistingFile(counts.get(0) + " " + counts.get(1),"humanFriends.txt");
        }

    }

    public void longitudinalTests() {
        LongitudinalTweetBehaviour longitudinal = new LongitudinalTweetBehaviour();

        for (Long bot : bots) {
            double result = longitudinal.getStdDeviationOfDurations(bot);
            String resultStr = new BigDecimal(result).toPlainString();
            System.out.println(result);
            utilities.writeToExistingFile(resultStr, "botLongitude.txt");
        }

        for (Long human : humans) {
            double result = longitudinal.getStdDeviationOfDurations(human);
            String resultStr = new BigDecimal(result).toPlainString();
            utilities.writeToExistingFile(resultStr, "humanLongitude.txt");
        }
    }

    public void longitudeHrsTests() {
        LongitudinalTweetBehaviour longitudinal = new LongitudinalTweetBehaviour();

        for (Long bot : bots) {
            double result = longitudinal.getStdDeviationOfHours(bot);
            String resultStr = new BigDecimal(result).toPlainString();
            utilities.writeToExistingFile(resultStr, "humanHourLongitude.txt");
        }

        for (Long human : humans) {
            double result = longitudinal.getStdDeviationOfHours(human);
            String resultStr = new BigDecimal(result).toPlainString();
            utilities.writeToExistingFile(resultStr, "botHourLongitude.txt");
        }
    }

    public void hoursTests() {
        LongitudinalTweetBehaviour longitudinal = new LongitudinalTweetBehaviour();

        for (Long bot : bots) {
            ArrayList<String> hours = longitudinal.getTimesFromTweets(bot);

            if (hours.size() > 0) {
                String result = hours.get(0);
                utilities.writeToExistingFile(result, "botHours.txt");
            }
        }

        for (Long human : humans) {
            ArrayList<String> hours = longitudinal.getTimesFromTweets(human);

            if (hours.size() > 0) {
                String result = hours.get(0);
                utilities.writeToExistingFile(result, "humanHours.txt");
            }
        }
    }

    public void avgTime() {
        LongitudinalTweetBehaviour longitudinal = new LongitudinalTweetBehaviour();

        for (Long bot : bots) {
            Integer hour = longitudinal.getAvgHour(bot);
            utilities.writeToExistingFile(hour.toString(), "botAvgTime.txt");
        }

        for (Long human : humans) {
            Integer hour = longitudinal.getAvgHour(human);
            utilities.writeToExistingFile(hour.toString(), "humanAvgTime.txt");
        }
    }

    public void duplicateTweets() {
        AnalyzeRepetition repetition = new AnalyzeRepetition();

        for (Long human : humans) {
            Integer result = repetition.numberOfDuplicateTweets(human);
            utilities.writeToExistingFile(result.toString(), "humanRepeatedTweets.txt");
        }

        for (Long bot : bots) {
            Integer result = repetition.numberOfDuplicateTweets(bot);
            utilities.writeToExistingFile(result.toString(), "botRepeatedTweets.txt");
        }
    }

    public void duplicateHashtags() {
        AnalyzeRepetition repetition = new AnalyzeRepetition();

        for (Long human : humans) {
            Integer result = repetition.numberOfDuplicateHashtags(human);
            utilities.writeToExistingFile(result.toString(), "humanRepeatedHashtags.txt");
        }

        for (Long bot : bots) {
            Integer result = repetition.numberOfDuplicateHashtags(bot);
            utilities.writeToExistingFile(result.toString(), "botRepeatedHashtags.txt");
        }
    }

    public void writeHalfTweetsToFile() {
        for (Long human : halfHumans) {
            ArrayList<String> tweets = retrieveData.getTweets(human);
            for (String tweet : tweets) {
                tweet = tweet.replaceAll("(\\r|\\n|\\r\\n)+", " ");
                tweet = utilities.removeStopWords(tweet);
                String toAdd = "human " + tweet;
                utilities.writeToExistingFile(toAdd, "allTweets.txt");
            }
        }

        for (Long bot : halfBots) {
            ArrayList<String> tweets = retrieveData.getTweets(bot);
            for (String tweet : tweets) {
                tweet = tweet.replaceAll("(\\r|\\n|\\r\\n)+", " ");
                tweet = utilities.removeStopWords(tweet);
                String toAdd = "bot " + tweet;
                utilities.writeToExistingFile(toAdd, "allTweets.txt");
            }
        }
    }

    public void writeTestingTweetsToFile() {
        for (Long human : testingHumans) {
            ArrayList<String> tweets = retrieveData.getTweets(human);
            for (String tweet : tweets) {
                tweet = tweet.replaceAll("(\\r|\\n|\\r\\n)+", " ");
                utilities.writeToExistingFile(tweet, "humanTesting.txt");
            }
        }

        for (Long bot : testingBots) {
            ArrayList<String> tweets = retrieveData.getTweets(bot);
            for (String tweet : tweets) {
                tweet = tweet.replaceAll("(\\r|\\n|\\r\\n)+", " ");
                utilities.writeToExistingFile(tweet, "botTesting.txt");
            }
        }
    }

    public void doNaiveBayesTest() {
        ArrayList<String> humanTweets = utilities.fileToArray("humanTesting.txt");
        ArrayList<String> botTweets = utilities.fileToArray("botTesting.txt");
        NaiveBayes naiveBayes = new NaiveBayes();
        DocumentCategorizerME categorizerME = naiveBayes.buildClassifier();

        utilities.writeToExistingFile("Humans:", "bayesMassivNoStopWords.txt");
        for (String humanTweet : humanTweets) {
            String result = naiveBayes.estimateBestCategory(humanTweet, categorizerME);
            utilities.writeToExistingFile(result, "bayesMassivNoStopWords.txt");
        }

        utilities.writeToExistingFile("Bots:", "bayesMassivNoStopWords.txt");
        for (String botTweet : botTweets) {
            String result = naiveBayes.estimateBestCategory(botTweet, categorizerME);
            utilities.writeToExistingFile(result, "bayesMassivNoStopWords.txt");
        }
    }
}
