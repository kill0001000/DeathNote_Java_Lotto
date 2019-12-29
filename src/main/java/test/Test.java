package test;

import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("one", "一");
        map1.put("two", "二");
        map1.put("three", "三");

        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("ten", "十");
        map2.put("nine", "九");
        map2.put("eight", "八");
        map2.put("three", "三八");

        map1.putAll(map2);

        // 合并后打印出所有内容
        for (Map.Entry<String, String> entry : map1.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

}
