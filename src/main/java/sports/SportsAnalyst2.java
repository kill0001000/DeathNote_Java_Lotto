package sports;

import groovy.util.Node;

import java.util.ArrayList;
import java.util.List;

import common.Common;

/**
 * @author rren1
 * 
 */
public class SportsAnalyst2 {

	static Common common = new Common();
	static Node xml = common.getXmlData("lotto/data_sports.xml");

	static int redSame0 = 0;
	static int redSame1 = 0;
	static int redSame2 = 0;
	static int redSame3 = 0;
	static int redSame4 = 0;
	static int redSame5 = 0;

	static int buleSame0 = 0;
	static int buleSame1 = 0;
	static int buleSame2 = 0;

	static int allSame0 = 0;
	static int allSame1 = 0;
	static int allSame2 = 0;
	static int allSame3 = 0;
	static int allSame4 = 0;
	static int allSame5 = 0;
	static int allSame6 = 0;
	static int allSame7 = 0;

	static int historyListSize = 0;
	static int sameSize4_global = 0;
	static int sameSize5_global = 0;

	// 每一注 相同数分析
	static int[] redSame = new int[6];
	static int[] blueSame = new int[3];
	static int[] allSame = new int[8];

	static int[] sameSize4_analyst = new int[21];
	static int[] sameSize5_analyst = new int[10];

	public static void main(String[] args) {

		// 开始分析
		analyst();

		// 打印分析报告
		// printAnalystReport();

	}

	/**
	 * 开始分析
	 */
	private static void analyst() {

		// 键存储，用于存取每一注历史数据
		String[] oneSports = new String[8];

		// 开始分析
		int size = xml.children().size();
		for (int i = 0; i < size; i++) {
			redSame = new int[6];
			blueSame = new int[3];
			allSame = new int[8];

			Node oneLotto = (Node) xml.children().get(i);

			oneSports[0] = (String) oneLotto.attribute("date");

			oneSports[1] = (String) ((Node) oneLotto.children().get(0)).attribute("red");
			oneSports[2] = (String) ((Node) oneLotto.children().get(1)).attribute("red");
			oneSports[3] = (String) ((Node) oneLotto.children().get(2)).attribute("red");
			oneSports[4] = (String) ((Node) oneLotto.children().get(3)).attribute("red");
			oneSports[5] = (String) ((Node) oneLotto.children().get(4)).attribute("red");
			oneSports[6] = (String) ((Node) oneLotto.children().get(5)).attribute("blue");
			oneSports[7] = (String) ((Node) oneLotto.children().get(6)).attribute("blue");

			// 开始分析每一注
			compare_sports(xml, oneSports);
			printOneAnalystReport(oneSports);
		}
	}

	/**
	 * 比较历史数据_sports
	 * 
	 * @param xml
	 *            历史数据 XML格式
	 * @param oneSports
	 *            随机一注
	 * @param redSame
	 *            最大相同数
	 * @return
	 */
	private static void compare_sports(Node xml, String[] oneSports) {

		// 期号， 红球， 蓝球
		String compareDate = oneSports[0];
		List<String> listRed = getListRed(oneSports);
		List<String> listBlue = getListBlue(oneSports);

		// 遍历
		historyListSize = xml.children().size();

		for (int i = 0; i < historyListSize; i++) {

			Node oneLotto = (Node) xml.children().get(i);

			String date = (String) oneLotto.attribute("date");

			if (!compareDate.equals(date)) {
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
		}

	}

	/**
	 * 得到红球的数据列表
	 * 
	 * @param oneSports
	 * @return
	 */
	private static List<String> getListRed(String[] oneSports) {
		List<String> listRed = new ArrayList<String>();

		for (int i = 1; i < oneSports.length - 2; i++) {
			listRed.add(oneSports[i]);
		}
		return listRed;
	}

	/**
	 * 得到蓝球的数据列表
	 * 
	 * @param oneSports
	 * @return
	 */
	private static List<String> getListBlue(String[] oneSports) {
		List<String> listBlue = new ArrayList<String>();

		listBlue.add(oneSports[6]);
		listBlue.add(oneSports[7]);
		return listBlue;
	}



	/**
	 * 打印分析报告
	 */
	private static void printAnalystReport() {

		System.out.println("\nhistoryListSize:" + historyListSize + "\tsameSize4_global:" + sameSize4_global + "\tsameSize5_global:" + sameSize5_global);

		String avg4 = String.format("%.2f", (double) sameSize4_global / (double) historyListSize);
		String avg5 = String.format("%.2f", (double) sameSize5_global / (double) historyListSize);
		System.out.println("historyListSize:" + historyListSize + "\tsameSize4_global:" + avg4 + "\tsameSize5_global:" + avg5);

		System.out.println("\nsameSize4_analyst:");
		for (int i = 0; i < sameSize4_analyst.length; i++) {
			System.out.println(i + "\t" + sameSize4_analyst[i]);

		}

		System.out.println("\nsameSize5_analyst:");
		for (int i = 0; i < sameSize5_analyst.length; i++) {
			System.out.println(i + "\t" + sameSize5_analyst[i]);

		}
	}

	/**
	 * 打印一条分析报告
	 */
	private static void printOneAnalystReport(String[] sport) {

		System.out.print(sport[0] + "\t" + sport[1] + " " + sport[2] + " " + sport[3] + " " + sport[4] + " " + sport[5] + "    " + sport[6] + " " + sport[7] + "\t\t");
		System.out.print(redSame[0] + "\t" + redSame[1] + "\t" + redSame[2] + "\t" + redSame[3] + "\t" + redSame[4] + "\t" + redSame[5] + "\t\t");
		System.out.print(blueSame[0] + "\t" + blueSame[1] + "\t" + blueSame[2] + "\t\t");
		System.out.print(allSame[0] + "\t" + allSame[1] + "\t" + allSame[2] + "\t" + allSame[3] + "\t" + allSame[4] + "\t" + allSame[5] + "\t" + allSame[6] + "\t" + allSame[7]);
		System.out.println();

	}

}
