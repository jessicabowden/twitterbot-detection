import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jessica on 17/02/2015.
 */
public class TwitterbotUtils {

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
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

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
}
