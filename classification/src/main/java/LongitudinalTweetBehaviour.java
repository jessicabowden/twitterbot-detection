import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalTime;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Created by Jessica on 16/03/2015.
 */
public class LongitudinalTweetBehaviour {

    CreateConnection connection = new CreateConnection();
    MapUtils mapUtils = new MapUtils();

    protected ArrayList<Timestamp> getTweetDates(Long userId) {
        String sql = "SELECT created_at FROM tweet WHERE tweeters_id = ?";

        ArrayList<Timestamp> dates = Lists.newArrayList();

        ResultSet resultSet;

        try {
            PreparedStatement preparedStatement = connection.prepStatement(sql);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                dates.add(resultSet.getTimestamp("created_at"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            connection.close();
        }

        return dates;
    }

    private ArrayList<DateTime> getAsJodaDatetime(Long userId) {
        ArrayList<Timestamp> tweetsCreatedAt = getTweetDates(userId);
        ArrayList<DateTime> dateTimes = new ArrayList<DateTime>();

        for (Timestamp created_at : tweetsCreatedAt) {
            DateTime dateTime = new DateTime(created_at);
            dateTimes.add(dateTime);
        }

        return dateTimes;

    }

    private double[] getIntervals(Long userId) {
        ArrayList<Timestamp> dates = getTweetDates(userId);

        ArrayList<DateTime> dateTimes = getAsJodaDatetime(userId);

        ArrayList<Interval> intervals = new ArrayList<Interval>();

        for (int i = 0; i < dateTimes.size(); i++) {
            Interval interval = new Interval(i, i+1);   // if there any issues it will be here
            intervals.add(interval);
        }

        double[] durations = new double[intervals.size()];

        for (int i = 0; i < intervals.size(); i++) {
            Duration duration = new Duration(intervals.get(i));
            double millis = duration.getMillis();
            durations[i] = millis;
        }

        return durations;

    }

    // gets the standard deviation based on the times between a user's tweets

    //it shows us how sporadic their tweeting is (or not!)
        //a std deviation of 0 is not sporadic at all
    public double getStdDeviationOfDurations(Long userId) {
        double[] durations = getIntervals(userId);
        StandardDeviation standardDeviation = new StandardDeviation();
        double result = standardDeviation.evaluate(durations);
        return result;
    }

    //get time of tweet not including seconds
    private ArrayList<String> getTimesFromTweets(Long userId) {
        ArrayList<DateTime> dates = getAsJodaDatetime(userId);
        ArrayList<LocalTime> times = Lists.newArrayList();

        for (DateTime dateTime : dates) {
            LocalTime localTime = new LocalTime(dateTime);
            localTime = localTime.minusMillis(localTime.getMillisOfSecond());
            localTime = localTime.minusSeconds(localTime.getSecondOfMinute());
            times.add(localTime);
        }

//        HashMap<LocalTime, Integer> modes = Maps.newHashMap();
        HashMap<String, Integer> modes = Maps.newHashMap();

        for (LocalTime time : times) {
            if (modes.get(time.toString()) != null) {
                modes.put(time.toString(), 1);
            }
            else {
                modes.replace(time.toString(), modes.get(time) + 1);
            }
        }

        ArrayList<String> sortedModes = mapUtils.sortedMap(modes);
        ArrayList<String> trimode = mapUtils.getTopList(sortedModes, 3);

        return trimode;
    }


    //get hour of tweet
    private ArrayList<String> getHoursFromTweets(Long userId) {
        ArrayList<DateTime> dates = getAsJodaDatetime(userId);

        ArrayList<Integer> hours = Lists.newArrayList();

        for (DateTime dateTime : dates) {
            int hour = dateTime.getHourOfDay();
            hours.add(hour);
        }

        HashMap<String, Integer> modes = Maps.newHashMap();

        for (Integer hour : hours) {
            if (modes.get(hour.toString()) != null) {
                modes.put(hour.toString(), 1);
            }
            else {
                modes.replace(hour.toString(), modes.get(hour) + 1);
            }
        }

        ArrayList<String> sortedModes = mapUtils.sortedMap(modes);
        ArrayList<String> trimode = mapUtils.getTopList(sortedModes, 3);

        return trimode;

        //store values and count in map to get trimode
    }

    public static void main(String[] args) {
        Date date = new Date();

        DateTime dateTime = new DateTime(date);

        LocalTime localTime = new LocalTime(dateTime);

        System.out.println(localTime);

        System.out.println(localTime.getMillisOfSecond());
        System.out.println(localTime.getSecondOfMinute());

        localTime = localTime.minusMillis(localTime.getMillisOfSecond());
        localTime = localTime.minusSeconds(localTime.getSecondOfMinute());

        System.out.println(localTime);
    }
}
