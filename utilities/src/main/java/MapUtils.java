import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Lists;

/**
 * Created by Jessica on 29/01/2015.
 */
public class MapUtils {

    public ArrayList sortedMap(HashMap<String,Integer> hashMap) {
        ArrayList<String> compiledList = Lists.newArrayList();
        Object[] mapAsArrays = hashMap.entrySet().toArray();
        Arrays.sort(mapAsArrays, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue().compareTo(
                        ((Map.Entry<String, Integer>) o1).getValue());
            }
        });
        for (Object entrySet : mapAsArrays) {
            String singlekey = ((Map.Entry<String, Integer>) entrySet).getKey();
            compiledList.add(singlekey);
        }

        return compiledList;
    }

    public ArrayList<String> getTopList(ArrayList<String> list, int listSize) {
        ArrayList<String> topThree = Lists.newArrayList();

        if (list.size() >= listSize) {
            int i = 0;
            while (i < listSize) {
                topThree.add(list.get(i));
                i++;
            }
            return topThree;
        }

        return topThree;
    }

    public static void main(String[] args) {
        MapUtils mapTest = new MapUtils();

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("z", 4);
        map.put("l", 6);
        map.put("b", 2);
        map.put("f", 5);
        map.put("j", 0);

        ArrayList<String> list = mapTest.sortedMap(map);
//        System.out.println(mapTest.getTopList(list));

    }

}
