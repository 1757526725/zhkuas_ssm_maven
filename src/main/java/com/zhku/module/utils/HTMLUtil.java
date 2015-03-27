package com.zhku.module.utils;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @author JackCan
 *
 */
public class HTMLUtil {
	public static void main(String[] args) {
//		String html=LocalHTMLutils.loadLocalHtml("termClassCoursePage.html");
//		System.out.println("文件载入完成");
//		Document doc = Jsoup.parse(html);
//		String headwords[] = {"课程","学分","总学时","考核方式","教师","上课班号","上课人数","周次","节次","地点"};
//		Element table = getTableByTableHeadWords(doc, headwords);
//		
		System.out.println(getSections("三[3-4节]"));
//		Element select = getSelectorByName(html,"Sel_XZBJ");
//		System.out.println(select);*/
//		
//		String nameNo =cutNameNo("[0000111]蔡梦筠");
//		System.out.println(nameNo);
	}
	private static Map<String ,Integer> dayMap = new HashMap<String,Integer>();
	static{
		dayMap.put("一", 1);
		dayMap.put("二", 2);
		dayMap.put("三", 3);
		dayMap.put("四", 4);
		dayMap.put("五", 5);
		dayMap.put("六", 6);
		dayMap.put("日", 7);
		dayMap.put("天", 7);
	}
	
	public static Element getTableByTableHeadWords(Document doc ,String [] tableHeadWords){
		Element table = null;
		Elements tables = doc.select("table");
		if(tables!=null&&tables.size()>0){
			if (tables.size() == 1) {
				table = tables.get(0);
			} else {
				// 选择表头匹配个数最多的table
				int maxMatch = -1;
				if (tables.size() == 0)
					tables = doc.select("table");
				for (Element tempTable : tables) {
					boolean hasInnerTable = tempTable.select(">tbody").toString().contains("table");
					if(hasInnerTable) continue;
					String text = tempTable.text().replaceAll("[^\u4E00-\u9FA5]", "");
					int temCount = headwordMatch(text,tableHeadWords);
					if (temCount > maxMatch) {
						table = tempTable;
						maxMatch = temCount;
					}
				}
			}
		}
		return table;
	}
	/**
	 * 统计 匹配 表头 关键字 个数
	 * 
	 * @param text
	 * @return
	 */
	private static int headwordMatch(String text,String [] headwords) {
		if (text == null)
			return -1;
		int count = 0;
		for (String headword : headwords) {
			if (text.contains(headword))
				count++;
		}
		return count;
	}
	/**
	 * 对与页面 的 一些动态通过js填充内容的select 的内容进行 提取，并封装成Doc 元素
	 * @param html
	 * @return
	 */
	public static Element getSelectorByName(String html,String selectName){
		if(html==null) return null;
		Document doc = Jsoup.parse(html);
		Elements selectors =null;
		//先去页面拿 ,如果拿不到，或者拿到的是空的 列表，则在js拿
		selectors=doc.select("select[name="+selectName+"]");
		if(selectors!=null&&selectors.select("option").size()>0&&selectors.text()!=null&&!selectors.text().trim().equals("")){
			return selectors.first();
		} 
		//首先去js里面拿,拿不到再去页面拿
		selectors=doc.select("script");
		if(selectors!=null&&selectors.size()>0){
			String seletorHtml=selectors.html().replaceAll("[\\s\\S]*(<select[\\w\\W]*>[\\w\\W]+</select>)", "$1");
			Document docTemp = Jsoup.parse("<html>"+seletorHtml+"</html>");
			return docTemp.select("select[name="+selectName+"]").first();
		}
		
		return null;
	}
	/**
	 * 截取 [0000111]蔡梦筠   000057|(网络课)魅力数学  这样的 信息中的 中文 
	 * @return
	 */
	public static String cutName(String text){
		if(text==null||text=="") return text;
		return text.replaceAll("[\\s\\S]*\\[[\\d]+\\]([\\s\\S]+)", "$1").replaceAll("[\\s\\S]*\\d+\\|([\\s\\S]+)", "$1");
	}
	
	/**
	 * 截取 [0000111]蔡梦筠   000057|(网络课)魅力数学 这样的 信息中的 代码0000111
	 * @return
	 */
	public static String cutNameNo(String text){
		if(text==null||text=="") return text;
		return text.replaceAll("[\\s\\S]*\\[([\\d]+)\\][\\s\\S]+", "$1").replaceAll("(\\d+)\\|[\\s\\S]+", "$1");
	}
	/**
	 * 截取 节次 三[3-4节]
	 * @param sectionStr
	 * @return
	 */
	public static String getSections(String sectionStr){
		return sectionStr.replaceAll("[\\s\\S]*?\\[([\\d-,]+)节*\\][\\s\\S]*", "$1");
	}
	/**
	 * 将 一 二 三 四 五 六 或者 星期一 二 。。。 转换成数字
	 * @param chineseDay
	 * @return
	 */
	public static int convertDay(String chineseDay){
		String key=chineseDay.replaceAll("[^一二三四五六日天]", "");
		return dayMap.get(key);
	}
	/**
	 * 获取隐藏的表单值
	 * @param html
	 * @return
	 */
	public static Map<String,String> getHiddenFormMap(String html){
		Document doc = Jsoup.parse(html);
		Elements hiddenIpts= doc.select("input[type=hidden]");
		Map<String,String> formMap = new HashMap<String,String>();
		for(Element ipt:hiddenIpts){
			formMap.put(ipt.attr("name"), ipt.val());
		}
		return formMap;
	}
	
	public static Map<String, String> getFormMap_Kingo(String html, int formIndex) {
		Map<String, String> retVal = new HashMap<String, String>();
		try {
			Document doc = Jsoup.parse(html);
			Elements elements = doc.select("form");
			Element formElement = elements.get(formIndex);
			retVal.put("formAction", formElement.attr("action"));
			Elements inputElements = doc.select("input");
			// System.out.println(inputElements);
			for (Element element : inputElements) {
				if (element.nodeName().equals("select")) {
					Element element5 = inputElements.select("option").first();
					retVal.put(element.attr("name"), element5.attr("value"));
				} else {
					if (element.attr("name").equals("") || element.attr("name") == null) {
					} else {
						retVal.put(element.attr("name"), element.attr("value"));
					}
				}
			}
		} catch (Exception e) {
			retVal = null;
		}
		return retVal;
	}
	
}
