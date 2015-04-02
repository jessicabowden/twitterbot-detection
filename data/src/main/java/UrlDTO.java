import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jessica on 11/02/2015.
 */

public class UrlDTO {
    int urlId;
    String url;
    String expandedUrl;
    String domain;
    long tweetId;

    Utilities utilities = new Utilities();
    TwitterbotUtils twitterbotUtils = new TwitterbotUtils();

    public UrlDTO(String newUrl) {
        if (!(newUrl.contains("http://") || newUrl.contains("https://"))) {
            url = "http://" + newUrl;
        }
        else {
            url = newUrl;
        }
        try {
            URL shortUrl = new URL(url);
            url = shortUrl.toString();

            System.out.println(url);

            expandedUrl = twitterbotUtils.expand(shortUrl).toString();

            System.out.println(twitterbotUtils.expand(shortUrl));

            System.out.println(expandedUrl);

            domain = TwitterbotUtils.getBaseURL(expandedUrl);
            System.out.println(domain);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public int getUrlId() {
        return urlId;
    }

    public void setUrlId(int urlId) {
        this.urlId = urlId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExpandedUrl() {
        return expandedUrl;
    }

    public void setExpandedUrl(String expandedUrl) {
        this.expandedUrl = expandedUrl;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }

    public static void main(String[] args) {
        String s = new String("http://www.capwiz.com/compassionindex/issues/alert/?alertid=62658651#.UZYdRCSzdnE.twitter");
        String working = new String("http://t.co/5UCsLP7hyS");
        UrlDTO urlDTO = new UrlDTO(s);
    }
}
