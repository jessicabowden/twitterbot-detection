import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

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
    ArrayList<String> urls = Lists.newArrayList();

    public ArrayList<String> sampleBots() {
        return fileToArray("botlist.txt");
    }

    public ArrayList<String> sampleNonBots() {
        return fileToArray("nonbotlist.txt");
    }

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

    public URL expand(URL shorturl) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) shorturl.openConnection();
            connection.setInstanceFollowRedirects(false);
            connection.connect();
            String expanded = connection.getHeaderField("Location");
            if (expanded != null) {
                if (connection.getResponseCode() == 301) {
                    URL expandedURL = new URL(expanded);
                    return expand(expandedURL);
                }
                else {
                    URL expandedURL = new URL(expanded);
                    return expandedURL;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return shorturl;
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

    public void writeToExistingFile(String text, String file) {
        try {
            String filename = file;
            FileWriter fw = new FileWriter(filename, true);
            fw.write(text + "\n");
            fw.close();
        }
        catch(IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }


    public static void main(String[] args) throws Exception {
        Utilities utilities = new Utilities();
    }
}
