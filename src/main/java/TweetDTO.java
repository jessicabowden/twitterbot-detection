import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import org.json.JSONObject;

import com.google.common.collect.Lists;

/**
 * Created by Jessica on 18/11/2014.
 */
public class TweetDTO {
    long tweet_id;
    String status;
//    long tweeters_id;
    boolean retweet;
    String source;
    Timestamp createdAt;
    long replyTo;
    boolean truncated;
    int retweetCount;
    int favouriteCount;
    UserDTO userDTO = new UserDTO();
    ArrayList<HashtagDTO> hashtagDTOList = Lists.newArrayList();
    ArrayList<UrlDTO> urlDTOList = Lists.newArrayList();
    ArrayList<MentionDTO> mentionDTOList = Lists.newArrayList();

    Utilities utilities = new Utilities();

    public TweetDTO createTweetFromJson(JSONObject jsonObject) {
        TweetDTO tweetDTO = new TweetDTO();

        try {
            final String twitterFormat = "EEE MMM dd HH:mm:ss Z yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
            simpleDateFormat.setLenient(true);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(simpleDateFormat.parse(jsonObject.getString("created_at")));
            Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

            tweetDTO.setCreatedAt(timestamp);
            tweetDTO.setFavouriteCount(jsonObject.getInt("favorite_count"));

            if (!(jsonObject.get("in_reply_to_user_id").equals(null))) {
                //check this works as expected
                tweetDTO.setReplyTo(jsonObject.getLong("in_reply_to_user_id"));
            }

            tweetDTO.setRetweet(jsonObject.getBoolean("retweeted"));
//            tweetDTO.setTweeters_id(userDTO.getUser_id());

            tweetDTO.setTweet_id(jsonObject.getLong("id"));
            tweetDTO.setTruncated(jsonObject.getBoolean("truncated"));
            tweetDTO.setStatus(jsonObject.getString("text"));

            if (!(jsonObject.get("source").equals(null))) {
                String source = utilities.extractSourceFromHtml(jsonObject.getString("source"));
                tweetDTO.setSource(source);
            }

            tweetDTO.setRetweetCount(jsonObject.getInt("retweet_count"));
        }
        catch (NullPointerException e) {}
        catch (ParseException e) {
            e.printStackTrace();
        }

        return tweetDTO;
    }

    public TweetDTO(long tweet_id, String status, long tweeters_id, boolean retweet, String source, Timestamp createdAt, long replyTo, boolean truncated, int retweetCount, int favouriteCount, UserDTO userDTO, ArrayList<HashtagDTO> hashtagDTO, ArrayList<UrlDTO> urlDTO, ArrayList<MentionDTO> mentionDTO) {
        this.tweet_id = tweet_id;
        this.status = status;
//        this.tweeters_id = tweeters_id;
        this.retweet = retweet;
        this.source = source;
        this.createdAt = createdAt;
        this.replyTo = replyTo;
        this.truncated = truncated;
        this.retweetCount = retweetCount;
        this.favouriteCount = favouriteCount;
        this.userDTO = userDTO;
        this.hashtagDTOList = hashtagDTO;
        this.urlDTOList = urlDTO;
        this.mentionDTOList = mentionDTO;
    }

    public TweetDTO() {
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public long getTweet_id() {
        return tweet_id;
    }

    public void setTweet_id(long tweet_id) {
        this.tweet_id = tweet_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public long getTweeters_id() {
//        return tweeters_id;
//    }
//
//    public void setTweeters_id(long tweeters_id) {
//        this.tweeters_id = tweeters_id;
//    }

    public boolean isRetweet() {
        return retweet;
    }

    public void setRetweet(boolean retweet) {
        this.retweet = retweet;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public long getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(long replyTo) {
        this.replyTo = replyTo;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public int getFavouriteCount() {
        return favouriteCount;
    }

    public void setFavouriteCount(int favouriteCount) {
        this.favouriteCount = favouriteCount;
    }

//    public UserDTO getUserDTO() {
//        return userDTO;
//    }
//
//    public void setUserDTO(UserDTO userDTO1) {
//        userDTO = userDTO1;
//    }

    public void appendToUrls(UrlDTO urlDTO) {
        urlDTOList.add(urlDTO);
    }

    public void appendToHashtags(HashtagDTO hashtagDTO) {
        hashtagDTOList.add(hashtagDTO);
    }

    public void appendToMentions(MentionDTO mentionDTO) {
        mentionDTOList.add(mentionDTO);
    }

    public ArrayList<UrlDTO> getUrls() {
        return urlDTOList;
    }

    public ArrayList<MentionDTO> getMentions() {
        return mentionDTOList;
    }

    public ArrayList<HashtagDTO> getHashtags() {
        return hashtagDTOList;
    }
}
