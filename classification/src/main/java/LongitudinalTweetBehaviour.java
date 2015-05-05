import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
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

        for (int i = 0; i < dateTimes.size() -1; i++) {
            try {
                Interval interval = new Interval(dateTimes.get(i), dateTimes.get(i + 1));
                intervals.add(interval);
            }
            catch (Exception e) {
//                System.out.println(i);
//                System.out.println(dateTimes.get(i));
//                System.out.println(dateTimes.get(i + 1));
            }
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
    public String getStdDeviationOfDurations(Long userId) {
        double[] durations = getIntervals(userId);
//        System.out.println(durations.length);
        StandardDeviation standardDeviation = new StandardDeviation();
        double result = standardDeviation.evaluate(durations);
        System.out.println(result);

        DecimalFormat decimalFormat = new DecimalFormat();
//        decimalFormat.setMaximumFractionDigits(340);
        String res = decimalFormat.format(result);

        return res;
    }

    //get time of tweet not including seconds
    public ArrayList<String> getTimesFromTweets(Long userId) {
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
            if (modes.containsKey(time.toString())) {
                modes.replace(time.toString(), modes.get(time.toString()) + 1);
            }
            else {
                modes.put(time.toString(), 1);
            }
        }

        ArrayList<String> sortedModes = mapUtils.sortedMap(modes);
        ArrayList<String> trimode = mapUtils.getTopList(sortedModes, 3);

        return trimode;
    }

    public LocalTime averageTime(Long userId) {
        ArrayList<DateTime> dates = getAsJodaDatetime(userId);
        ArrayList<LocalTime> times = Lists.newArrayList();

        for (DateTime dateTime : dates) {
            LocalTime localTime = new LocalTime(dateTime);
            localTime = localTime.minusMillis(localTime.getMillisOfSecond());
            localTime = localTime.minusSeconds(localTime.getSecondOfMinute());
            times.add(localTime);
        }

        long sum = 0;

        for (int i = 0; i < times.size() - 1; i++) {
            LocalTime currentTime = times.get(i);
            LocalTime nextTime = times.get(i + 1);

            DateTime currentDateTime = currentTime.toDateTimeToday(DateTimeZone.UTC);
            long currentMillis = currentDateTime.getMillis();

            DateTime nextDateTime = nextTime.toDateTimeToday(DateTimeZone.UTC);
            long nextMillis = nextDateTime.getMillis();

            long currentResult = currentMillis + nextMillis;

            sum += currentResult;
        }

        long result = sum/times.size();
        LocalTime localTime = new LocalTime(result, DateTimeZone.UTC);
//        System.out.println(localTime);

        return localTime;

    }


    //get hour of tweet
    public ArrayList<String> getHoursFromTweets(Long userId) {
        ArrayList<DateTime> dates = getAsJodaDatetime(userId);

        ArrayList<Integer> hours = Lists.newArrayList();

        for (DateTime dateTime : dates) {
            int hour = dateTime.getHourOfDay();
            hours.add(hour);
        }

        HashMap<String, Integer> modes = Maps.newHashMap();

        for (Integer hour : hours) {
            if (modes.containsKey(hour.toString())) {
                modes.replace(hour.toString(), modes.get(hour.toString()) + 1);
            }
            else {
                modes.put(hour.toString(), 1);
            }
        }

        ArrayList<String> sortedModes = mapUtils.sortedMap(modes);
        ArrayList<String> trimode = mapUtils.getTopList(sortedModes, 3);

        return trimode;
    }

    public int getAvgHour(Long userId) {
        ArrayList<DateTime> dates = getAsJodaDatetime(userId);
        ArrayList<LocalTime> times = Lists.newArrayList();

        for (DateTime dateTime : dates) {
            LocalTime localTime = new LocalTime(dateTime);
            localTime = localTime.minusMillis(localTime.getMillisOfSecond());
            localTime = localTime.minusSeconds(localTime.getSecondOfMinute());
            times.add(localTime);
        }

        long sum = 0;

        for (int i = 0; i < times.size() - 1; i++) {
            LocalTime currentTime = times.get(i);
            LocalTime nextTime = times.get(i + 1);

            DateTime currentDateTime = currentTime.toDateTimeToday(DateTimeZone.UTC);
            long currentMillis = currentDateTime.getMillis();

            DateTime nextDateTime = nextTime.toDateTimeToday(DateTimeZone.UTC);
            long nextMillis = nextDateTime.getMillis();

            long currentResult = currentMillis + nextMillis;

            sum += currentResult;
        }

        long result = sum/times.size();
        LocalTime localTime = new LocalTime(result, DateTimeZone.UTC);
        //        System.out.println(localTime);

        return localTime.getHourOfDay();
    }

    public static void main(String[] args) {
        LongitudinalTweetBehaviour longitude = new LongitudinalTweetBehaviour();
        longitude.averageTime(17427004L);

        Long user = new Long(602225568);

        System.out.println(longitude.getStdDeviationOfDurations(user));

//        longitude.getIntervals(17427004L);

//        System.out.println(longitude.getHoursFromTweets(17427004L));

//        System.out.println(longitude.getStdDeviationOfDurations(17427004L));
//        System.out.println(longitude.getStdDeviationOfDurations(16335974L));
    }
}
