import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.common.collect.Maps;

/**
* Created by Jessica on 17/02/2015.
*/
public class InsertTestData {
    InsertData insertData = new InsertData();
    Utilities utilities = new Utilities();
    GetTweetObjects getTweetObjects = new GetTweetObjects();
    JsonUtils jsonUtils = new JsonUtils();

    public Map<String, ArrayList<String>> getTestUsers() {
        Map<String, ArrayList<String>> users = Maps.newHashMap();

        users.put("Bots", utilities.fileToArray("botsTest.txt"));
//        users.put("Non-Bots", utilities.fileToArray("humansTest.txt"));

        return users;
    }

    public void getTweets() {
        Map<String, ArrayList<String>> users = getTestUsers();

        ArrayList<String> bots = users.get("Bots");
//        ArrayList<String> nonbots = users.get("Non-Bots");

        for (String bot : bots) {
                System.out.println("-------NEW BOT---------");
                ArrayList<JSONObject> tweets = getTweetObjects.getOneThousandTweets(bot);

                for (JSONObject tweet : tweets) {

                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    System.out.println(dateFormat.format(date));

                    Map<String, JSONObject> tweetInfo = jsonUtils.splitJson(tweet);
                    System.out.println("------------------------");
                    System.out.println(tweetInfo.toString());

                    JSONObject userJson = tweetInfo.get("user");
                    JSONObject entitiesJson = tweetInfo.get("entities");
                    JSONObject tweetJson = tweetInfo.get("tweet");

                    UserDTO currentUser = new UserDTO();
                    currentUser = currentUser.createUserFromJson(userJson, BotStatus.TRUE);

                    TweetDTO tweetDTO = new TweetDTO();
                    tweetDTO = tweetDTO.createTweetFromJson(tweetJson);
                    tweetDTO.setUserDTO(currentUser);

                    JSONArray urls = entitiesJson.getJSONArray("urls");
                    if (urls.length() != 0) {
                        for (int i = 0; i < urls.length(); i++) {
                            JSONObject currentUrl = urls.getJSONObject(i);
                            String original_url = currentUrl.getString("expanded_url");
                            UrlDTO urlDTO = new UrlDTO(original_url);
                            urlDTO.setTweetId(tweetDTO.getTweet_id());
                            tweetDTO.appendToUrls(urlDTO);
                        }
                    }

                    JSONArray hashtags = entitiesJson.getJSONArray("hashtags");
                    if (hashtags.length() != 0) {
                        for (int i = 0; i < hashtags.length(); i++) {
                            JSONObject currentHashtag = hashtags.getJSONObject(i);
                            HashtagDTO hashtagDTO = new HashtagDTO();
                            hashtagDTO.setHashtag(currentHashtag.getString("text"));
                            hashtagDTO.setTweetId(tweetDTO.getTweet_id());
                            tweetDTO.appendToHashtags(hashtagDTO);
                        }
                    }

                    JSONArray userMentions = entitiesJson.getJSONArray("user_mentions");
                    if (userMentions.length() != 0) {
                        for (int i = 0; i < userMentions.length(); i++) {
                            JSONObject currentMention = userMentions.getJSONObject(i);
                            MentionDTO mentionDTO = new MentionDTO();
                            mentionDTO.setMention(currentMention.getString("screen_name"));
                            mentionDTO.setTweetId(tweetDTO.getTweet_id());
                            tweetDTO.appendToMentions(mentionDTO);
                        }
                    }

                    System.out.println("---------INSERTING TWEET INTO DB---------");
                    insertData.insertIntoTweetTable(tweetDTO);

                }

                try {
                    Thread.sleep(50000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        }

//        for (String nonbot : nonbots) {
//            ArrayList<JSONObject> tweets = getTweetObjects.getOneThousandTweets(nonbot);
//
//            for (JSONObject tweet : tweets) {
//                System.out.println("----------------");
//                System.out.println(tweet.toString());
//                Map<String, JSONObject> tweetInfo = jsonUtils.splitJson(tweet);
//
//                JSONObject userJson = tweetInfo.get("user");
//                JSONObject entitiesJson = tweetInfo.get("entities");
//                JSONObject tweetJson = tweetInfo.get("tweet");
//
//                UserDTO currentUser = new UserDTO();
//                currentUser = currentUser.createUserFromJson(userJson, BotStatus.FALSE);
//                System.out.println(currentUser.getTimeZone());
//
//                TweetDTO tweetDTO = new TweetDTO();
//                tweetDTO = tweetDTO.createTweetFromJson(tweetJson);
//                tweetDTO.setUserDTO(currentUser);
//
//                JSONArray urls = entitiesJson.getJSONArray("urls");
//                if (urls.length() != 0) {
//                    for (int i = 0; i < urls.length(); i++) {
//                        JSONObject currentUrl = urls.getJSONObject(i);
//                        String original_url = currentUrl.getString("expanded_url");
//                        System.out.println("-------------");
//                        System.out.println(original_url);
//                        UrlDTO urlDTO = new UrlDTO(original_url);
//                        System.out.println(urlDTO.getExpandedUrl());
//                        System.out.println(urlDTO.getDomain());
//                        urlDTO.setTweetId(tweetDTO.getTweet_id());
//                        tweetDTO.appendToUrls(urlDTO);
//                    }
//                }
//
//                JSONArray hashtags = entitiesJson.getJSONArray("hashtags");
//                if (hashtags.length() != 0) {
//                    for (int i = 0; i < hashtags.length(); i++) {
//                        JSONObject currentHashtag = hashtags.getJSONObject(i);
//                        HashtagDTO hashtagDTO = new HashtagDTO();
//                        hashtagDTO.setHashtag(currentHashtag.getString("text"));
//                        hashtagDTO.setTweetId(tweetDTO.getTweet_id());
//                        tweetDTO.appendToHashtags(hashtagDTO);
//                    }
//                }
//
//                JSONArray userMentions = entitiesJson.getJSONArray("user_mentions");
//                if (userMentions.length() != 0) {
//                    for (int i = 0; i < userMentions.length(); i++) {
//                        JSONObject currentMention = userMentions.getJSONObject(i);
//                        MentionDTO mentionDTO = new MentionDTO();
//                        mentionDTO.setMention(currentMention.getString("screen_name"));
//                        mentionDTO.setTweetId(tweetDTO.getTweet_id());
//                        tweetDTO.appendToMentions(mentionDTO);
//                    }
//                }
//
//                System.out.println("---------INSERTING TWEET INTO DB---------");
//                insertData.insertIntoTweetTable(tweetDTO);
//
//            }
//
//            try {
//                Thread.sleep(50000);
//            }
//            catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

    }

    public static void main(String[] args) {
        InsertTestData insertTestData = new InsertTestData();
        insertTestData.getTweets();


    }

}
