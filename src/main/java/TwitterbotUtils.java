import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Jessica on 17/02/2015.
 */
public class TwitterbotUtils {

    public String getBaseURLOld(String page) {
        URI uri = null;

        try {
            uri = new URI(page);
        }
        catch (URISyntaxException e) {
        }

        return uri.getHost();
    }

    public static String getBaseURL(String page) {
        URL url = null;

        try {
            url = new URL(page);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url.getHost();
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

    public static void main(String[] args) throws IOException, URISyntaxException {
        String url = "http://www.thetimes.co.uk/tto/opinion/columnists/article4319349.ece?CMP=Spklr-128959631-Editorial-TWITTER-thetimes-20150110-Comment and Opinion&linkId=11660646";
        URL url1 = new URL(url);
//        System.out.println(url1.getHost());

        TwitterbotUtils twitterbotUtils = new TwitterbotUtils();
        System.out.println(twitterbotUtils.getBaseURL(url));
    }
}
