import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;
import com.vdurmont.emoji.EmojiParser;

/**
 * Created by Jessica on 21/03/2015.
 */
public class EmoticonFinder {
    RetrieveData retrieveData = new RetrieveData();

    public ArrayList<String> emoticons(Long userId) {
        ArrayList<String> tweets = retrieveData.getTweets(userId);

        ArrayList<String> tweetsWithEmoticons = Lists.newArrayList();

        for (String tweet : tweets) {
            if (hasEmoji(tweet).size() > 0) {
                tweetsWithEmoticons.add(tweet);
            }
            else if (findCommonEmoticons(tweet)) {
                tweetsWithEmoticons.add(tweet);
            }
        }

        return tweetsWithEmoticons;
    }

    private boolean findCommonEmoticons(String status) {
        ArrayList<String> emoticons = Lists.newArrayList();

        emoticons.add(":)");
        emoticons.add(":-)");
        emoticons.add(":(");
        emoticons.add(":-(");
        emoticons.add(":D");
        emoticons.add(":-D");
        emoticons.add(";)");
        emoticons.add(";-)");
        emoticons.add(":P");
        emoticons.add(":-P");
        emoticons.add("<3");
        emoticons.add(":|");
        emoticons.add(":-|");
        emoticons.add(":S");
        emoticons.add(":-S");
        emoticons.add(":*");
        emoticons.add(":-*");
        emoticons.add(":O");
        emoticons.add(":-O");
        emoticons.add(":p");
        emoticons.add(":|");
        emoticons.add(":'(");

        for (String emoticon : emoticons) {
            if (status.contains(emoticon)) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<String> hasEmoji(String tweet) {
        String parsed = EmojiParser.parseToAliases(tweet);
        ArrayList<String> emoji = Lists.newArrayList();

        if (parsed != tweet) {

            String pattern = "(:\\w+:)+";
            Pattern p = Pattern.compile(pattern);
            Matcher matcher = p.matcher(parsed);

            while (matcher.find()) {
                String currentMatch = matcher.group();
                String[] splitMatch = currentMatch.split(":");

                for (int i = 0; i < splitMatch.length; i++) {
                    if (splitMatch[i].length() > 0) {
                        emoji.add(splitMatch[i]);
                    }
                }
            }

        }

        return emoji;
    }
}
