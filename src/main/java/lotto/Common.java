package lotto;

import groovy.util.Node;
import groovy.util.XmlParser;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Common {

	/**
	 * 随机 生成一组颜色的球
	 * 
	 * @param size
	 *            数据范围
	 * @param get
	 *            需要的个数
	 * @return
	 */
	public String[] randomSequence(int size, int get) {

		int[] sequence = new int[size];
		String[] output = new String[get];

		for (int i = 0; i < size; i++) {
			sequence[i] = i + 1;
		}

		Random random = new Random();

		for (int i = 0; i < get; i++) {
			int num = random.nextInt(size);

			int ball = sequence[num];
			if (ball < 10) {
				output[i] = "0" + ball;
			} else {
				output[i] = "" + ball;
			}
			sequence[num] = sequence[size - 1];
			size--;
		}

		Arrays.sort(output);
		return output;
	}

	/**
	 * 获取历史数据
	 * 
	 * @param pathname
	 *            指定历史数据目录
	 * @return
	 */
	public Node getXmlData(String pathname) {
		try {
			// 加载xml文件
			File file = new File(pathname);

			// 转换成xml格式
			XmlParser xmlParser;
			xmlParser = new XmlParser();
			Node xml = xmlParser.parse(file);
			return xml;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		String date = sdf.format(calendar.getTime());
		return date;
	}

	/**
	 * 
	 * 对比
	 * 
	 * 获得两注 的相似数
	 * 
	 * @param oneWelfare
	 * @param list
	 * @param flag
	 * @param red1
	 * @param red2
	 * @param red3
	 * @param red4
	 * @param red5
	 * @param red6
	 * @param blue
	 * @return
	 */
	public int getSameSize_welfare(String generlationBlue, List<String> list, int flag, String red1, String red2, String red3, String red4, String red5, String red6, String blue) {

		if (list.contains(red1))
			flag += 1;
		if (list.contains(red2))
			flag += 1;
		if (list.contains(red3))
			flag += 1;
		if (list.contains(red4))
			flag += 1;
		if (list.contains(red5))
			flag += 1;
		if (list.contains(red6))
			flag += 1;
		if (generlationBlue.equals(blue))
			flag += 1;
		return flag;
	}

	/**
	 * 打印生成的数据
	 * 
	 * @param lotto
	 */
	private static void printOneLotto(String[] lotto) {
		System.out.println();
		for (int i = 0; i < lotto.length; i++) {
			System.out.print(lotto[i] + " ");
		}
	}

}