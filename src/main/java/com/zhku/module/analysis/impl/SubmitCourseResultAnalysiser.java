package com.zhku.module.analysis.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SubmitCourseResultAnalysiser {

	public String doAnalysis(String html){
		if (html == null)
			return null;
		Document doc = null;
		doc = Jsoup.parse(html);
		Elements tables = doc.select("table");
		if (tables.size() < 1)
			return "获取不到选课结果";
		Element table = tables.get(0);
		Elements fonts = table.select("td").select("font");
		if (fonts.size() == 0)
			return "获取不到选课结果";
		String result = "";
		if (fonts.size() == 1)
			return fonts.get(0).html();
		for (Element font : fonts) {
			result += font.html() + "</br>";
		}
		return result;
	}
}
