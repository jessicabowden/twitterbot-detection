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
//        emoticons.add(":3");
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

//    public void hasEmoji(String status) {
//                String regexPattern = "[\uD83C-\uDBFF\uDC00-\uDFFF]+";
        ////        String regexPattern = "[\uD83C\uDBFF\uDC00\uDFFF]+";
        //
        //        byte[] utf8 = new byte[0];
        //
        //        String string1 = null;
        //
        //        try {
        //            utf8 = status.getBytes("UTF-8");
        //            string1 = new String(utf8, "UTF-8");
        //        } catch (UnsupportedEncodingException e) {
        //            e.printStackTrace();
        //        }
        //
        //        Pattern pattern = Pattern.compile(regexPattern);
        //        Matcher matcher = pattern.matcher(string1);
        //        List<String> matchList = new ArrayList<String>();
        //
        //        while (matcher.find()) {
        //            matchList.add(matcher.group());
        ////            return true;
        //        }
        //
        //        for (int i = 0; i < matchList.size(); i++) {
        //            System.out.println(i + ":" + matchList.get(i));
        //        }
        //
        //        System.out.println(matchList.size());
        //
        ////        return false;
        //    }

    public static void main(String[] args) {
        Long user = 14304170L;

        EmoticonFinder emoticonFinder = new EmoticonFinder();

        System.out.println(emoticonFinder.emoticons(user).size());
//        String s = "Emoticon tweet \uD83D\uDE34\uD83D\uDE04";

//        System.out.println(emoticonFinder.hasEmoji(s));

    }
}
