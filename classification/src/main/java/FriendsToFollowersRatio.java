import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by Jessica on 21/03/2015.
 */
public class FriendsToFollowersRatio {
    RetrieveData retrieveData = new RetrieveData();

    public Double getFriendshipRatio(Long userId) {
        ArrayList<Long> friendsAndFollowers = retrieveData.getFriendsAndFollowers(userId);
        return getRatio(friendsAndFollowers.get(0), friendsAndFollowers.get(1));
    }

    public double getRatio(double followers, double friends) {
        if (friends == 0) {
            return 0.00;
        }

        double ratio = followers/friends;
        Double roundedRatio = round(ratio, 2);

        return roundedRatio;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }
}
