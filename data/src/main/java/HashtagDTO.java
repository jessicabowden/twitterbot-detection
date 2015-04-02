import org.json.JSONObject;

/**
 * Created by Jessica on 11/02/2015.
 */
public class HashtagDTO {
    int hashtagId;
    String hashtag;
    long tweetId;

    public HashtagDTO createHashtagFromJson(JSONObject jsonObject) {
        HashtagDTO hashtagDTO = new HashtagDTO();

//        hashtagDTO.setHashtag(jsonObject.getString("has"));
        return hashtagDTO;
    }

    public int getHashtagId() {
        return hashtagId;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }
}
