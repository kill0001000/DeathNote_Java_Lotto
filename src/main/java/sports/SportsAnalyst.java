package sports;

import groovy.util.Node;

import java.util.ArrayList;
import java.util.List;

import common.Common;

/**
 * @author rren1
 * 
 */
public class SportsAnalyst {

	static Common common = new Common();
	static Node xml = common.getXmlData("lotto/data_sports.xml");
	static int historyListSize = 0;
	static int sameSize4_global = 0;
	static int sameSize5_global = 0;
	static int[] sameSize4_analyst = new int[21];
	static int[] sameSize5_analyst = new int[10];

	public static void main(String[] args) {

		// 开始分析
		analyst();

		// 打印分析报告
		printAnalystReport();

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
			oneSports = new String[8];
		}
	}

	/**
	 * 比较历史数据_sports
	 * 
	 * @param xml
	 *            历史数据 XML格式
	 * @param oneSports
	 *            随机一注
	 * @param sameSize
	 *            最大相同数
	 * @return
	 */
	private static void compare_sports(Node xml, String[] oneSports) {

		// 期号， 红球， 蓝球
		String compareDate = oneSports[0];
		List<String> listRed = getListRed(oneSports);
		List<String> listBlue = getListBlue(oneSports);

		// 遍历
		int flag = 0, sameSize4 = 0, sameSize5 = 0;
		String date, red1, red2, red3, red4, red5, blue1, blue2;
		historyListSize = xml.children().size();
		for (int i = 0; i < historyListSize; i++) {

			Node oneLotto = (Node) xml.children().get(i);

			date = (String) oneLotto.attribute("date");
			if (!compareDate.equals(date)) {
				red1 = (String) ((Node) oneLotto.children().get(0)).attribute("red");
				red2 = (String) ((Node) oneLotto.children().get(1)).attribute("red");
				red3 = (String) ((Node) oneLotto.children().get(2)).attribute("red");
				red4 = (String) ((Node) oneLotto.children().get(3)).attribute("red");
				red5 = (String) ((Node) oneLotto.children().get(4)).attribute("red");
				blue1 = (String) ((Node) oneLotto.children().get(5)).attribute("blue");
				blue2 = (String) ((Node) oneLotto.children().get(6)).attribute("blue");

				flag = getSameSize(listRed, listBlue, flag, red1, red2, red3, red4, red5, blue1, blue2);

				if (flag == 4) {
					sameSize4 += 1;
					sameSize4_global += 1;
				}
				if (flag == 5) {
					sameSize5 += 1;
					sameSize5_global += 1;
				}

				flag = 0;
			}
		}

		// 收集 相似数为4的数据
		if (sameSize4 < 21)
			sameSize4_analyst[sameSize4] += 1;
		// 收集 相似数为5的数据
		if (sameSize5 < 7)
			sameSize5_analyst[sameSize5] += 1;

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
	 * 计算和历史一期数据相同的个数
	 * 
	 * @param listRed
	 * @param listBlue
	 * @param flag
	 * @param red1
	 * @param red2
	 * @param red3
	 * @param red4
	 * @param red5
	 * @param blue1
	 * @param blue2
	 * @return
	 */
	private static int getSameSize(List<String> listRed, List<String> listBlue, int flag, String red1, String red2, String red3, String red4, String red5, String blue1,
			String blue2) {
		if (listRed.contains(red1))
			flag += 1;
		if (listRed.contains(red2))
			flag += 1;
		if (listRed.contains(red3))
			flag += 1;
		if (listRed.contains(red4))
			flag += 1;
		if (listRed.contains(red5))
			flag += 1;
		if (listBlue.contains(blue1))
			flag += 1;
		if (listBlue.contains(blue2))
			flag += 1;
		return flag;
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

}
