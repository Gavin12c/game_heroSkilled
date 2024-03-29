package com.hy;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Monitor {

	public Monitor() {
		new Thread(new KeyboardHook()).start(); // 监听
		final TrayIcon trayIcon; // 添加到系统托盘的托盘图标

		if (SystemTray.isSupported()) {

			SystemTray tray = SystemTray.getSystemTray(); // 任务栏状态区域(桌面的系统托盘)
			Image image = Toolkit.getDefaultToolkit().getImage(
					".//lib//king.png");

			ActionListener exitListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Exiting...");

					String i1 = KeyboardHook.getProcessId("Game.jar");
					String i2 = KeyboardHook.getProcessId("WinTab.jar");
					String i3 = KeyboardHook.getProcessId("MouseInfo.jar");
					System.out.println(i1);
					System.out.println(i2);
					System.out.println(i3);
					if (null != i1) {
						KeyboardHook.closeProcess(i1);
					}
					if (null != i2) {
						KeyboardHook.closeProcess(i2);
					}
					if (null != i3) {
						KeyboardHook.closeProcess(i3);
					}
					System.exit(0);
				}
			};

			PopupMenu popup = new PopupMenu(); // 创建具有空名称的新弹出式菜单。
			MenuItem defaultItem = new MenuItem("Exit"); // 加标签的菜单项,标签为"Exit" ;
															// 快捷键参数
															// MenuShortcut s
			defaultItem.addActionListener(exitListener);
			popup.add(defaultItem);

			trayIcon = new TrayIcon(image, "王者辅助", popup); // 创建带指定图像、工具提示和弹出菜单的
															// TrayIcon

			ActionListener actionListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					trayIcon.displayMessage("", "程序已启动",
							TrayIcon.MessageType.NONE); // 在托盘图标附近显示弹出消息。
														// [INFO提示信息 ERROR错误信息
														// NONE普通信息 WARNING警告信息
														// ]

				}
			};

			trayIcon.setImageAutoSize(true); // 设置自动调整大小的属性。
			trayIcon.addActionListener(actionListener);

			try {
				tray.add(trayIcon);
			} catch (AWTException e1) {
				e1.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		new Monitor();
	}

}