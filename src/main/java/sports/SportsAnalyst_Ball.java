package sports;

import common.Common;
import groovy.util.Node;

/**
 * @author rren1
 * 
 *         分析 每一个球
 * 
 *         概率统计
 * 
 */
public class SportsAnalyst_Ball {

	static Common common = new Common();
	static Node xml = common.getXmlData("lotto/data_sports.xml");

	static int historyListSize = 0;

	static int[] redBallList = new int[36];
	static int[] blueBallList = new int[13];

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

		historyListSize = xml.children().size();

		// 遍历
		Node oneLotto;

		for (int i = 0; i < historyListSize; i++) {

			oneLotto = (Node) xml.children().get(i);

			analystRedBall(oneLotto);
			analystBlueBall(oneLotto);

		}

	}

	/**
	 * 分析红球
	 * 
	 * @param oneLotto
	 */
	private static void analystRedBall(Node oneLotto) {
		int redBall;
		for (int j = 0; j < 5; j++) {

			redBall = Integer.parseInt((String) ((Node) oneLotto.children().get(j)).attribute("red"));
			redBallList[redBall] += 1;

		}
	}

	/**
	 * 分析蓝球
	 * 
	 * @param oneLotto
	 */
	private static void analystBlueBall(Node oneLotto) {
		int blueBall;
		for (int j = 5; j < 7; j++) {

			blueBall = Integer.parseInt((String) ((Node) oneLotto.children().get(j)).attribute("blue"));
			blueBallList[blueBall] += 1;

		}
	}

	/**
	 * 打印分析报告
	 */
	private static void printAnalystReport() {

		System.out.println("\nredBallList:");
		for (int i = 0; i < redBallList.length; i++) {
			System.out.println(i + "\t" + redBallList[i]);
		}

		System.out.println("\nblueBallList:");
		for (int i = 0; i < blueBallList.length; i++) {
			System.out.println(i + "\t" + blueBallList[i]);
		}
	}

}
