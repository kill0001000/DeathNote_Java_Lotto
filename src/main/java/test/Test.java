package test;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        String[] nowSports1 = {"07", "11", "12", "16", "33", "05", "07"};

        String[] nowSports2 = {"07", "11", "12", "16", "33", "05", "08"};

        System.out.println(Arrays.equals(nowSports1, nowSports2));
    }

}
