import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.google.common.collect.Lists;

/**
 * Created by Jessica on 18/11/2014.
 */
public class Utilities {
    ArrayList<String> urls = Lists.newArrayList();

    public String extractSourceFromHtml(String text) {
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

    public ArrayList<String> getStopWords() {
        return fileToArray("stopwords.txt");
    }

    public static void main(String[] args) {
        File file = new File("tweets.txt");
        BufferedReader reader = null;
        String curr = null;

        try {
            reader = new BufferedReader(new FileReader(file));
        }
        catch (FileNotFoundException e) {
            System.out.println("Can't find file ");
        }

        try {
            while ((curr = reader.readLine())!= null) {
                if (curr.equals("\n")) {
                    System.out.println("found blank line");
                }
                else if (curr.equals("bot") || curr.equals("human")) {
                    System.out.println(curr);
                }
            }
        }
        catch (IOException e) {
            System.out.println("Can't read from file ");
        }
    }

}
