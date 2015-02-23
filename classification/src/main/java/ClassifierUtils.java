import java.util.Set;

import com.google.common.collect.Sets;

/**
 * Created by Jessica on 17/02/2015.
 */
public class ClassifierUtils {

    public Set<String> getSourceWhitelist() {
        Set<String> whitelist = Sets.newHashSet();

        whitelist.add("Twitter for iPhone");
        whitelist.add("Twitter for iPad");
        whitelist.add("Twitter for Android");
        whitelist.add("Twitter for Android Tablets");

        return whitelist;
    }


}
