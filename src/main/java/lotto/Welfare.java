package lotto;

import groovy.util.Node;

import java.util.ArrayList;
import java.util.List;

public class Welfare {

	static Common common = new Common();
	static Node xml = common.getXmlData("lotto/data_welfare.xml");

	public static void main(String arge[]) {

		System.out.println("\n\nWelfare:" + common.getCurrentDate());
		get5_welfare();

	}

	/**
	 * 生成5注
	 * 
	 * @param sameSize
	 *            最大相同数
	 */
	private static void get5_welfare() {

		String[] oneWelfare = getOneWelfare();

		for (int i = 0; i < 5; i++) {
			do {
				oneWelfare = getOneWelfare();
			} while (compare_welfare(oneWelfare));
		}
	}

	/**
	 * 生成随机一注_welfare
	 * 
	 * @return
	 */
	private static String[] getOneWelfare() {
		// red 33
		String[] reds = common.randomSequence(33, 6);
		// blue 16
		String[] bule = common.randomSequence(16, 1);

		String[] welfare = new String[7];

		for (int i = 0; i < welfare.length; i++) {

			if (i != 6) {
				welfare[i] = reds[i];
			} else {
				welfare[i] = bule[0];
			}
		}

		return welfare;
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
	private static boolean compare_welfare(String[] oneWelfare) {

		List<String> list = new ArrayList<String>();
		String generlationBlue = oneWelfare[6];

		int size = xml.children().size();

		for (int i = 0; i < oneWelfare.length - 1; i++) {
			list.add(oneWelfare[i]);
		}

		// 遍历
		int flag = 0, sameSize4 = 0, sameSize5 = 0;
		String red1, red2, red3, red4, red5, red6, blue;
		for (int i = 0; i < size; i++) {
			Node oneLotto = (Node) xml.children().get(i);
			red1 = (String) ((Node) oneLotto.children().get(0)).attribute("red");
			red2 = (String) ((Node) oneLotto.children().get(1)).attribute("red");
			red3 = (String) ((Node) oneLotto.children().get(2)).attribute("red");
			red4 = (String) ((Node) oneLotto.children().get(3)).attribute("red");
			red5 = (String) ((Node) oneLotto.children().get(4)).attribute("red");
			red6 = (String) ((Node) oneLotto.children().get(5)).attribute("red");
			blue = (String) ((Node) oneLotto.children().get(6)).attribute("blue");

			flag = common.getSameSize_welfare(generlationBlue, list, flag, red1, red2, red3, red4, red5, red6, blue);

			if (flag > 5) {
				// System.out.println("^^^^^^^^^^^^^^^^^");
				return true;
			}

			if (flag == 4)
				sameSize4 += 1;
			if (flag == 5)
				sameSize5 += 1;
			flag = 0;
		}

		if (sameSize4 < 12 || sameSize4 > 20 || sameSize5 > 1) {
			// System.out.println("***************");
			return true;
		}
		printLotto_welfare(oneWelfare);
		return false;
	}

	/**
	 * 打印生成的数据
	 * 
	 * @param oneWelfare
	 */
	private static void printLotto_welfare(String[] oneWelfare) {
		for (int i = 0; i < oneWelfare.length; i++) {
			System.out.print(oneWelfare[i] + " ");
		}
		System.out.println();
	}

}
