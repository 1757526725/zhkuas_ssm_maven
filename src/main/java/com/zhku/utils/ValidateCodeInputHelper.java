package com.zhku.utils;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * 验证码显示类
 * 
 * @author JackCan
 * 
 */
public class ValidateCodeInputHelper {

	public static String showValidateCode(byte[] img) {
		
		Icon icon = new ImageIcon(img);
		return (String) JOptionPane.showInputDialog(null, "请输入验证码：\n", "验证码", JOptionPane.PLAIN_MESSAGE, icon, null, "");
	}

}
