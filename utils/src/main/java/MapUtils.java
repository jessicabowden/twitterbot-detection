import java.util.*;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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

    // should ideally return it sorted on the integers
    public HashMap<String, Integer> getIndividualCounts(ArrayList<String> list) {
        HashMap<String, Integer> map = Maps.newHashMap();

        for (String x : list) {
            if (map.get(x) != null) {
                int newCount = map.get(x) + 1;
                map.replace(x, newCount);
            }
            else  {
                map.put(x, 1);
            }
        }

        return map;
    }

    public HashMap sortByValue(HashMap<String, Integer> map) {
        List list = new LinkedList(map.entrySet());

        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        HashMap sortedMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public HashMap sortByValueLargestFirst(HashMap<String, Integer> map) {
        List list = new LinkedList(map.entrySet());

        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue())
                        .compareTo(((Map.Entry) (o1)).getValue());
            }
        });

        HashMap sortedMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public ArrayList<String> getFirstNElements(ArrayList<String> list, int n) {
        ArrayList<String> newList = Lists.newArrayList();

        if (list.size() < n) {
            return list;
        }

        for (int i = 0; i < n; i++) {
            newList.add(list.get(i));
        }

        return newList;

    }

    public static void main(String[] args) {
        MapUtils mapTest = new MapUtils();

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("z", 4);
        map.put("l", 6);
        map.put("b", 2);
        map.put("f", 5);
        map.put("j", 0);

        ArrayList<String> stuff = Lists.newArrayList();
        stuff.add("a");
        stuff.add("a");
        stuff.add("b");
        stuff.add("c");
        stuff.add("b");
        stuff.add("a");

        stuff.add("f");
        stuff.add("f");
        stuff.add("f");
        stuff.add("f");
        stuff.add("f");

        HashMap<String, Integer> tada = mapTest.getIndividualCounts(stuff);

        System.out.println(mapTest.sortByValueLargestFirst(tada));

    }

}
