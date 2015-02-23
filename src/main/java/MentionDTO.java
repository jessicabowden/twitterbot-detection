/**
 * Created by Jessica on 11/02/2015.
 */
public class MentionDTO {
    int mentionId;
    String mention;
    long tweetId;

    public int getMentionId() {
        return mentionId;
    }

    public void setMentionId(int mentionId) {
        this.mentionId = mentionId;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }
}
