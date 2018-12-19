package sports;

import common.Common;
import groovy.util.Node;

import java.util.ArrayList;
import java.util.List;


public class Sports2 {

    static Common common = new Common();

    // 每一注 相同数分析
    static int[] redSame = new int[6];
    static int[] blueSame = new int[3];
    static int[] allSame = new int[8];

    static int cal = 0;

    static String[] reds;
    static String[] bule;

    public static void main(String[] args) {

        System.out.println("\n\nSports:" + common.getCurrentDate());
        get_sports();

    }


    /**
     * 得到组合结果
     *
     * @param num 从N个数中选取num个数
     * @param str 包含Ng个元素的字符串
     * @return 组合结果
     */
    public static List<String> getCombinationResult(int num, String str) {
        List<String> result = new ArrayList<String>();
        if (num == 1) {
            for (char c : str.toCharArray()) {
                result.add(String.valueOf(c));
            }
            return result;
        }
        if (num >= str.length()) {
            result.add(str);
            return result;
        }
        int strlen = str.length();
        for (int i = 0; i < (strlen - num + 1); i++) {
            List<String> cr = getCombinationResult(num - 1, str.substring(i + 1));//从i+1处直至字符串末尾
            char c = str.charAt(i);//得到上面被去掉的字符，进行组合
            for (String s : cr) {
                result.add(c + s);
            }
        }
        return result;
    }


    /**
     * 生成1注
     */
    private static void get_sports() {
        Node xml = common.getXmlData("lotto/data_sports.xml");

        String[] oneSports = getOneSports();

        printLotto_sports(reds);

        getCombinationResult(5, "ABCDFRE");


    }


    /**
     * 生成随机一注_sports
     *
     * @return
     */
    private static String[] getOneSports() {
        // red 6/35
        reds = common.randomSequence(35, 6);
        // blue 3/12
        bule = common.randomSequence(12, 3);

        String[] Sports = new String[9];

        for (int i = 0; i < Sports.length; i++) {

            if (i < 6) {
                Sports[i] = reds[i];
            } else {
                Sports[i] = bule[i - 6];
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

        if (redSame[0] < 750 || redSame[0] > 860)
            return true;
        if (redSame[1] < 680 || redSame[1] > 800)
            return true;
        if (redSame[2] < 180 || redSame[2] > 275)
            return true;
        if (redSame[3] < 13 || redSame[3] > 35)
            return true;
        if (redSame[4] > 2)
            return true;
        if (redSame[5] > 0)
            return true;

        if (blueSame[0] < 1150 || blueSame[0] > 1250)
            return true;
        if (blueSame[1] < 500 || blueSame[1] > 580)
            return true;
        if (blueSame[2] < 16 || blueSame[2] > 36)
            return true;

        if (allSame[0] < 448 || allSame[0] > 596)
            return true;
        if (allSame[1] < 690 || allSame[1] > 771)
            return true;
        if (allSame[2] < 334 || allSame[2] > 434)
            return true;
        if (allSame[3] < 66 || allSame[3] > 135)
            return true;
        if (allSame[4] < 5 || allSame[4] > 17)
            return true;
        if (allSame[5] > 2)
            return true;
        if (allSame[6] > 0)
            return true;
        if (allSame[7] > 0)
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
            if (i == 5)
                System.out.print(oneSports[i] + "   ");
            else
                System.out.print(oneSports[i] + " ");

        }
        System.out.print("\t\t" + cal);
        System.out.println();
    }

}
