package com.hy.utils;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

/**
 * @description Robot帮助类
 * 
 */
public final class Common {

	/**
	 * 鼠标单击(左击),双击就连续调用
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @param delay
	 *            该操作后的延迟时间
	 */
	public synchronized static void clickLMouse(Robot r, int x, int y, int delay) {
		r.mouseMove(x, y);
		r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		/*
		 * Random random = new Random(); double v = random.nextDouble(); v = 300
		 * + v*300;
		 */
		r.delay(1);
		r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		r.delay(delay);
	}

	// 测试
	public static void main(String[] args) {
		Robot r;
		try {
			r = new Robot();
			while (true) {
				Common.clickLMove(r, 710, 600, 510, 180, 1000);
				Thread.sleep(1000);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 鼠标按住移动
	 */
	public synchronized static void clickLMove(Robot r, int startX, int startY,
			int endX, int endY, int delay) {
		r.mouseMove(startX, startY);
		r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		r.delay(500);
		r.mouseMove(endX, endY);
		r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		r.delay(500);
		r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		r.delay(delay);
	}

	/**
	 * 指定次数单击左键
	 * 
	 * @param x
	 *            点击x坐标
	 * @param y
	 *            点击y坐标
	 * @param delay
	 * @param num
	 */
	public static void clickLMouse2(Robot r, int x, int y, int delay) {
		r.mouseMove(x, y);
		r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		r.delay(delay);
		r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

	public static Color getScreenPixel(int x, int y) throws AWTException { // 函数返回值为颜色的RGB值。
		Robot rb = null; // java.awt.image包中的类，可以用来抓取屏幕，即截屏。
		rb = new Robot();
		Toolkit tk = Toolkit.getDefaultToolkit(); // 获取缺省工具包
		Dimension di = tk.getScreenSize(); // 屏幕尺寸规格
		// System.out.println(di.width);
		// System.out.println(di.height);
		Rectangle rec = new Rectangle(0, 0, di.width, di.height);
		BufferedImage bi = rb.createScreenCapture(rec);
		int pixelColor = bi.getRGB(x, y);
		Color color = new Color(16777216 + pixelColor);
		bi = null;
		rec = null;
		di = null;
		tk = null;
		return color; // pixelColor的值为负，经过实践得出：加上颜色最大值就是实际颜色值。
	}

	/**
	 * 判断是否点击坐标
	 * 
	 * @param x
	 *            点击x坐标
	 * @param y
	 *            点击y坐标
	 * @param xpan
	 *            需要判断的x坐标
	 * @param ypan
	 *            需要判断的y坐标
	 * @param rgb
	 *            指定rgb值
	 * @param delay
	 *            延迟时间
	 * @param red
	 *            红
	 * @param g
	 *            绿
	 * @param b
	 *            蓝
	 * @throws IOException
	 */
	public static void clickLMouseIfNo(Robot r, int x, int y, int red, int g,
			int b, int delay, int num) throws IOException {
		Color c;
		try {
			c = getScreenPixel(x, y);

			int getRed = Math.abs(c.getRed() - red);
			int getGreen = Math.abs(c.getGreen() - g);
			int getBlue = Math.abs(c.getBlue() - b);

			if (getRed > 10 || getGreen > 10 || getBlue > 10) {
				clickLMouse(r, x, y, delay);
			}
			c = null;
		} catch (AWTException e) {
			System.out.println("屏幕获取失败");
			e.printStackTrace();
		}

		// BufferedImage c = captureFullScreen2(r);
		// int pixel = c.getRGB(x, y);
		// int getRed = (pixel & 0xff0000) >> 16;
		// int getGreen = (pixel & 0xff00) >> 8;
		// int getBlue = (pixel & 0xff);
	}

	/**
	 * 判断是否点击坐标
	 * 
	 * @param x
	 *            点击x坐标
	 * @param y
	 *            点击y坐标
	 * @param xpan
	 *            需要判断的x坐标
	 * @param ypan
	 *            需要判断的y坐标
	 * @param rgb
	 *            指定rgb值
	 * @param delay
	 *            延迟时间
	 * @param red
	 *            红
	 * @param g
	 *            绿
	 * @param b
	 *            蓝
	 * @throws IOException
	 */
	public static void clickLMouseIfYes(Robot r, int x, int y, int red, int g,
			int b, int delay, int num) throws Exception {
		Color c = getScreenPixel(x, y);

		int getRed = Math.abs(c.getRed() - red);
		int getGreen = Math.abs(c.getGreen() - g);
		int getBlue = Math.abs(c.getBlue() - b);

		if (getRed < 10 && getGreen < 10 && getBlue < 10) {
			clickLMouse(r, x, y, delay);
		}
		c = null;

	}

	/**
	 * 鼠标右击
	 */
	public static void clickRMouse(Robot r, int x, int y, int delay) {
		r.mouseMove(x, y);
		r.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		r.delay(10);
		r.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
		r.delay(delay);
	}

	/**
	 * 键盘输入(一次只能输入一个字符)
	 * 
	 * @param ks
	 *            键盘输入的字符数组
	 * @param delay
	 *            输入一个键后的延迟时间
	 */
	public static void pressKeys(Robot r, int[] ks, int delay) {
		for (int i = 0; i < ks.length; i++) {
			r.keyPress(ks[i]);
			r.delay(1);
			r.keyRelease(ks[i]);
			r.delay(delay);
		}
	}

	/**
	 * 复制
	 * 
	 * @param r
	 * @throws InterruptedException
	 */
	void doCopy(Robot r) throws InterruptedException {
		Thread.sleep(3000);
		r.setAutoDelay(200);
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_C);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyRelease(KeyEvent.VK_C);
	}

	/**
	 * 粘贴
	 * 
	 * @param r
	 * @throws InterruptedException
	 */
	void doParse(Robot r) throws InterruptedException {
		r.setAutoDelay(500);
		Thread.sleep(2000);
		r.mouseMove(300, 300);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyRelease(KeyEvent.VK_V);
	}

	/**
	 * 捕捉全屏幕
	 */
	public Icon captureFullScreen(Robot r) {
		BufferedImage fullScreenImage = r.createScreenCapture(new Rectangle(
				Toolkit.getDefaultToolkit().getScreenSize()));
		ImageIcon icon = new ImageIcon(fullScreenImage);
		return icon;
	}

	/**
	 * 捕捉全屏幕
	 */
	public static BufferedImage captureFullScreen2(Robot r) {
		BufferedImage c = r.createScreenCapture(new Rectangle(Toolkit
				.getDefaultToolkit().getScreenSize()));
		return c;
	}

	/**
	 * 捕捉屏幕的一个矫形区域
	 * 
	 * @param r
	 * @param x
	 *            x坐标位置
	 * @param y
	 *            y坐标位置
	 * @param width
	 *            矩形的宽
	 * @param height
	 *            矩形的高
	 * @return
	 */
	public Icon capturePartScreen(Robot r, int x, int y, int width, int height) {
		r.mouseMove(x, y);
		BufferedImage fullScreenImage = r.createScreenCapture(new Rectangle(
				width, height));
		ImageIcon icon = new ImageIcon(fullScreenImage);
		return icon;
	}

	/**
	 * 自定义截图
	 * 
	 * @param r
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public BufferedImage capturePartScreen2(Robot r, int x, int y, int width,
			int height) {
		// r.mouseMove(x, y);
		BufferedImage fullScreenImage = r.createScreenCapture(new Rectangle(
				width, height));
		return fullScreenImage;
	}

	/**
	 * 如果颜色变化，则点击
	 * 
	 * @param r
	 * @param x
	 * @param y
	 * @param xpan
	 * @param ypan
	 * @throws Exception
	 */
	public static void ifChange(Robot r, int xpan, int ypan) throws Exception {
		HashSet<Integer> hashSet = new HashSet<Integer>();
		for (int i = 0; i < 5; i++) {
			int rgb = getScreenPixel(xpan, ypan).getRGB();
			Thread.sleep(1);
			hashSet.add(rgb);
		}
		if (hashSet.size() != 1) {
			clickLMouse(r, xpan, ypan, 300);
		}
	}

	/**
	 * 判断颜色是否变化
	 * 
	 * @param r
	 * @param x
	 * @param y
	 * @param xpan
	 * @param ypan
	 */
	public static boolean ifChangeT(Robot r, int xpan, int ypan) {
		try {
			HashSet<Integer> hashSet = new HashSet<Integer>();
			for (int i = 0; i < 10; i++) {
				int rgb = getScreenPixel(xpan, ypan).getRGB();
				Thread.sleep(100);
				hashSet.add(rgb);
			}
			if (hashSet.size() != 1) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean IfNo(int xpan, int ypan, int red, int g, int b)
			throws IOException {
		Color c;
		try {
			c = getScreenPixel(xpan, ypan);
			int getRed = Math.abs(c.getRed() - red);
			int getGreen = Math.abs(c.getGreen() - g);
			int getBlue = Math.abs(c.getBlue() - b);
			if (getRed > 10 || getGreen > 10 || getBlue > 10) {
				return true;
			}
			c = null;
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return false;

	}

	public static boolean IfYes(int xpan, int ypan, int red, int g, int b)
			throws IOException {
		Color c;
		try {
			c = getScreenPixel(xpan, ypan);
			Thread.sleep(1);
			int getRed = Math.abs(c.getRed() - red);
			int getGreen = Math.abs(c.getGreen() - g);
			int getBlue = Math.abs(c.getBlue() - b);
			if (getRed < 10 && getGreen < 10 && getBlue < 10) {
				return true;
			}
			c = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	public static WinUser.INPUT input = new WinUser.INPUT();

	public static void sendChar2(char ch) throws InterruptedException {
		input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
		input.input.setType("ki");
		input.input.ki.wScan = new WinDef.WORD(0);
		input.input.ki.time = new WinDef.DWORD(0);
		input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
		// Press
		input.input.ki.wVk = new WinDef.WORD(Character.toUpperCase(ch)); // 0x41
		input.input.ki.dwFlags = new WinDef.DWORD(0); // keydown
		User32.INSTANCE.SendInput(new WinDef.DWORD(1),
				(WinUser.INPUT[]) input.toArray(1), input.size());
		// Release
		input.input.ki.wVk = new WinDef.WORD(Character.toUpperCase(ch)); // 0x41
		input.input.ki.dwFlags = new WinDef.DWORD(2); // keyup
		User32.INSTANCE.SendInput(new WinDef.DWORD(1),
				(WinUser.INPUT[]) input.toArray(1), input.size());
	}

	/**
	 * 键盘输出字符串
	 */
	public static void insertStr(String str) {
		try {
			char[] charArray = str.toCharArray();
			for (char c : charArray) {
				sendChar2(c);
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
