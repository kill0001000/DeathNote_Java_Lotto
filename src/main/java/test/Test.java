package test;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        String[] nowSports1 = {"07", "11", "12", "16", "33", "05", "07"};

        String[] nowSports2 = {"07", "11", "12", "16", "33", "05", "08"};

        System.out.println(Arrays.equals(nowSports1, nowSports2));

        String newStr = "Set-Cookie: XoiLogin=E3C30AF3-4AC7-4ECA-9018-903DCA604673; expires=Tue, 12-May-2020 03:06:52 GMT; path=/\n";
        String findStr = "E3C30AF3-4AC7-4ECA-9018-903DCA604673";
        newStr.contains(findStr);
    }

}
