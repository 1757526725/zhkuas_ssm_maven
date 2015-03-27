package com.zhku.utils;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhku.bean.MyCourseScheme;
import com.zhku.bean.MySchedule;
import com.zhku.bean.TermCourse;
import com.zhku.bean.TimeWapper;

public class ServiceUtils {
	
	public static String toMD5(String m){
	       String algorithm = "MD5";
	       try{
	    	   MessageDigest digest= MessageDigest.getInstance(algorithm);
	           digest.update(m.getBytes());
	           return byte2hex(digest.digest());
	        }catch (Exception e){
	        	e.printStackTrace();
	            return null;
	        }
	    }
	    
	    private static String byte2hex(byte[] b){
	        String result="";
	        String hex="";
	        for(int n = 0;n < b.length;n++){                                       
	            hex=(java.lang.Integer.toHexString(b[n] & 0XFF));
	            if (hex.length()==1) result=result+"0"+hex;
	            else result=result+hex;
	        }
	        return result.toLowerCase();
	     }
	    
	    public static String getCurrentTermNo(){
	    	Calendar date =Calendar.getInstance();
	    	int currYear =date.get(Calendar.YEAR);
	    	String strYear=String.valueOf(currYear);
	    	int month=date.get(Calendar.MONTH);
	    	return month<8?(strYear+"1"):(strYear+"0");
	    }
	    
	    public static String termNo2TermName(String termNo){
	    	int int_termNo=Integer.valueOf(termNo);
	    	int termYear =int_termNo/10;
	    	if(termYear==0) return "2013-2014第一学期"	;
	    	int term =int_termNo%termYear;
	    	
	    	if(term==0){
	    		return String.valueOf(termYear)+"-"+String.valueOf(termYear+1)+"第一学期";		
	    	}
    		return String.valueOf(termYear)+"-"+String.valueOf(termYear+1)+"第二学期";		

	    }
	    
	    public static String weekNumToChinese(int week){
	    	String weeks[] ={" ","一","二","三","四","五","六","七"};
	    	if(week>0&&week<=7){
	    		return weeks[week];
	    	}
	    	return "";
	    }
	    
	    public static int getNumOfWeek(String text) {
			if (text == null)
				return -1;

			String chineseOfWeek = text.substring(0, 1);
			if (chineseOfWeek.equals("一"))
				return 1;
			if (chineseOfWeek.equals("二"))
				return 2;
			if (chineseOfWeek.equals("三"))
				return 3;
			if (chineseOfWeek.equals("四"))
				return 4;
			if (chineseOfWeek.equals("五"))
				return 5;
			if (chineseOfWeek.equals("六"))
				return 6;
			if (chineseOfWeek.equals("日"))
				return 7;

			return -1;

		}
	    
		public static Map<String, String> conflictDetectionCourse(List<MySchedule> schedules, List<MyCourseScheme> schemes) {
			Map<String, String> result =new HashMap<String,String>();
			//将schedules 转换为 三维数据
			TermCourse[][][] courseTable =new TermCourse[20][8][13];
			for(MySchedule schedule:schedules){
				if(schedule.getState()==1){
					TermCourse termCourse =schedule.getTermCourse();
					List<Integer> periods=termCourse.getPeriodList();
					List<Integer> classSections =termCourse.getSectionList();
					Integer week=termCourse.getClassWeek();
					for(Integer period :periods){
						for(Integer classSection :classSections){
							courseTable[period][week][classSection]=termCourse;
						}
					}
				}
			}
			//检测冲突
			for(MyCourseScheme scheme :schemes){
				List<TimeWapper> times=scheme.getCourseTimeList();
			//	List<Integer> classSections =scheme.getSectionList();
			//	Integer week=scheme.getClassWeek();
				boolean flag=false;
				for(TimeWapper timeWapper:times){
					List<Integer> periodList =timeWapper.getPeriodList();
					List<Integer> sectionList =timeWapper.getSectionList();
					Integer week =timeWapper.getWeek();
					for(Integer period :periodList){
						for(Integer classSection :sectionList){
							if(courseTable[period][week][classSection]!=null){
								result.put(String.valueOf(scheme.getCourse().getNo()), "《"+courseTable[period][week][classSection].getCourse().getName()+"》");
								flag=true;
								break;
							}
						}
						if(flag){
							break;
						}
					}
					if(flag){
						break;
					}
				}
			}
			
			return result;
		}
		
}
