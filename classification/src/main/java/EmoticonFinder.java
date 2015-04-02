import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;

/**
 * Created by Jessica on 21/03/2015.
 */
public class EmoticonFinder {
    RetrieveData retrieveData = new RetrieveData();

    public ArrayList<String> emoticons(Long userId) {
        ArrayList<String> tweets = retrieveData.getTweets(userId);

        ArrayList<String> tweetsWithEmoticons = Lists.newArrayList();

        for (String tweet : tweets) {
            if (findEmojiTweets(tweet) != null) {
                tweetsWithEmoticons.add(tweet);
            }
        }

        return tweetsWithEmoticons;
    }

    private ArrayList<String> findEmojiTweets(String original) {
        ArrayList<String> emojiTweets = Lists.newArrayList();
        String regexPattern = "[\uD83C-\uDBFF\uDC00-\uDFFF]+";
        byte[] utf8 = new byte[0];

        String string1 = null;

        try {
            utf8 = original.getBytes("UTF-8");
            string1 = new String(utf8, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(string1);
        List<String> matchList = new ArrayList<String>();

        while (matcher.find()) {
            matchList.add(matcher.group());
        }

        for(int i=0;i<matchList.size();i++){
            emojiTweets.add(original);
        }

        return emojiTweets;
    }
}
