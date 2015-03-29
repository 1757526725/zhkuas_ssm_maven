package com.zhku.web;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	public static String loginPostUrl = "http://jw.zhku.edu.cn/jwmis/_data/index_LOGIN.aspx";
	public final static String CURRENT_TERMNO = "20140";
	public final static int MAX_FETCHURL_COUNT = 10; // 最大重复抓取的次数。

	public enum URL {
		TEACHER_FETCH_RUL("http://jw.zhku.edu.cn/jwmis/ZNPK/Private/List_JS.aspx", "老师课表查询页面的URL,直接GET请求，后面带上js和xnxq"), 
		ORGANIZATION_FETCH_RUL("http://jw.zhku.edu.cn/jwmis/JXSJ/FB_XQJXSJAP.aspx", "学院信息查询页面的URL,GET请求"),
		MAJOR_FETCH_RUL("http://jw.zhku.edu.cn/jwmis/XSXJ/Private/List_NJYXZY.aspx","专业信息查询页面的URL,GET请求,后面带上yx学院no"), 
		BASECLASS_FETCH_RUL("http://jw.zhku.edu.cn/jwmis/XSXJ/Private/List_ZYBJ.aspx", "班级信息查询页的URL,GET请求,后面带上yx专业no"), 
		COURSE_BASEICAL_FETCH_RUL("http://jw.zhku.edu.cn/jwmis/ZNPK/Private/List_XNXQKC.aspx","课程列表请求地址，后面带上xnxq，代表学期"),
		COURSE_FULL_FETCH_RUL("http://jw.zhku.edu.cn/jwmis/JXJH/INFO_KC.aspx","课程详细信息请求地址，后面带上id，代表课程号,需要登录权限"),
		BUILDING_FETCH_RUL("http://jw.zhku.edu.cn/jwmis/ZNPK/Private/List_JXL.aspx?w=150","教学楼请求地址，后面带上id，代表校区号"),
		CLASSROOM_FETCH_RUL("http://jw.zhku.edu.cn/jwmis/ZNPK/Private/List_ROOM.aspx?w=150","教室列表请求地址，后面带上id，代表教学楼号"),
		CAMPUS_BASEICAL_FETCH_RUL("http://jw.zhku.edu.cn/jwmis/ZNPK/KBFB_RoomSel.aspx","校区页面的抓取URL"),
		CLASS_COURSE_FETCH_REFERER_RUL("http://jw.zhku.edu.cn/jwmis/ZNPK/KBFB_ClassSel.aspx","班级课表请求地址的REFERER"),
		CLASS_COURSE_FETCH_RUL("http://jw.zhku.edu.cn/jwmis/ZNPK/KBFB_ClassSel_rpt.aspx","班级课表请求地址"), 
		TERM_COURSE_FETCH_RUL("http://jw.zhku.edu.cn/jwmis/ZNPK/KBFB_LessonSel_rpt.aspx","课程课表请求地址"), 
		TERM_COURSE_FETCH_REFERER_RUL("http://jw.zhku.edu.cn/jwmis/ZNPK/KBFB_LessonSel.aspx","课程课表请求地址的REFERER"), 
		TEACHER_COURSE_FETCH_RUL("http://jw.zhku.edu.cn/jwmis/ZNPK/TeacherKBFB_rpt.aspx","教师课表请求地址"),
		TEACHER_COURSE_FETCH_REFERER_RUL("http://jw.zhku.edu.cn/jwmis/ZNPK/TeacherKBFB.aspx","教师课表请求地址的REFERER"), 
		STUDENT_INFO_FETCH_URL("http://jw.zhku.edu.cn/jwmis/xsxj/Stu_MyInfo_RPT.aspx","学生信息请求地址"),
		STUDENT_INFO_FETCH_URL_FIX("http://jw.zhku.edu.cn/jwmis/xsxj/Stu_xszcxs_rpt.aspx","学生信息请求地址2"), 
		PUBLIC_COURSE_FETCH_RUL("http://jw.zhku.edu.cn/jwmis/ZNPK/KBFB_RXKBSel_rpt.aspx","公选课课表请求地址"),
		PUBLIC_COURSE_FETCH_REFERER_RUL("http://jw.zhku.edu.cn/jwmis/ZNPK/KBFB_RXKBSel.aspx","公选课课表请求地址的REFERER"),
		PUBLIC_COURSE_OPTION_FETCH_URL("http://jw.zhku.edu.cn/jwmis/wsxk/stu_xszx_chooseskbj.aspx?lx=ZX&id=","公选课选项请求地址的REFERER")
		,PUBLIC_COURSE_ZX_PAGE_FETCH_URL("http://jw.zhku.edu.cn/jwmis/wsxk/stu_xszx_rpt.aspx","正选课检索的地址")
		,PUBLIC_COURSE_ZX_PAGE_FETCH_REFERE_URL("http://jw.zhku.edu.cn/jwmis/wsxk/stu_xszx.aspx","正选课检索的REFERER")
		,PUBLIC_COURSE_ZX_SUBMIT_URL("http://jw.zhku.edu.cn/jwmis/wsxk/stu_xszx_rpt.aspx?func=1","正选课提交的地址")
		,PUBLIC_COURSE_ZX_SUBMIT_REFERE_URL("http://jw.zhku.edu.cn/jwmis/wsxk/stu_xszx.aspx","正选课提交的地址REFERER")
		,SCORE_RETRIVAL_URL("http://jw.zhku.edu.cn/jwmis/xscj/Stu_MyScore_rpt.aspx","查询成绩的URL")
		,SCORE_RETRIVAL_REFERER_URL("http://jw.zhku.edu.cn/jwmis/xscj/Stu_MyScore.aspx","查询成绩的URL"), 
		USER_ALL_COURSE_RETRIVAL_URL("http://jw.zhku.edu.cn/jwmis/wsxk/stu_zxjg_rxyl.aspx","查询入学以来所有课程的URL"),
		USER_COURSE_TABLE_RETRIVAL_URL("http://jw.zhku.edu.cn/jwmis/znpk/Pri_StuSel_rpt.aspx","查询指定学期的课表 POST 请求"),
		USER_COURSE_TABLE_RETRIVAL_REFERE_URL("http://jw.zhku.edu.cn/jwmis/znpk/Pri_StuSel.aspx","查询指定学期的课表 POST 请求"),
		;
		private String url;
		private String description;

		private URL(String url, String description) {
			this.url = url;
			this.description = description;
		}

		public String getUrl() {
			return url;
		}

		public String getDescription() {
			return description;
		}
	}

	public enum HTML_ELEMENT_NAME {
		SELECT_NAME_TEACHER("Sel_JS", "教师列表Select的name值"), 
		SELECT_NAME_ORGANIZATION("Sel_YX", "学院列表Select的name值"), 
		SELECT_NAME_MAJOR("Sel_ZY", "专业Select的name值"), 
		SELECT_NAME_BASECLASS("Sel_BJ", "班级Select的name值"),
		SELECT_NAME_GREAD("Sel_NJ", "年级列表的Select的name值"),
		SELECT_NAME_COURSE("Sel_KC", "课程列表Select的name值"),
		SELECT_NAME_CAMPUS("Sel_XQ", "校区列表Select的name值"),
		SELECT_NAME_BUILDING("Sel_JXL", "教学楼列表Select的name值"),	
		SELECT_NAME_CLASSROOM("Sel_ROOM", "教学楼列表Select的name值"),	
		SELECT_NAME_TERMYEAR("Sel_XNXQ", "学期Select的name值"),	
		SELECT_NAME_XZ_BJ("Sel_XZBJ", "行政班级Select的name值"),	
		SELECT_NAME_PUBLIC_COURSE_CAMPUS("Sel_XQXX", "公选课表的校区Select的name值"),	
		SELECT_NAME_ZX_COURSE_CAMPUS("sel_xq", "正选课表的校区Select的name值"),	
		SELECT_NAME_ZX_COURSE_TYPE("sel_lx", "正选选课表的校区Select的name值"),	
		//登录表单
		LOGIN_FORM_NAME_USERNAME("txt_asmcdefsddsd","登录表单的用户名name"),
		LOGIN_FORM_NAME_PASSWORD("txt_pewerwedsdfsdff","登录表单的密码name"),
		LOGIN_FORM_NAME_VAILDATECODE("txt_sdertfgsadscxcadsads","登录表单的验证码name"), 
		LOGIN_FORM_NAME_HIDDEN_USERNAME_PASSWORD("dsdsdsdsdxcxdfgfg","用户名密码加密表单name"),
		LOGIN_FORM_NAME_HIDDEN_VAILDATECODE("fgfggfdgtyuuyyuuckjg","验证码加密表单name");
		
		private String value;
		private String description;
		private HTML_ELEMENT_NAME(String value, String description) {
			this.value = value;
			this.description = description;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

	}

	public enum Accont {
		BAIYUN_ACCOUNT("201321314315", "123321"), HAIZHU_ACCOUNT("201111314424", "522415181");
		private String sNo;
		private String password;

		Accont(String sNo, String password) {
			this.sNo = sNo;
			this.password = password;
		}

		public String getsNo() {
			return sNo;
		}

		public String getPassword() {
			return password;
		}

	}

	public enum PageSize {
		MY_COMMENT(8), REPLYME_COMMENT(8),ADMIN_MAJOR(12), ADMIN_COURSE(12), ADMIN_CLASS(12),
		ADMIN_SCHOOL_USER_LIST(12), ADMIN_CLASSROOM(12), COMMON_SIZE(12);
		private int size;

		PageSize(int size) {
			this.size = size;
		}

		public int getSize() {
			return size;
		}
	}

	public enum CommentState {
		UNREAD(0), HADREAD(1), DELETED(2);
		private int state;

		CommentState(int state) {
			this.state = state;
		}

		public int getState() {
			return state;
		}

	}

	public enum Avater {

		AVATER_30("http://bcs.duapp.com/zhkuas/avater/30/"), AVATER_80("http://bcs.duapp.com/zhkuas/avater/80/"), AVATER_180("http://bcs.duapp.com/zhkuas/avater/180/"), AVATER_DEFAULT(
				"http://bcs.duapp.com/zhkuas/avater%2Fdefault_avt.jpg");
		private String url;

		Avater(String url) {
			this.url = url;
		}

		@Override
		public String toString() {
			return url;
		}
	}

	public enum Campus {
		BAIYU("3"), HAIZHU("1");
		private String text;

		Campus(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}

		public int getId(){
			return Integer.valueOf(this.text);
		}
		public static String getChineseName(String id) {
			if (id.equals("1"))
				return "海珠校区";
			return "白云校园";
		}
	}

	public enum CourseType {
		RENWENG(1), KEJI(2), WAIYU(3), YISHU(4);

		private int code;

		CourseType(int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}

		public static String getChieseName(String id) {
			if (id.toString().equals("1"))
				return "人文社科类";
			if (id.toString().equals("2"))
				return "科学技术类";
			if (id.toString().equals("3"))
				return "外语类";
			if (id.toString().equals("4"))
				return "艺术体育类";
			return "全部";
		}

	}

	public enum ActionResultCode {
		USEREXITS("00001"), SUCCESS("00000"), CONNECTION_ERROR("00002"), PASSWORD_ERROR("00003"), BIND_SUCCESS("00004"), USERNAMEORPASSWORDWORING("00005"), NOTLOGIN("unlogined"), UNKNOWNEXCEPTION(
				"-00001"), CODE_ERROR("-00002");
		private String code;

		ActionResultCode(String code) {
			this.code = code;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return code;
		}

	}

	public enum ActionCode {

		CLASS_LIST("classList"), TEACHER_LIST("teacherList"), CLASS_COURSE_TABLE("classCourseTable");
		private String code;

		ActionCode(String code) {
			this.code = code;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return code;
		}
	}

	public enum RemarkItem {
		REMAR_GOOD(1), REMARK_BAD(2);

		private int item;

		RemarkItem(int item) {
			this.item = item;
		}

		public int getItem() {
			return item;
		}

	}

	public enum BaiduBCS {
		API_KEY("ExpG1bqbzKjb8MUl0kicP3AW"), SECRET_KEY("xlUS5lEqBbIKGPGg7xWXBsPowsGvjQ1h");
		private String key;

		BaiduBCS(String key) {
			this.key = key;
		}

		public String getKey() {
			return key;
		}
	}

	public enum CachePrefix {

		SINAUSER("sina_user");
		private String prefix;

		CachePrefix(String prefix) {
			this.prefix = prefix;
		}

		@Override
		public String toString() {
			return prefix + "_";
		}
	}

	public enum SessionKey {

		VCODE_COOKIE("sys_cookie"), MYSCHEMES("mySchemes"), 
		STUDENT_COOKIE("cookie_"), CURRENT_SNO("current_sNo"),
		ADMIN_USER("admin_user"), FETCH_ORGANIZATION_DATA("fetch_organization"), 
		FETCH_MAJOR("academy_majors_"),FETCH_MAJOR_DATA_LIST("fecth_temp_majors"),
		FETCH_CAMPUS("fetch_campuses"),
		FETCH_BUILDING("fetch_campus_building_"),FETCH_BUILDING_DATA_LIST("fetch_temp_buildings"),
		FETCH_CLASSROOM("fetch_building_classroom_"),FETCH_CLASSROOM_DATA_LIST("fetch_temp_classroom"),
		FETCH_COURSE_BASICAL("fetch_course_basical_"),FETCH_COURSE_BASICAL_DATA_SET("fetch_temp_coursebasical"),
		PROGRESS("progress"), LOGIN_REFERER("login_referer"), LOGIN_USER("loginUser"), FETCH_COURSE_FULL_DATA_LIST("fetch_temp_courseFull"), 
		STUDENT_NO("studentNo");
		;
		private String key;

		SessionKey(String key) {
			this.key = key;
		}

		@Override
		public String toString() {
			return key;
		}
	}

	public enum Info {

		NO_DATA("no data", "10001", "没有获取到数据"), UPDATE_SUCCESS("update success", "10002", "更新成功");

		private String info;
		private String info_code;
		private String info_description;

		private Info(String info, String info_code, String info_description) {
			this.info = info;
			this.info_code = info_code;
			this.info_description = info_description;
		}

		public String toJsonString() {
			StringBuilder builder = new StringBuilder();
			builder.append("{");
			builder.append("\"info\":");
			builder.append("\"" + info + "\",");
			builder.append("\"info_code\":");
			builder.append("\"" + info_code + "\",");
			builder.append("\"info_description\":");
			builder.append("\"" + info_description + "\",");
			builder.append("}");
			return builder.toString();
		}

	}

	/**
	 * 0：代表网络方面的错误 1: 用户信息提交方面的错误 ...  .
	 * 
	 * @author JackCan_Liao
	 * 
	 */
	public enum Error {
		// 用户信息相关错误
		USERNAME_OR_PASSWORD_ERROR("username or password error", "-10001", "用户名或者密码错误"), 
		VALIDATECODE_ERROR("validate code error", "-10002", "验证码错误"), 
		USERNAME_NULL_ERROR("userName could not be null", "-10003", "用户名不能为空"), 
		PASSWORD_NULL_ERROR("password could not be null", "-10004", "密码不能为空"), 
		VALIDATECODE_NULL_ERROR("validate code could not be null", "-10005", "验证码不能为空"), 
		ACADEMY_SYSTEM_LOGIN_OUT_TIME("academy system login out time", "-10006", "教务网登录信息过期"), 
		ACADEMY_SYSTEM_UNBIND("academy system unBind", "-10007", "未绑定教务网"), 
		DB_DATA_EXIST("the data inserting  aleary exits", "-10008", "数据库插入数据已经存在"),
		REQUEST_PARAMS_ERROR("request params are incorrect", "-10009", "请求参数错误"),
		GET_VALIDATECODE_ERROR("get validate code error", "-10010", "验证码获取错误"), 
		FETCH_ACCOUNT_NULL("there is not account for fetching access", "-10011", "没有教务网账户用于抓取数据，请至少在后台配置好一个账户！"), 
		SNO_NULL_ERROR("sno could not be null", "-100012", "学号不能为空"), 
		PERMISSIONS_DO_NOT_ALLOW("Permissions do not allow", "-100013", "权限不足"), 
		HAVE_NOT_LOGIN_SYSTEM("this operation need authnation", "-100014", "未登录系统，请登陆后操作"), 
		// 网络连接相关错误
		CONNECTION_ERROR("connection error", "-00001", "连接错误"), 
		BIND_ACCOUT_FAIL("bind your college account fail", "-00004", "绑定教务网帐号失败"),
		// 本系统相关错误
		LOGIN_OUT_TIME("login out time", "-20001", "登录信息已经过期，请重新登录"),
		// 程序错误
		FORM_DATE_FORMATE_ERROR("Formation of form Date is mistake", "-30001", "表单的时间格式错误。"), 
		DELETE_ERROR("delete failed", "-30002", "删除操作失败。"),
		// 表单错误信息
		TERM_NAME_NULL_ERROR("term name is null", "-40000", "学期名称为空。"), 
		TERM_NO_NULL_ERROR("term no is null", "-40001", "学期号为空。"),
		//抓取错误
		FETCH_ORGANIZATION_FAIL("There was a problem while fetching orginzation data ","-50000","抓取学院信息出错！"), 
		FETCH_ORGANIZATION_EMPTY("orginzation data is empty","-50001","机构列表是空的，可能是你没有进行第一步的抓取！"),
		FETCH_ORGANIZATION_PARAM_WRONG("param is wrong","-50002","参数列表错误！"),
		ACADEMY_LIST_IS_EMPTY("academy data is empty","-50003","学院列表为空，请先更新学院数据!"),
		DATA_LIST_IS_EMPTY("fetch data is empty","-50004","抓取的数据是空的!"),
		PARAMETER_ERROR("parameter error","-50004","参数错误，或者缺少必要参数!"), 
		FETCH_TIME_OUT("fetch data timeout !","-50005","抓取超时!"),
		FETCH_ACCOUNT_USERNAME_PASSWORD_ERROR("account in config is unavailable","-50006","内置账户登录失败，可能是密码错误"),
		FETCH_NO_ACCESS_ERROR("havn't login in academy system","-50007","为获取到教务网的权限，请重新登录！"), 
		NONE_TASK("There is not fetching task","-50008","没有启动任务~"), 
		FAILURE("excute failure","-50009","操作失败，请重试！"), 
		HAVE_NOT_ENSURE_SCHEDULE("have not ensure schedule","-50010","未确认个人课表！"), 
		FETCH_NO_DATA("have not data respone","-50011","没有获取到数据！"), 
		IS_NOT_TIME("now is not the official time","-50012","现在非正选时间"),
		SUBMIT_TIME_OUT("submit time out","-50013","提交失败!!");
		private String error;
		private String error_code;
		private String error_description;
		Error(String error, String error_code, String error_description) {
			this.error = error;
			this.error_code = error_code;
			this.error_description = error_description;
		}
		public String toJsonString() {
			StringBuilder builder = new StringBuilder();
			builder.append("{");
			builder.append("\"error\":");
			builder.append("\"" + error + "\",");
			builder.append("\"error_code\":");
			builder.append("\"" + error_code + "\",");
			builder.append("\"error_description\":");
			builder.append("\"" + error_description + "\"");
			builder.append("}");
			return builder.toString();
		}

		public Map<String, Object> toMap() {
			Map<String, Object> error = new HashMap<String, Object>();
			error.put("error", this.error);
			error.put("error_code", this.error_code);
			error.put("error_description", this.error_description);
			return error;
		}
	}

}
