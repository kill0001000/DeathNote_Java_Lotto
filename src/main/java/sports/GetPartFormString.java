package sports;

import java.util.ArrayList;


public class GetPartFormString {

    public static void main(String[] args) {
        //System.out.println(alignmentString("ghjksdghu"));
        ArrayList<String> ss = getPartStepOne(5, "abcdef");
        for (String s : ss) {
            System.out.println(s);
        }
    }

    private static ArrayList<String> getPartStepOne(int num, String s) {
        return getPart(num, alignmentString(s));
    }

    private static String alignmentString(String s) {
        char[] c = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j < s.length(); j++) {
                if (c[i] > c[j]) {
                    char t = c[i];
                    c[i] = c[j];
                    c[j] = t;
                }
            }
        }
        return new String(c);
    }

    private static ArrayList<String> getPart(int num, String s) {
        ArrayList<String> finalss = new ArrayList<String>();
        if (num == 1) {
            char[] c = s.toCharArray();
            String laststr = "";
            for (int i = 0; i < s.length(); i++) {
                String newstr = new String(c, i, 1);
                if (laststr.equals(newstr))
                    continue;
                laststr = newstr;
                finalss.add(newstr);
            }
            return finalss;
        }
        if (num >= s.length()) {
            finalss.add(s);
            return finalss;
        }
        int len = s.length();
        char lastc = ' ';
        for (int i = 0; i < (len - num + 1); i++) {
            char c = s.charAt(i);
            if (lastc == c)
                continue;
            lastc = c;
            ArrayList<String> al = getPart(num - 1, s.substring(i + 1));
            for (String ss : al) {
                finalss.add(c + ss);
            }
        }
        return finalss;
    }

}