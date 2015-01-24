import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import jdk.nashorn.internal.parser.JSONParser;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

import javax.lang.model.element.Element;
import javax.print.URIException;
import javax.xml.soap.Node;
import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Jessica on 18/11/2014.
 */
public class Utilities {
    public String extractTextFromSource(String text) {
        StringBuilder stringBuilder = new StringBuilder();

            ArrayList<Character> list = convertStringToArraylist(text);

            for (int i=0; i<list.size(); i++) {
                if (text.contains(">")) {
                    int position = text.indexOf(">");
                    int position2 = text.indexOf("</a>");

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

    public Set<String> getSourceWhitelist() {
        Set<String> whitelist = Sets.newHashSet();

        whitelist.add("Twitter for iPhone");
        whitelist.add("Twitter for iPad");
        whitelist.add("Twitter for Android");
        whitelist.add("Twitter for Android Tablets");

        return whitelist;
    }

//    public String expandShortURL(String address) {
//        URL url = null;
//        BufferedReader bufferedReader = null;
//
//        try {
//            url = new URL("http://api.longurl.org/v2/expand?format=json&url=" + address);
//        }
//        catch (MalformedURLException e) {
//            System.out.println("Bad url");
//        }
//
//        try {
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setRequestMethod("GET");
//            httpURLConnection.connect();
//
//            bufferedReader = new BufferedReader(
//                    new InputStreamReader(httpURLConnection.getInputStream())
//            );
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String line = null;
//        String full = null;
//        org.json.JSONObject jsonObject = null;
//
//        try {
//            while ((line = bufferedReader.readLine()) != null) {
//                full += line;
//                jsonObject = new org.json.JSONObject(line);
//            }
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return jsonObject.get("long-url").toString();
//    }

    public String getBaseURL(String page) {
        URI uri = null;
        try {
            uri = new URI(page);
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return uri.getHost();
    }

    public ArrayList<String> sampleBots() {
        return fileToArray("botlist.txt");
    }

    public ArrayList<String> sampleNonBots() {
        return fileToArray("nonbotlist.txt");
    }

    public String reverseShortenedURL(String shorturl) throws Exception {
        URL url = new URL(shorturl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setInstanceFollowRedirects(false);

//        String url2 = httpURLConnection.getHeaderField("location");
        String url2 = httpURLConnection.getURL().toString();
        System.out.println(url2);

        if (!url2.contains("www")) {
            return reverseShortenedURL(url2);
        }
        return url2;
    }

    public void stringToFile(ArrayList<String> strings, String filename) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(filename);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (String s : strings) {
            out.print(s);
            System.out.println("written a string");
        }

        out.close();
    }

    public ArrayList<String> fileToArray(String name) {
        File file = new File(name);
        BufferedReader reader = null;
        String curr = null;
        ArrayList<String> fileContents = Lists.newArrayList();

        try {
            reader = new BufferedReader(new FileReader(file));
        }
        catch (FileNotFoundException e) {
            System.out.println("Can't find file " + name);
        }

        try {
            while ((curr = reader.readLine())!= null) {
                fileContents.add(curr);
            }
        }
        catch (IOException e) {
            System.out.println("Can't read from file " + name );
        }

        return fileContents;
    }


    public static void main(String[] args) throws Exception {
        Utilities utilities = new Utilities();
        utilities.reverseShortenedURL("http://t.co/FICMTydyjk");
    }
}
