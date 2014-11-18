import java.util.ArrayList;

/**
 * Created by Jessica on 18/11/2014.
 */
public class Utilities {
    public String extractTextFromSource(String text) {
//        String testString = "<a href=\"http://twitterfeed.com\" rel=\"nofollow\">twitterfeed</a>";
        StringBuilder stringBuilder = new StringBuilder();

        if (text.contains(">")) {
            int position = text.indexOf(">");
            int position2 = text.indexOf("</a>");

            ArrayList<Character> list = convertStringToArraylist(text);

            for (int i=0; i<list.size(); i++) {
                if (i > position && i < position2) {
                    stringBuilder.append(list.get(i));
                }
            }
        }
        return stringBuilder.toString();
    }

    public ArrayList<Character> convertStringToArraylist(String str) {
        ArrayList<Character> charList = new ArrayList<Character>();
        for(int i = 0; i<str.length();i++){
            charList.add(str.charAt(i));
        }
        return charList;
    }

    public static void main(String[] args) {
        Utilities utilities = new Utilities();
//        utilities.extractTextFromSource();
    }
}
