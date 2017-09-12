package lotto;

import groovy.util.Node;

import java.util.ArrayList;
import java.util.List;

public class Sports {

	static Common common = new Common();

	public static void main(String[] args) {

		System.out.println("\n\nSports:" + common.getCurrentDate());
		get5_sports();

	}

	/**
	 * 生成5注
	 * 
	 * @param sameSize
	 *            最大相同数
	 */
	private static void get5_sports() {
		Node xml = common.getXmlData("lotto/data_sports.xml");

		String[] oneSports = getOneSports();

		for (int i = 0; i < 5; i++) {
			do {
				oneSports = getOneSports();
			} while (compare_sports(xml, oneSports));
		}
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
	 * @param xml
	 *            历史数据 XML格式
	 * @param oneSports
	 *            随机一注
	 * @param sameSize
	 *            最大相同数
	 * @return
	 */
	private static boolean compare_sports(Node xml, String[] oneSports) {

		List<String> listRed = new ArrayList<String>();
		List<String> listBlue = new ArrayList<String>();

		for (int i = 0; i < oneSports.length - 2; i++) {
			listRed.add(oneSports[i]);
		}

		listBlue.add(oneSports[5]);
		listBlue.add(oneSports[6]);

		// 遍历
		int flag = 0, flag_redBall = 0, sameSize4 = 0, sameSize5 = 0, sameSize3_redBall = 0, sameSize4_redBall = 0;
		int size = xml.children().size();
		String date, red1, red2, red3, red4, red5, blue1, blue2;
		for (int i = 0; i < size; i++) {

			Node oneLotto = (Node) xml.children().get(i);

			date = (String) oneLotto.attribute("date");
			red1 = (String) ((Node) oneLotto.children().get(0)).attribute("red");
			red2 = (String) ((Node) oneLotto.children().get(1)).attribute("red");
			red3 = (String) ((Node) oneLotto.children().get(2)).attribute("red");
			red4 = (String) ((Node) oneLotto.children().get(3)).attribute("red");
			red5 = (String) ((Node) oneLotto.children().get(4)).attribute("red");
			blue1 = (String) ((Node) oneLotto.children().get(5)).attribute("blue");
			blue2 = (String) ((Node) oneLotto.children().get(6)).attribute("blue");

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

			flag_redBall = flag;

			if (listBlue.contains(blue1))
				flag += 1;
			if (listBlue.contains(blue2))
				flag += 1;

			if (flag_redBall > 3 || flag > 4) {
				// System.out.println(date + " " + red1 + " " + red2 + " " + red3 + " " + red4 + " " + red5 + " " + blue1 + " " + blue2);
				return true;
			}

			if (flag_redBall == 3)
				sameSize3_redBall += 1;
			if (flag_redBall == 4)
				sameSize4_redBall += 1;

			if (flag == 4)
				sameSize4 += 1;
			if (flag == 5)
				sameSize5 += 1;

			flag = 0;
		}

		if (sameSize3_redBall < 15 || sameSize3_redBall > 25 || sameSize4 < 6 || sameSize4 > 13) {
			// System.out.println("sameSize4:" + sameSize4 + "\tsameSize3_redBall:" + sameSize3_redBall);
			return true;
		}

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
			System.out.print(oneSports[i] + " ");
		}
		System.out.println();
	}

}
