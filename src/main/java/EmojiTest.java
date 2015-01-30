import com.google.common.collect.Lists;
import twitter4j.ResponseList;
import twitter4j.Status;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jessica on 29/01/2015.
 */
public class EmojiTest {
    public ArrayList<String> findEmojiTweets(String original) {
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

    public static void main(String[] args)  {
        GetTweetsByUser getTweetsByUser = new GetTweetsByUser();
        EmojiTest emojiTest = new EmojiTest();

        ResponseList<Status> tweets = getTweetsByUser.getTweetsFromUser(10, "MbowdenJessica");
        ArrayList<String> posts = Lists.newArrayList();

        for (Status status : tweets) {
            String text = status.getText();
            posts.add(text);
        }

        System.out.println("Emoji tweets:");
        for (String post : posts) {
            emojiTest.findEmojiTweets(post);
        }
    }
}
