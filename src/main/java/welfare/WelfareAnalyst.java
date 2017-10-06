package welfare;

import groovy.util.Node;

import java.util.ArrayList;
import java.util.List;

import common.Common;

public class WelfareAnalyst {

	static Common common = new Common();
	static Node xml = common.getXmlData("lotto/data_welfare.xml");
	static int historyListSize = 0;
	static int sameSize4_global = 0;
	static int sameSize5_global = 0;
	static int[] sameSize4_analyst = new int[30];
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
		String[] oneWelfare = new String[8];

		// 开始分析
		int size = xml.children().size();
		for (int i = 0; i < size; i++) {

			Node oneLotto = (Node) xml.children().get(i);

			oneWelfare[0] = (String) oneLotto.attribute("date");
			oneWelfare[1] = (String) ((Node) oneLotto.children().get(0)).attribute("red");
			oneWelfare[2] = (String) ((Node) oneLotto.children().get(1)).attribute("red");
			oneWelfare[3] = (String) ((Node) oneLotto.children().get(2)).attribute("red");
			oneWelfare[4] = (String) ((Node) oneLotto.children().get(3)).attribute("red");
			oneWelfare[5] = (String) ((Node) oneLotto.children().get(4)).attribute("red");
			oneWelfare[6] = (String) ((Node) oneLotto.children().get(5)).attribute("red");
			oneWelfare[7] = (String) ((Node) oneLotto.children().get(6)).attribute("blue");

			// 开始分析每一注
			compare_welfare(xml, oneWelfare);
			oneWelfare = new String[8];
		}
	}

	/**
	 * 比较历史数据_welfare
	 * 
	 * @param xml
	 *            历史数据 XML格式
	 * @param oneWelfare
	 *            随机一注
	 * @param sameSize
	 *            最大相同数
	 * @return
	 */
	private static void compare_welfare(Node xml, String[] oneWelfare) {

		// 期号， 红球， 蓝球
		String compareDate = oneWelfare[0];
		List<String> listRed = getListRed(oneWelfare);
		String generlationBlue = oneWelfare[7];

		// 遍历
		int flag = 0, sameSize4 = 0, sameSize5 = 0;
		String date, red1, red2, red3, red4, red5, red6, blue;
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
				red6 = (String) ((Node) oneLotto.children().get(5)).attribute("red");
				blue = (String) ((Node) oneLotto.children().get(6)).attribute("blue");

				flag = common.getSameSize_welfare(generlationBlue, listRed, flag, red1, red2, red3, red4, red5, red6, blue);

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
		if (sameSize4 < sameSize4_analyst.length)
			sameSize4_analyst[sameSize4] += 1;
		// 收集 相似数为5的数据
		if (sameSize5 < sameSize5_analyst.length)
			sameSize5_analyst[sameSize5] += 1;

		System.out.println(oneWelfare[0] + " " + oneWelfare[1] + " " + oneWelfare[2] + " " + oneWelfare[3] + " " + oneWelfare[4] + " " + oneWelfare[5] + " " + oneWelfare[6] + " "
				+ oneWelfare[7] + ": sameSize4 - " + sameSize4 + " sameSize5 - " + sameSize5);

	}

	/**
	 * 得到红球的数据列表
	 * 
	 * @param oneWelfare
	 * @return
	 */
	private static List<String> getListRed(String[] oneWelfare) {
		List<String> listRed = new ArrayList<String>();

		for (int i = 1; i < oneWelfare.length - 1; i++) {
			listRed.add(oneWelfare[i]);
		}
		return listRed;
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
