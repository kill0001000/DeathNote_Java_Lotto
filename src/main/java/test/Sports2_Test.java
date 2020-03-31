package test;

import common.Common;
import groovy.util.Node;

import java.util.ArrayList;
import java.util.List;


public class Sports2_Test {

    static Common common = new Common();

    // 每一注 相同数分析
    static int[] redSame = new int[6];
    static int[] blueSame = new int[3];
    static int[] allSame = new int[8];

    static int cal = 0;
    static boolean isfindSame = true;
    static boolean isFindThis = true;

    public static String[] getOneSports() {
        return oneSports;
    }

    public static void setOneSports(String[] oneSports) {
        Sports2_Test.oneSports = oneSports;
    }

    static String[] oneSports = new String[9];


    public static void main(String[] args) {
        System.out.println("\n\nSports:" + common.getCurrentDate());
        List<String[]> combinationResult;

        do {
            combinationResult = createOne_6_3();
            cal++;
            for (String[] oneSport : combinationResult) {
                isfindSame = get_sports(oneSport);
                if (isfindSame == true) {
                    break;
                }

            }
            isFindThis();

        } while (isfindSame && isFindThis);


    }

    private static boolean isFindThis() {

        String[] listRedThis = {"03", "05", "07", "20", "23"};
        String[] listBlueThis = {"04", "11"};

        String[] one = getOneSports();

        List<String> listRed = new ArrayList<String>();
        List<String> listBlue = new ArrayList<String>();

        for (int i = 0; i < one.length - 3; i++) {
            listRed.add(one[i]);
        }

        listBlue.add(one[6]);
        listBlue.add(one[7]);
        listBlue.add(one[8]);


        listBlue.contains(listBlueThis[0]);
        listRed.contains(listRedThis);


        return true;
    }


    private static List<String[]> createOne_6_3() {

        // red 6/35
        String[] reds = common.randomSequence(35, 6);
        // blue 3/12
        String[] bules = common.randomSequence(12, 3);

        oneSports = getOneSports(reds, bules);

        printLotto_6_3(oneSports);
        return getCombinationResult(reds, bules);
    }


    /**
     * 生成1注
     */
    private static boolean get_sports(String[] oneSport) {
        Node xml = common.getXmlData("lotto/data_sports.xml");

        return compare_sports(xml, oneSport);
    }


    /**
     * 比较历史数据_sports
     *
     * @param xml       历史数据 XML格式
     * @param oneSports 随机一注
     * @return
     */
    private static boolean compare_sports(Node xml, String[] oneSports) {

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

        if (redSame[0] < 733 || redSame[0] > 900)
            return true;
        if (redSame[1] < 729 || redSame[1] > 851)
            return true;
        if (redSame[2] < 190 || redSame[2] > 296)
            return true;
        if (redSame[3] < 15 || redSame[3] > 34)
            return true;
        if (redSame[4] > 2)
            return true;
        if (redSame[5] > 0)
            return true;

        if (blueSame[0] < 1212 || blueSame[0] > 1289)
            return true;
        if (blueSame[1] < 521 || blueSame[1] > 596)
            return true;
        if (blueSame[2] < 16 || blueSame[2] > 36)
            return true;

        if (allSame[0] < 500 || allSame[0] > 630)
            return true;
        if (allSame[1] < 750 || allSame[1] > 826)
            return true;
        if (allSame[2] < 360 || allSame[2] > 459)
            return true;
        if (allSame[3] < 62 || allSame[3] > 121)
            return true;
        if (allSame[4] < 4 || allSame[4] > 18)
            return true;
        if (allSame[5] > 2)
            return true;
        if (allSame[6] > 0)
            return true;
        if (allSame[7] > 0)
            return true;

        return false;
    }


    /**
     * 生成随机一注_sports
     *
     * @return
     */
    private static String[] getOneSports(String[] reds, String[] bules) {
        String[] Sports = new String[9];

        for (int i = 0; i < Sports.length; i++) {

            if (i < 6)
                Sports[i] = reds[i];
            else
                Sports[i] = bules[i - 6];

        }
        return Sports;
    }

    /**
     * 打印生成的数据
     *
     * @param oneSports
     */
    private static void printLotto_5_2(String[] oneSports) {
        for (int i = 0; i < oneSports.length; i++) {
            if (i == 4)
                System.out.print(oneSports[i] + "   ");
            else
                System.out.print(oneSports[i] + " ");

        }
        System.out.print("\t\t" + cal);
        System.out.println();
    }


    /**
     * 打印生成的数据
     *
     * @param oneSports
     */
    private static void printLotto_6_3(String[] oneSports) {
        for (int i = 0; i < oneSports.length; i++) {
            if (i == 5)
                System.out.print(oneSports[i] + "   ");
            else
                System.out.print(oneSports[i] + " ");

        }
        System.out.print("\t\t" + cal);
        System.out.println();
    }

    /**
     * 得到组合结果
     */
    public static List<String[]> getCombinationResult(String[] reds, String[] bules) {
        List<String[]> result = new ArrayList<String[]>();

        String[] temp01 = {reds[0], reds[1], reds[2], reds[3], reds[4], bules[0], bules[1]};
        String[] temp02 = {reds[0], reds[1], reds[2], reds[3], reds[5], bules[0], bules[1]};
        String[] temp03 = {reds[0], reds[1], reds[2], reds[4], reds[5], bules[0], bules[1]};
        String[] temp04 = {reds[0], reds[1], reds[3], reds[4], reds[5], bules[0], bules[1]};
        String[] temp05 = {reds[0], reds[2], reds[3], reds[4], reds[5], bules[0], bules[1]};
        String[] temp06 = {reds[1], reds[2], reds[3], reds[4], reds[5], bules[0], bules[1]};

        result.add(temp01);
        result.add(temp02);
        result.add(temp03);
        result.add(temp04);
        result.add(temp05);
        result.add(temp06);

        String[] temp11 = {reds[0], reds[1], reds[2], reds[3], reds[4], bules[0], bules[2]};
        String[] temp12 = {reds[0], reds[1], reds[2], reds[3], reds[5], bules[0], bules[2]};
        String[] temp13 = {reds[0], reds[1], reds[2], reds[4], reds[5], bules[0], bules[2]};
        String[] temp14 = {reds[0], reds[1], reds[3], reds[4], reds[5], bules[0], bules[2]};
        String[] temp15 = {reds[0], reds[2], reds[3], reds[4], reds[5], bules[0], bules[2]};
        String[] temp16 = {reds[1], reds[2], reds[3], reds[4], reds[5], bules[0], bules[2]};

        result.add(temp11);
        result.add(temp12);
        result.add(temp13);
        result.add(temp14);
        result.add(temp15);
        result.add(temp16);

        String[] temp21 = {reds[0], reds[1], reds[2], reds[3], reds[4], bules[1], bules[2]};
        String[] temp22 = {reds[0], reds[1], reds[2], reds[3], reds[5], bules[1], bules[2]};
        String[] temp23 = {reds[0], reds[1], reds[2], reds[4], reds[5], bules[1], bules[2]};
        String[] temp24 = {reds[0], reds[1], reds[3], reds[4], reds[5], bules[1], bules[2]};
        String[] temp25 = {reds[0], reds[2], reds[3], reds[4], reds[5], bules[1], bules[2]};
        String[] temp26 = {reds[1], reds[2], reds[3], reds[4], reds[5], bules[1], bules[2]};

        result.add(temp21);
        result.add(temp22);
        result.add(temp23);
        result.add(temp24);
        result.add(temp25);
        result.add(temp26);

        return result;
    }

}
