import com.google.common.collect.Lists;
import twitter4j.*;
import java.util.ArrayList;

/**
 * Created by Jessica on 18/11/2014.
 */
public class StatusStream {
    private final Object lock = new Object();
    ArrayList<Status> statuses = Lists.newArrayList();

    public ArrayList<Status> fetchStatuses() {
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();

        StatusListener statusListener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                if (statuses.size() >= 10) {
                    synchronized (lock) {
                        lock.notify();
                    }
                }
                else {
                    if (status.getIsoLanguageCode().equals("en")) {
                        statuses.add(status);
                    }
                }
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

            }

            @Override
            public void onTrackLimitationNotice(int i) {

            }

            @Override
            public void onScrubGeo(long l, long l2) {

            }

            @Override
            public void onStallWarning(StallWarning stallWarning) {

            }

            @Override
            public void onException(Exception e) {
            }
        };
        twitterStream.addListener(statusListener);
        twitterStream.sample();

        try {
            synchronized (lock) {
                lock.wait();
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        twitterStream.cleanUp();
        twitterStream.shutdown();
        System.out.println("------------STREAM SHUT DOWN--------------");
        return statuses;
    }

    public ArrayList<Status> getStatuses() {
        fetchStatuses();
        return statuses;
    }

}
