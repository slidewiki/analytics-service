package rs.ac.bg.imp.analytics.recommender.util;

import java.util.Comparator;
import java.util.HashMap;

// Comparator that compares Strings
public class ValueComparator implements Comparator<String>{

    HashMap<String, Integer> map = new HashMap<>();

    public ValueComparator(HashMap<String, Integer> map){
        this.map.putAll(map);
    }

    @Override
    public int compare(String s1, String s2) {
        if(map.get(s1) >= map.get(s2)){
            return -1;
        }else{
            return 1;
        }
    }
}