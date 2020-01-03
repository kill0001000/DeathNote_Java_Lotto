package sports;

import common.Common;
import groovy.util.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sports_Test {

    static Common common = new Common();

    // 每一注 相同数分析
    static int[] redSame = new int[6];
    static int[] blueSame = new int[3];
    static int[] allSame = new int[8];

    static int cal = 0;

    public static void main(String[] args) {

        System.out.println("\n\nSports:" + common.getCurrentDate());
        get5_sports();

    }

    /**
     * 生成5注
     */
    private static void get5_sports() {
        Node xml = common.getXmlData("lotto/data_sports.xml");

        String[] oneSports = getOneSports();
        String[] nowSports = {"07", "11", "12", "16", "33", "05", "07"};

        do {
            cal++;
            oneSports = getOneSports();
        } while (compare_sports(xml, oneSports, nowSports));

    }

    /**
     * 生成随机一注_sports
     *
     * @return
     */
    private static String[] getOneSports() {
        // red 5/35
        String[] reds = common.randomSequence(35, 5);
        // blue 2/12
        String[] bule = common.randomSequence(12, 2);

        String[] Sports = new String[7];

        for (int i = 0; i < Sports.length; i++) {

            if (i < 5) {
                Sports[i] = reds[i];
            } else {
                Sports[i] = bule[i - 5];
            }
        }

        return Sports;
    }

    /**
     * 比较历史数据_sports
     *
     * @param xml       历史数据 XML格式
     * @param oneSports 随机一注
     * @return
     */
    private static boolean compare_sports(Node xml, String[] oneSports, String[] nowSports) {

        redSame = new int[6];
        blueSame = new int[3];
        allSame = new int[8];

        List<String> listRed = new ArrayList<String>();
        List<String> listBlue = new ArrayList<String>();

        for (int i = 0; i < oneSports.length - 2; i++) {
            listRed.add(oneSports[i]);
        }

        listBlue.add(oneSports[5]);
        listBlue.add(oneSports[6]);

        // 遍历
        int size = xml.children().size();
        for (int i = 0; i < size; i++) {

            Node oneLotto = (Node) xml.children().get(i);
            int redFlag = 0, blueFlag = 0;

            String red1 = (String) ((Node) oneLotto.children().get(0)).attribute("red");
            String red2 = (String) ((Node) oneLotto.children().get(1)).attribute("red");
            String red3 = (String) ((Node) oneLotto.children().get(2)).attribute("red");
            String red4 = (String) ((Node) oneLotto.children().get(3)).attribute("red");
            String red5 = (String) ((Node) oneLotto.children().get(4)).attribute("red");
            String blue1 = (String) ((Node) oneLotto.children().get(5)).attribute("blue");
            String blue2 = (String) ((Node) oneLotto.children().get(6)).attribute("blue");

            redFlag = common.getSameSizeRed(listRed, redFlag, red1, red2, red3, red4, red5);
            blueFlag = common.getSameSizeBlue(listBlue, blueFlag, blue1, blue2);

            redSame[redFlag] += 1;
            blueSame[blueFlag] += 1;
            allSame[redFlag + blueFlag] += 1;

        }

        if (redSame[2] < 200 || redSame[2] > 275)
            return true;
        if (redSame[3] < 13 || redSame[3] > 35)
            return true;
        if (redSame[4] > 2)
            return true;
        if (redSame[5] > 0)
            return true;


        if (blueSame[1] < 500 || blueSame[1] > 620)
            return true;
        if (blueSame[2] < 18 || blueSame[2] > 37)
            return true;

        if (allSame[3] < 66 || allSame[3] > 125)
            return true;
        if (allSame[4] < 6 || allSame[4] > 20)
            return true;
        if (allSame[5] > 2)
            return true;
        if (allSame[6] > 0)
            return true;
        if (allSame[7] > 0)
            return true;

//        printLotto_sports(oneSports);
//        printLotto_sports(nowSports);

        if (!Arrays.equals(oneSports, nowSports))
            return true;

        printLotto_sports(oneSports);
        return false;
    }

    /**
     * 打印生成的数据
     *
     * @param oneSports
     */
    private static void printLotto_sports(String[] oneSports) {
        for (int i = 0; i < oneSports.length; i++) {
            if (i == 4)
                System.out.print(oneSports[i] + "   ");
            else
                System.out.print(oneSports[i] + " ");

        }
        System.out.print("\t\t" + cal);
        System.out.println();
    }

}
