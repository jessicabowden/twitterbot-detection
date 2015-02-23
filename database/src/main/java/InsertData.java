import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
* Created by Jessica on 18/11/2014.
*/
public class InsertData {
    PreparedStatement userUpdate;
    PreparedStatement tweetUpdate;
    CreateConnection createConnection = new CreateConnection();
    RetrieveData retrieveData = new RetrieveData();

    protected void insertIntoUserTable(UserDTO userDTO) {
        if (!retrieveData.checkForUser(userDTO.getUser_id())) {
            String sql = "INSERT INTO user (user_id, screen_name, is_verified, is_bot, friend_count, follower_count, " +
                    "name, description, url_in_description, lang, time_zone, location, default_img, status_count, favourite_count) " +
                    "VALUES (" +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                userUpdate = createConnection.prepStatement(sql);

                userUpdate.setLong(1, userDTO.getUser_id());
                userUpdate.setString(2, userDTO.getUsername());
                userUpdate.setBoolean(3, userDTO.isVerified());
                userUpdate.setString(4, userDTO.getBotStatus().toString());
                userUpdate.setLong(5, userDTO.getFriendCount());
                userUpdate.setLong(6, userDTO.getFollowerCount());
                userUpdate.setString(7, userDTO.getName());
                userUpdate.setString(8, userDTO.getDescription());
                userUpdate.setString(9, userDTO.getUrlInDescription());
                userUpdate.setString(10, userDTO.getLanguage());
                userUpdate.setString(11, userDTO.getTimeZone());
                userUpdate.setString(12, userDTO.getLocation());
                userUpdate.setBoolean(13, userDTO.isDefaultImage());
                userUpdate.setLong(14, userDTO.getStatusCount());
                userUpdate.setLong(15, userDTO.getFavouriteCount());

                userUpdate.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                createConnection.close();
            }
        }
    }

    public void updateUsers(UserDTO newUser) {
        if (!(retrieveData.checkForUser(newUser.getUser_id()))) {
            //they don't exist, do nothing
        }

        UserDTO currentUser = retrieveData.getUser(newUser.getUser_id());

        if (!(currentUser.equals(newUser))) {
            updateUser(newUser);
        }
    }

    protected void updateUser(UserDTO userDTO) {
        if (!retrieveData.checkForUser(userDTO.getUser_id())) {
            String sql = "UPDATE user SET user_id = ?, screen_name = ?, is_verified = ?, is_bot = ?, friend_count = ?, follower_count = ?, " +
                    "name = ?, description = ?, url_in_description = ?, lang = ?, time_zone = ?, location = ?, default_img = ?, status_count = ?, favourite_count = ?)";
            try {
                userUpdate = createConnection.prepStatement(sql);

                userUpdate.setLong(1, userDTO.getUser_id());
                userUpdate.setString(2, userDTO.getUsername());
                userUpdate.setBoolean(3, userDTO.isVerified());
                userUpdate.setString(4, userDTO.getBotStatus().name());
                userUpdate.setLong(5, userDTO.getFriendCount());
                userUpdate.setLong(6, userDTO.getFollowerCount());
                userUpdate.setString(7, userDTO.getName());
                userUpdate.setString(8, userDTO.getDescription());
                userUpdate.setString(9, userDTO.getUrlInDescription());
                userUpdate.setString(10, userDTO.getLanguage());
                userUpdate.setString(11, userDTO.getTimeZone());
                userUpdate.setString(12, userDTO.getLocation());
                userUpdate.setBoolean(13, userDTO.isDefaultImage());
                userUpdate.setLong(14, userDTO.getStatusCount());
                userUpdate.setLong(15, userDTO.getFavouriteCount());

                userUpdate.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                createConnection.close();
            }
        }
    }


    protected void insertIntoTweetTable(TweetDTO tweetDTO) {
        String status = tweetDTO.getStatus();
//        System.out.println(tweetDTO.getUserDTO().getUsername());

        if (!retrieveData.checkForUser(tweetDTO.getUserDTO().getUser_id())) {
            UserDTO userDTO = tweetDTO.getUserDTO();
            insertIntoUserTable(userDTO);
        }

        String quotemarks = "\"";
        if (status.contains(quotemarks)) {
            status = status.replace(quotemarks, "'");
        }
        if (status.contains("\n")) {
            status = status.replace("\n", " ");
        }

        if (!retrieveData.checkForTweet(tweetDTO.getTweet_id())) {
            String sql = "INSERT INTO tweet (tweet_id, status, tweeters_id, is_retweet, source, created_at, " +
                    "in_reply_to, truncated, retweet_count, favourite_count) " +
                    "VALUES (" +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                tweetUpdate = createConnection.prepStatement(sql);

                tweetUpdate.setLong(1, tweetDTO.getTweet_id());
                tweetUpdate.setString(2, tweetDTO.getStatus());
                tweetUpdate.setLong(3, tweetDTO.getUserDTO().getUser_id());
                tweetUpdate.setBoolean(4, tweetDTO.isRetweet());
                tweetUpdate.setString(5, tweetDTO.getSource());
                tweetUpdate.setTimestamp(6, tweetDTO.getCreatedAt());
                tweetUpdate.setLong(7, tweetDTO.getReplyTo());
                tweetUpdate.setBoolean(8, tweetDTO.isTruncated());
                tweetUpdate.setInt(9, tweetDTO.getRetweetCount());
                tweetUpdate.setInt(10, tweetDTO.getFavouriteCount());

                tweetUpdate.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                createConnection.close();
            }
        }

        if (tweetDTO.getHashtags() != null) {
            for (HashtagDTO hashtagDTO : tweetDTO.getHashtags()) {
                insertIntoHashtag(hashtagDTO);
            }
        }
        if (tweetDTO.getUrls() != null) {
            for (UrlDTO urlDTO : tweetDTO.getUrls()) {
                insertIntoURL(urlDTO);
            }
        }
        if (tweetDTO.getMentions() != null) {
            for (MentionDTO mentionDTO : tweetDTO.getMentions()) {
                insertIntoMention(mentionDTO);
            }
        }

    }

    protected void insertIntoHashtag(HashtagDTO hashtagDTO) {
        PreparedStatement hashtagUpdate;

        String sql = "INSERT INTO hashtag (hashtag, tweet_id) " +
                "VALUES (" +
                "?, ?)";
        if (retrieveData.checkForTweet(hashtagDTO.getTweetId())) {
            try {
                hashtagUpdate = createConnection.prepStatement(sql);

                hashtagUpdate.setString(1, hashtagDTO.getHashtag());
                hashtagUpdate.setLong(2, hashtagDTO.getTweetId());

                hashtagUpdate.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                createConnection.close();
            }
        }
    }

    protected void insertIntoURL(UrlDTO urlDTO) {
        PreparedStatement urlUpdate;

        String sql = "INSERT INTO url (url, expanded_url, domain, tweet_id) " +
                "VALUES (" +
                "?, ?, ?, ?)";
        if (retrieveData.checkForTweet(urlDTO.getTweetId())) {
            try {
                urlUpdate = createConnection.prepStatement(sql);

                urlUpdate.setString(1, urlDTO.getUrl());
                urlUpdate.setString(2, urlDTO.getExpandedUrl());
                urlUpdate.setString(3, urlDTO.getDomain());
                urlUpdate.setLong(4, urlDTO.getTweetId());

                urlUpdate.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                createConnection.close();
            }
        }
    }

    protected void insertIntoMention(MentionDTO mentionDTO) {
        PreparedStatement mentionUpdate;

        String sql = "INSERT INTO mention (mention, tweet_id) " +
                "VALUES (" +
                "?, ?)";
        if (retrieveData.checkForTweet(mentionDTO.getTweetId())) {
            try {
                mentionUpdate = createConnection.prepStatement(sql);

                mentionUpdate.setString(1, mentionDTO.getMention());
                mentionUpdate.setLong(2, mentionDTO.getTweetId());

                mentionUpdate.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                createConnection.close();
            }
        }
    }
}
