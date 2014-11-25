import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import jdk.nashorn.internal.parser.JSONParser;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

import javax.lang.model.element.Element;
import javax.print.URIException;
import javax.xml.soap.Node;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        ArrayList<String> bots = Lists.newArrayList();

        bots.add("EricWilsonBeatz");
        bots.add("itakethelead");
        bots.add("ellenthoen");
        bots.add("TellurideBlues");
        bots.add("Dinigoarent");
        bots.add("caindiru");
        bots.add("nugrosigit");
        bots.add("_CGB");
        bots.add("kobra_kid_chloe");
        bots.add("Quotablest");

        return bots;
    }

    public ArrayList<String> sampleNonBots() {
        ArrayList<String> nonbots = Lists.newArrayList();

        nonbots.add("TriiSHE");
        nonbots.add("rachelmbray");
        nonbots.add("matthewpack");
        nonbots.add("tobewan");
        nonbots.add("jmck");
        nonbots.add("Mxit");
        nonbots.add("MIA_Kev");
        nonbots.add("Frenzee");
        nonbots.add("iowarfs");
        nonbots.add("lepolt");

        return nonbots;
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


    public static void main(String[] args) throws Exception {
        Utilities utilities = new Utilities();
        utilities.reverseShortenedURL("http://t.co/FICMTydyjk");
    }
}
