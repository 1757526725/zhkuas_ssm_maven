package com.zhku.utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 本地文件测试工具类
 * 
 * @author JackCan
 * 
 */
public class LocalHTMLutils {
	public static final String PATH = System.getProperty("user.dir") + "\\source\\";

	public static void main(String[] args) {
		String html=loadLocalHtml("List_XZBJ.html","GBK");
		System.out.println(html);
	}
	/**
	 * 跟文件名称，在运行目录下的 source 文件夹中找到 对应的 html 文件 并载入
	 * 
	 * @param htmlName
	 * @return
	 */
	public static String loadLocalHtml(String fileName, String charset) {
		String filePath = PATH + fileName;
		File file = new File(filePath);
		BufferedReader in = null;
		String html = "";
		String line=null;
		try {
			in = new BufferedReader(new FileReader(file));
			while ((line = in.readLine()) != null) {
				html += line;
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(in!=null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return html;
	}

	public static String loadLocalHtml(String fileName) {
		return loadLocalHtml(fileName, "UTF-8");
	}
}
