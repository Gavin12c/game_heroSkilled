package com.hy.service;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hy.Monitor;
import com.hy.utils.Common;
import com.hy.utils.FileIO;

/**
 * @author John 脚本开启
 */
public class Zero implements Runnable {

	// 装配文件路径
	private final static String path = System.getProperty("user.dir");

	private static List<Integer[]> list3;// ifYes

	private volatile static boolean flag = true;

	public void stop() {
		flag = false;
	}

	public void start() {
		flag = true;
	}

	public static void main(String[] args) throws Exception {
		// go();
		new Monitor();

	}

	@Override
	public void run() {
		try {
			// 判断是rbg颜色，就点
			String[] split3 = io("/ifYesXY.txt");
			// LinkedList add,del 有优势,arraylist 适用查找
			list3 = new ArrayList<>();
			for (int i = 1; i < split3.length; i++) {
				String[] smap3 = split3[i].split(",");
				Integer[] point = { Integer.parseInt(smap3[0]),
						Integer.parseInt(smap3[1]), Integer.parseInt(smap3[2]),
						Integer.parseInt(smap3[3]), Integer.parseInt(smap3[4]) };
				list3.add(point);
			}
			Robot r = new Robot();
			int delay;
			System.out.println("ifYes执行间隔： " + split3[0] + " 秒");
			if (split3[0].isEmpty() || split3[0] == "" || split3[0] == null) {
				delay = 3000;
			} else {
				delay = Integer.parseInt(split3[0]);
			}
			while (flag) {
				ifYesXY(getList3(), r);
				// System.out.println(1);
				Thread.sleep(delay);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 过滤汉字 */
	public static String[] io(String filePath) {
		String t = FileIO.readTxtFile(path + filePath);
		// 过滤汉字
		Pattern pattern = Pattern.compile("[^0-9,;;]");
		Matcher matcher = pattern.matcher(t);
		String all = matcher.replaceAll("");
		return all.split(";");
	}

	/**
	 * @param list
	 *            坐标集合
	 * @param delay
	 *            每次遍历休息时间
	 */
	public synchronized static void ifYesXY(final List<Integer[]> list,
			final Robot r) {
		try {
			if (list != null && list.size() > 0
					&& Common.IfNo(1127, 630, 206, 207, 225)) {
				for (Integer[] integers : list) {
					Common.clickLMouseIfYes(r, integers[0], integers[1],
							integers[2], integers[3], integers[4], 1, 0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<Integer[]> getList3() {
		return list3;
	}

	public static void setList3(List<Integer[]> list3) {
		Zero.list3 = list3;
	}

	public static void wirteTxt(int x, int y, int r, int g, int b) {
		String text = "";
		try {
			File file1 = new File(path);
			Writer writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file1, true), "UTF-8"));
			writer.write(text + "\r\n" + ";" + x + "," + y + "," + r + "," + g
					+ "," + b + "\r\n");
			writer.flush();
			writer.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 获取颜色
	 */
	public static Color getScreenPixel(int x, int y) { // 函数返回值为颜色的RGB值。
		try {
			Robot rb = new Robot(); // java.awt.image包中的类，可以用来抓取屏幕，即截屏。
			Toolkit tk = Toolkit.getDefaultToolkit(); // 获取缺省工具包
			Dimension di = tk.getScreenSize(); // 屏幕尺寸规格
			// System.out.println(di.width);
			// System.out.println(di.height);
			Rectangle rec = new Rectangle(0, 0, di.width, di.height);
			BufferedImage bi = rb.createScreenCapture(rec);
			int pixelColor = bi.getRGB(x, y);
			Color color = new Color(16777216 + pixelColor);
			return color; // pixelColor的值为负，经过实践得出：加上颜色最大值就是实际颜色值。
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * public synchronized static void ifChange(final List<Integer[]> list, int
	 * delay,final boolean flag) {
	 * 
	 * if (list != null && list.size() > 0) {
	 * 
	 * TimerTask task = new TimerTask() {
	 * 
	 * @Override public synchronized void run() { try { Robot r = new Robot();
	 * // 判断颜色 改变了 就 点击 for (Integer[] integers : list) { Common.ifChange(r,
	 * integers[0], integers[1]); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } } };
	 * 
	 * new Timer().schedule(task, 1000, 1000);
	 * 
	 * }
	 * 
	 * }
	 */

}
