package com.zhku.module.fetchData;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.RandomStringUtils;

import zhku.jackcan.webCrawler.BasicNameValuePair;
import zhku.jackcan.webCrawler.FetchUrl;
import zhku.jackcan.webCrawler.FetchUrlFactory;
import zhku.jackcan.webCrawler.NameValuePair;
import zhku.jackcan.webCrawler.exception.FetchTimeoutException;

import com.zhku.bean.Academy;
import com.zhku.bean.BaseClass;
import com.zhku.bean.CampusArea;
import com.zhku.bean.Classroom;
import com.zhku.bean.Course;
import com.zhku.bean.CourseListPage;
import com.zhku.bean.Major;
import com.zhku.bean.MyPublicCourse;
import com.zhku.bean.Organization;
import com.zhku.bean.PublicCourseOption;
import com.zhku.bean.SchoolBuilding;
import com.zhku.bean.Teacher;
import com.zhku.bean.Term;
import com.zhku.module.analysis.AnalysiserFactroy;
import com.zhku.module.analysis.IAnalysiser;
import com.zhku.module.analysis.impl.CourseAnalysiser;
import com.zhku.module.analysis.impl.MyPublicCourseAnalysiser;
import com.zhku.module.analysis.impl.PublicCourseOptiontAnalysiser;
import com.zhku.module.analysis.impl.PublicCoursePageAnalysiser;
import com.zhku.module.analysis.impl.SelectorValueAnalysiser;
import com.zhku.module.analysis.impl.StudentPageAnalysiser;
import com.zhku.module.analysis.impl.SubmitCourseResultAnalysiser;
import com.zhku.module.analysis.impl.TermClassCoursePageAnalysiser;
import com.zhku.module.analysis.impl.TermCoursePageAnalysiser;
import com.zhku.module.analysis.impl.TermTeacherCoursePageAnalysiser;
import com.zhku.module.analysis.impl.ZXCoursePageAnalysiser;
import com.zhku.module.bo.KeyValue;
import com.zhku.module.fetchData.bo.CoursePageBo;
import com.zhku.module.fetchData.bo.FetchRequest;
import com.zhku.module.fetchData.bo.PublicCoursePageBo;
import com.zhku.module.fetchData.bo.StudentPage;
import com.zhku.module.fetchData.bo.TermClassCoursePageBo;
import com.zhku.module.fetchData.bo.TermCoursePageBo;
import com.zhku.module.fetchData.bo.TermTeacherCoursePageBo;
import com.zhku.module.utils.HTMLUtil;
import com.zhku.utils.ServiceUtils;
import com.zhku.utils.WebConfigUtils;
import com.zhku.web.Constants;
import com.zhku.web.Constants.URL;
/**
 * 封装 抓取类
 * @author JackCan
 *
 */
public class FetchHelper {
	
	private Fetcher fetcher =null;
	public FetchHelper(){
		fetcher = new Fetcher();
		fetcher.setCharset("GBK");
	}
	
	/**
	 * 获取已选课程，的信息，对应功能模块(选课统计)
	 * @param cookie
	 * @return
	 * @throws FetchTimeoutException 
	 */
	public List<MyPublicCourse> fetchMyPublicCourses(String cookie,Integer uid,List<Term> terms) throws FetchTimeoutException{
		//先抓取个人选课表(不准确 ，还是 还 课程表吧)，再 抓取 成绩 最后合并
		String myCourseUrl = URL.USER_COURSE_TABLE_RETRIVAL_URL.getUrl();
		FetchUrl fetchUrl = FetchUrlFactory.getFetchurl();
		fetchUrl.setProxy("localhost",8888);
		fetchUrl.setCookies(cookie);
		fetchUrl.setDecodeCharset("GBK");
		StringBuffer sb = new StringBuffer();
		fetchUrl.get(URL.USER_COURSE_TABLE_RETRIVAL_REFERE_URL.getUrl());
		Map<String, String> hiddenformMap = HTMLUtil.getHiddenFormMap(fetchUrl.getResponseBody());
		String schoolcode =WebConfigUtils.getValue("kingoCode");
		for(Term term : terms ){
			fetchUrl.setHeader("Referer", URL.USER_COURSE_TABLE_RETRIVAL_REFERE_URL.getUrl());
			// 生成 post参数
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("Sel_XNXQ", term.getNo()));
			params.add(new BasicNameValuePair("rad", "1"));
			params.add(new BasicNameValuePair("px", "0"));
			for(String key :hiddenformMap.keySet()){
				if(hiddenformMap.get(key)!=null&&!"".equals(hiddenformMap.get(key)))
					params.add(new BasicNameValuePair(key, hiddenformMap.get(key)));
			}
			String randomStr = "ZU89QS2Ks2BDGVw";
			params.add(new BasicNameValuePair("hidsjyzm", ServiceUtils.toMD5(schoolcode+term.getNo()+randomStr).toUpperCase()));
			fetchUrl.setPostData(params);
			fetchUrl.post(myCourseUrl+"?m=ZU89QS2Ks2BDGVw");
			sb.append(fetchUrl.getResponseBody());
			sb.append("~~course~~");
		}
		sb.append("~~score~~");
		String scoreUrl = URL.SCORE_RETRIVAL_URL.getUrl();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Sel_XNXQ", "0"));
		params.add(new BasicNameValuePair("SJ",	"0"));
		params.add(new BasicNameValuePair("zfx_flag", "0"));
		params.add(new BasicNameValuePair("zxf","0"));
		fetchUrl.setPostData(params);
		fetchUrl.setHeader("Referer", URL.SCORE_RETRIVAL_REFERER_URL.getUrl());
		fetchUrl.post(scoreUrl);
		sb.append(fetchUrl.getResponseBody());
		MyPublicCourseAnalysiser analysiser = new MyPublicCourseAnalysiser().setUid(uid).setTermList(terms);
//		System.out.println(sb.toString()); //TODO
		return analysiser.doAnalysis(sb.toString());
	}
	
	/**
	 * 提交选修课
	 * @param cookies
	 * @param formParams
	 * @return
	 * @throws FetchTimeoutException
	 */
	public String submitCourse(String cookies, List<NameValuePair> formParams) throws FetchTimeoutException{
		String url = URL.PUBLIC_COURSE_ZX_SUBMIT_URL.getUrl();
		String referer = URL.PUBLIC_COURSE_ZX_SUBMIT_REFERE_URL.getUrl();
		FetchUrl fetchUrl = FetchUrlFactory.getFetchurl();
		fetchUrl.setDecodeCharset("GBK");
		fetchUrl.setCookies(cookies);
		fetchUrl.setHeader("Referer", referer);
		fetchUrl.setPostData(formParams);
		String html = fetchUrl.post(url,5);
		return new SubmitCourseResultAnalysiser().doAnalysis(html);
	}
	/**
	 * 获取 正选列表
	 * @param cookies
	 * @param majorNo
	 * @return
	 * @throws FetchTimeoutException
	 */
	public CourseListPage fetchZXCourseListPage(String cookies,String majorNo) throws FetchTimeoutException{
		String url = URL.PUBLIC_COURSE_ZX_PAGE_FETCH_URL.getUrl();
		String referer = URL.PUBLIC_COURSE_ZX_PAGE_FETCH_REFERE_URL.getUrl();
		FetchUrl fetchUrl = FetchUrlFactory.getFetchurl();
		fetchUrl.setDecodeCharset("GBK");
		fetchUrl.setCookies(cookies);
		fetchUrl.setHeader("Referer", referer);
		Map<String,String> postData = new HashMap<String, String>();
		postData.put(Constants.HTML_ELEMENT_NAME.SELECT_NAME_ZX_COURSE_CAMPUS.getValue(), majorNo);
		postData.put(Constants.HTML_ELEMENT_NAME.SELECT_NAME_ZX_COURSE_TYPE.getValue(),"2");
		postData.put("kc","");
		fetchUrl.setPostData(postData);
		String html = fetchUrl.post(url,2);
		ZXCoursePageAnalysiser analysiser = new ZXCoursePageAnalysiser().setMajorNo(majorNo);
		return analysiser.doAnalysis(html);
	}
	
	/**
	 * 抓取时间选项数据
	 * @throws FetchTimeoutException 
	 */
	
	public PublicCourseOption fetchPublicCourseOption(String cookies,CampusArea campus,String termNo,String cNo) throws FetchTimeoutException{
		String url = new StringBuffer().append(URL.PUBLIC_COURSE_OPTION_FETCH_URL.getUrl()).append(termNo.substring(0, 4)).append("%7C")
				.append(termNo.substring(4, 5)).append("%7C").append(cNo).append("%7C0%7C2%7C%7C%7C1").toString();
		FetchUrl fetchUrl = FetchUrlFactory.getFetchurl();
		fetchUrl.setDecodeCharset("GBK");
		fetchUrl.setCookies(cookies);
		String html = fetchUrl.get(url,2);
		PublicCourseOptiontAnalysiser analysiser= new PublicCourseOptiontAnalysiser().setCampusArea(campus).setcNo(cNo).setTermNo(termNo);
		return analysiser.doAnalysis(html);
	}
	/**
	 * 即时抓取时间选项数据
	 * @throws FetchTimeoutException 
	 */
	
	public PublicCourseOption fetchPublicCourseOptionSyn(String cookies,CampusArea campus,String termNo,String value,String skbjval) throws FetchTimeoutException{
		String cNo = value.split("|")[2];
		try {
			value = URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
		}
		String url = new StringBuffer().append(URL.PUBLIC_COURSE_OPTION_FETCH_URL.getUrl()).append(value).append("&skbjval=").append(skbjval).toString();
		FetchUrl fetchUrl = FetchUrlFactory.getFetchurl();
		fetchUrl.setDecodeCharset("GBK");
		fetchUrl.setCookies(cookies);
		String html = fetchUrl.get(url,2);
		PublicCourseOptiontAnalysiser analysiser= new PublicCourseOptiontAnalysiser().setCampusArea(campus).setcNo(cNo).setTermNo(termNo);
		return analysiser.doAnalysis(html);
	}
	
	/**
	 * 抓取指定学期 ，指定 校区的 公选课课程
	 * @param term
	 * @param campus
	 * @return
	 * @throws FetchTimeoutException 
	 */
	public List<PublicCoursePageBo> fetchPublicCoursePage(Term term,CampusArea campus) throws FetchTimeoutException{
		String url = URL.PUBLIC_COURSE_FETCH_RUL.getUrl();
		String referer = URL.PUBLIC_COURSE_FETCH_REFERER_RUL.getUrl();
		FetchRequest request = new FetchRequest();
		request.setRequestUrl(url);
		request.setReferer(referer);
		Map<String,String> postData = new HashMap<String, String>();
		postData.put(Constants.HTML_ELEMENT_NAME.SELECT_NAME_TERMYEAR.getValue(), term.getNo());
		postData.put(Constants.HTML_ELEMENT_NAME.SELECT_NAME_PUBLIC_COURSE_CAMPUS.getValue(),campus.getId()+"");
		postData.put("rxflag", "1");
		request.setPostParamsMap(postData);
		request.setReDoTimes(5);
		IAnalysiser<PublicCoursePageBo> publicCoursePageAnalysiser = new PublicCoursePageAnalysiser().setCampus(campus).setTerm(term);
		return fetcher.doFetch(null, request, publicCoursePageAnalysiser);
	}
	
	/**
	 * 抓取 学生个人信息
	 * @param cookies
	 * @return
	 * @throws FetchTimeoutException
	 */
	public StudentPage fetchStudentPage(String cookies,String sno) throws FetchTimeoutException{
		String url = Constants.URL.STUDENT_INFO_FETCH_URL.getUrl();
		String fixUrl = Constants.URL.STUDENT_INFO_FETCH_URL_FIX.getUrl();
		FetchUrl  fetchUrl = FetchUrlFactory.getFetchurl();
		fetchUrl.setDecodeCharset("GBK");
		fetchUrl.setCookies(cookies);
		String html = fetchUrl.get(url, Constants.MAX_FETCHURL_COUNT);
		String fixHtml = fetchUrl.get(fixUrl, Constants.MAX_FETCHURL_COUNT);
		html=new StringBuffer().append(html).append(">@<").append(fixHtml).toString();
		return new StudentPageAnalysiser().setSno(sno).doAnalysis(html);
	}
	
	/**
	 * 抓取单个教师的课表页面
	 * @param term
	 * @param teacher
	 * @return
	 * @throws FetchTimeoutException
	 */
	public List<TermTeacherCoursePageBo> fetchTeacherCoursePageBos(Term term ,Teacher teacher) throws FetchTimeoutException{
		String url = URL.TEACHER_COURSE_FETCH_RUL.getUrl();
		String referer = URL.TEACHER_COURSE_FETCH_REFERER_RUL.getUrl();
		FetchRequest request = new FetchRequest();
		request.setRequestUrl(url);
		request.setReferer(referer);
		Map<String,String> postData = new HashMap<String, String>();
		postData.put(Constants.HTML_ELEMENT_NAME.SELECT_NAME_TERMYEAR.getValue(), term.getNo());
		postData.put(Constants.HTML_ELEMENT_NAME.SELECT_NAME_TEACHER.getValue(), teacher.getNo());
		postData.put("type", "2");
		request.setPostParamsMap(postData);
		request.setReDoTimes(5);
		IAnalysiser termTeacherCoursePageAnalysiser = new TermTeacherCoursePageAnalysiser().setTeacher(teacher).setTerm(term);
		return fetcher.doFetch(null, request, termTeacherCoursePageAnalysiser);
	}
	/**
	 * 单线程 抓取  教师课表页面
	 * @param classes
	 * @return
	 * @throws FetchTimeoutException 
	 */
	public List<TermTeacherCoursePageBo> fetchTeacherCoursePageBos(Term term,List<Teacher> teachers) throws FetchTimeoutException{
		List<TermTeacherCoursePageBo> list = new ArrayList<TermTeacherCoursePageBo>();
		for(Teacher teacher:teachers){
			list.addAll(fetchTeacherCoursePageBos(term, teacher));
		}
		return list;
	}
	/**
	 * 多线程抓取 教师课表页面
	 * @param term
	 * @param classes
	 * @return
	 */
	public List<TermTeacherCoursePageBo> fetchTeacherCoursePageBosMutilThread(Term term,List<Teacher> teachers,int threadNum){
		ExecutorService ex = Executors.newFixedThreadPool(threadNum);
		Vector<TermTeacherCoursePageBo> coursePages = new Vector<TermTeacherCoursePageBo>();
		class Task implements Callable<List<TermTeacherCoursePageBo>>{
			  private Teacher teacher;
			  private Term term;
	           public Task(Teacher teacher,Term term){
	        	   this.teacher=teacher;
	        	   this.term =term;
	           }
	           //抛出异常并可以拥有返回值
			@Override
			public List<TermTeacherCoursePageBo> call() throws Exception {
				return new FetchHelper().fetchTeacherCoursePageBos(term, teacher);
			}

	    }
		List<Future<List<TermTeacherCoursePageBo>>> result =new ArrayList<Future<List<TermTeacherCoursePageBo>>>();
		for(Teacher teacher:teachers){
			Future<List<TermTeacherCoursePageBo>> f = ex.submit(new Task(teacher, term));
			result.add(f);
		}
		for(Future<List<TermTeacherCoursePageBo>> f:result){
			try {
				coursePages.addAll(f.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		return new ArrayList<TermTeacherCoursePageBo>(coursePages);
	}
	/**
	 * 抓取单个班级的班级课表页面
	 * @param term
	 * @param baseClass
	 * @return
	 * @throws FetchTimeoutException
	 */
	public List<TermClassCoursePageBo> fetchClassCoursePageBos(Term term,BaseClass baseClass) throws FetchTimeoutException{
		String url = URL.CLASS_COURSE_FETCH_RUL.getUrl();
		String referer = URL.CLASS_COURSE_FETCH_REFERER_RUL.getUrl();
		FetchRequest request = new FetchRequest();
		request.setRequestUrl(url);
		request.setReferer(referer);
		Map<String,String> postData = new HashMap<String, String>();
		postData.put(Constants.HTML_ELEMENT_NAME.SELECT_NAME_TERMYEAR.getValue(), term.getNo());
		postData.put(Constants.HTML_ELEMENT_NAME.SELECT_NAME_XZ_BJ.getValue(), baseClass.getNo());
		postData.put("type", "2");
		postData.put("chkrxkc", "1");
		request.setPostParamsMap(postData);
		request.setReDoTimes(5);
		IAnalysiser termClassCoursePageAnalysiser = new TermClassCoursePageAnalysiser().setTerm(term).setBaseClass(baseClass);
		return fetcher.doFetch(null, request, termClassCoursePageAnalysiser);
	}
	
	/**
	 * 单线程 抓取 班级课表页面
	 * @param classes
	 * @return
	 * @throws FetchTimeoutException 
	 */
	public List<TermClassCoursePageBo> fetchClassCoursePageBos(Term term,List<BaseClass> classes) throws FetchTimeoutException{
		List<TermClassCoursePageBo> list = new ArrayList<TermClassCoursePageBo>();
		for(BaseClass baseClass:classes){
			list.addAll(fetchClassCoursePageBos(term, baseClass));
		}
		return list;
	}
	/**
	 * 多线程抓取 班级课表页面
	 * @param term
	 * @param classes
	 * @return
	 */
	public List<TermClassCoursePageBo> fetchClassCoursePageBosMutilThread(Term term,List<BaseClass> classes,int threadNum){
		ExecutorService ex = Executors.newFixedThreadPool(threadNum);
		Vector<TermClassCoursePageBo> coursePages = new Vector<TermClassCoursePageBo>();
		class Task implements Callable<List<TermClassCoursePageBo>>{
			  private BaseClass baseClass;
			  private Term term;
	           public Task(BaseClass baseClass,Term term){
	        	   this.baseClass=baseClass;
	        	   this.term =term;
	           }
	           //抛出异常并可以拥有返回值
			@Override
			public List<TermClassCoursePageBo> call() throws Exception {
				return new FetchHelper().fetchClassCoursePageBos(term, baseClass);
			}

	    }
		List<Future<List<TermClassCoursePageBo>>> result =new ArrayList<Future<List<TermClassCoursePageBo>>>();
		for(BaseClass baseClass:classes){
			Future<List<TermClassCoursePageBo>> f = ex.submit(new Task(baseClass, term));
			result.add(f);
		}
		for(Future<List<TermClassCoursePageBo>> f:result){
			try {
				coursePages.addAll(f.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		return new ArrayList<TermClassCoursePageBo>(coursePages);
	}
	/**
	 * 抓取单个课程的的课表页面
	 * @param term
	 * @param baseClass
	 * @return
	 * @throws FetchTimeoutException
	 */
	public List<TermCoursePageBo> fetchTermCoursePageBos(Term term,Course course) throws FetchTimeoutException{
		String url = URL.TERM_COURSE_FETCH_RUL.getUrl();
		String referer = URL.TERM_COURSE_FETCH_REFERER_RUL.getUrl();
		FetchRequest request = new FetchRequest();
		request.setRequestUrl(url);
		request.setReferer(referer);
		Map<String,String> postData = new HashMap<String, String>();
		postData.put(Constants.HTML_ELEMENT_NAME.SELECT_NAME_TERMYEAR.getValue(), term.getNo());
		postData.put(Constants.HTML_ELEMENT_NAME.SELECT_NAME_COURSE.getValue(), course.getNo());
		postData.put("gs", "2");
		request.setPostParamsMap(postData);
		request.setReDoTimes(5);
		IAnalysiser<TermCoursePageBo> termCoursePageAnalysiser = new TermCoursePageAnalysiser().setTerm(term).setCourse(course);
		return fetcher.doFetch(null, request, termCoursePageAnalysiser);
	}
	
	/**
	 * 多线程抓取 课程课表页面
	 * @param term
	 * @param classes
	 * @return
	 */
	public List<TermCoursePageBo> fetchTermCoursePageBosMutilThread(Term term,List<Course> courses,int threadNum){
		ExecutorService ex = Executors.newFixedThreadPool(threadNum);
		Vector<TermCoursePageBo> coursePages = new Vector<TermCoursePageBo>();
		class Task implements Callable<List<TermCoursePageBo>>{
			  private Course course;
			  private Term term;
	           public Task(Course course,Term term){
	        	   this.course=course;
	        	   this.term =term;
	           }
	           //抛出异常并可以拥有返回值
			@Override
			public List<TermCoursePageBo> call() throws Exception {
				return new FetchHelper().fetchTermCoursePageBos(term, course);
			}
	    }
		List<Future<List<TermCoursePageBo>>> result =new ArrayList<Future<List<TermCoursePageBo>>>();
		for(Course course:courses){
			Future<List<TermCoursePageBo>> f = ex.submit(new Task(course, term));
			result.add(f);
		}
		for(Future<List<TermCoursePageBo>> f:result){
			try {
				coursePages.addAll(f.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		return new ArrayList<TermCoursePageBo>(coursePages);
	}
	/**
	 * 抓取指定教学楼的 教室信息
	 * @param schoolBuilding
	 * @return
	 * @throws FetchTimeoutException
	 */
	public List<Classroom> fetchClassrooms(SchoolBuilding schoolBuilding) throws FetchTimeoutException{
		String url = Constants.URL.CLASSROOM_FETCH_RUL.getUrl()+"&id="+schoolBuilding.getNo();
		FetchRequest request = new FetchRequest();
		request.setRequestUrl(url);
		request.setReDoTimes(5);
		IAnalysiser analysiser  = new SelectorValueAnalysiser().setSelecterName(Constants.HTML_ELEMENT_NAME.SELECT_NAME_CLASSROOM.getValue());
		List<KeyValue> list = fetcher.doFetch(null, request, analysiser);
		//解析到bean
		List<Classroom> classrooms = new ArrayList<Classroom>();
		Classroom classroom = null;
		for(KeyValue keyValue:list){
			if(keyValue.getKey().contains("Nothing")) continue;
			classroom=new Classroom();
			classroom.setName(keyValue.getValue());
			classroom.setNo(keyValue.getKey());
			classroom.setSchoolBuilding(schoolBuilding);
			classrooms.add(classroom);
		}
		return classrooms;
	}
	
	/**
	 * 根据校区抓取教学楼信息
	 * @param campusArea
	 * @return
	 * @throws FetchTimeoutException
	 */
	public List<SchoolBuilding> fetchSchoolBuildings(CampusArea campusArea) throws FetchTimeoutException{
		String url = Constants.URL.BUILDING_FETCH_RUL.getUrl()+"&id="+campusArea.getId();
		FetchRequest request = new FetchRequest();
		request.setRequestUrl(url);
		request.setReDoTimes(5);
		IAnalysiser analysiser  = new SelectorValueAnalysiser().setSelecterName(Constants.HTML_ELEMENT_NAME.SELECT_NAME_BUILDING.getValue());
		List<KeyValue> list = fetcher.doFetch(null, request, analysiser);
		//解析到bean
		List<SchoolBuilding> schoolBuildings = new ArrayList<SchoolBuilding>();
		SchoolBuilding schoolBuilding = null;
		for(KeyValue keyValue:list){
			if(keyValue.getKey().contains("Nothing")) continue;
			schoolBuilding=new SchoolBuilding();
			schoolBuilding.setName(keyValue.getValue());
			schoolBuilding.setNo(keyValue.getKey());
			schoolBuilding.setCampusArea(campusArea);
			schoolBuildings.add(schoolBuilding);
		}
		return schoolBuildings;
	}
	
	/**
	 * 获取校区列表
	 * @return
	 * @throws FetchTimeoutException 
	 */
	public List<CampusArea> fetchCampusAreas() throws FetchTimeoutException{
		String url = Constants.URL.CAMPUS_BASEICAL_FETCH_RUL.getUrl();
		FetchRequest request = new FetchRequest();
		request.setRequestUrl(url);
		request.setReDoTimes(5);
		IAnalysiser analysiser  = new SelectorValueAnalysiser().setSelecterName(Constants.HTML_ELEMENT_NAME.SELECT_NAME_CAMPUS.getValue());
		List<KeyValue> list = fetcher.doFetch(null, request, analysiser);
		//解析到bean
		List<CampusArea> campuses = new ArrayList<CampusArea>();
		CampusArea campusArea = null;
		for(KeyValue keyValue:list){
			campusArea=new CampusArea();
			campusArea.setId(Integer.valueOf(keyValue.getKey()));
			campusArea.setName(keyValue.getValue());
			campuses.add(campusArea);
		}
		return campuses;
	}
	
	/**
	 * 抓取 课程信息 的一些基础信息，如 课程号，课程 名称，课程名称代号
	 * @param termNo
	 * @return
	 * @throws FetchTimeoutException 
	 */
	public List<Course> fetchCourseBasicInfo(String termNo) throws FetchTimeoutException{
		String url = Constants.URL.COURSE_BASEICAL_FETCH_RUL.getUrl() + "?xnxq=" + termNo;
		FetchRequest request = new FetchRequest();
		request.setRequestUrl(url);
		request.setReDoTimes(5);
		IAnalysiser analysiser = AnalysiserFactroy.getCourseBasicalInfoAnalysiser();
		return fetcher.doFetch(termNo, request, analysiser);
	}
	
	/**
	 * 根据 课程号进行课程内容的更多数据的抓取填充。
	 * @param basicCourse 包含课程号
	 * @param cookie	登录成功后的Cookie值
	 * @return
	 * @throws FetchTimeoutException 
	 */
	public CoursePageBo fetchCourseFullInfo(String cNo,String cookies) throws FetchTimeoutException{
		String url = Constants.URL.COURSE_FULL_FETCH_RUL.getUrl()+"?id="+cNo;
		FetchRequest request = new FetchRequest();
		request.setRequestUrl(url);
		request.setCookies(cookies);
		request.setReDoTimes(5);
		IAnalysiser analysiser   = new CourseAnalysiser().setcNo(cNo);
		Object result = fetcher.doFetch(null, request, analysiser).get(0);
		if(result==null) return null;
		return (CoursePageBo) result;
	}
	/**
	 * 提前指定学期的老师列表
	 * 
	 * @param termNo
	 * @return
	 * @throws FetchTimeoutException
	 */
	public List<Teacher> fetchTeacherData(String termNo) throws FetchTimeoutException {
		String url = Constants.URL.TEACHER_FETCH_RUL.getUrl() + "?xnxq=" + termNo;
		FetchRequest request = new FetchRequest();
		request.setRequestUrl(url);
		request.setReDoTimes(5);
		IAnalysiser analysiser = AnalysiserFactroy.getTeacherAnalysiser();
		return fetcher.doFetch(termNo, request, analysiser);
	}

	/**
	 * 提取系统页面中年级的选项列表，这个也可以不用，不过为了兼容性，最好还是牺牲那么一次的http请求去解析拿到列表
	 * 
	 * @return
	 * @throws FetchTimeoutException
	 */
	public List<KeyValue> fetchGradesData() throws FetchTimeoutException {
		String url = Constants.URL.ORGANIZATION_FETCH_RUL.getUrl();
		FetchRequest request = new FetchRequest();
		request.setRequestUrl(url);
		request.setReDoTimes(5);
		IAnalysiser analysiser = AnalysiserFactroy.getSelectorAnalysiser().setSelecterName(Constants.HTML_ELEMENT_NAME.SELECT_NAME_GREAD.getValue());
		return fetcher.doFetch(null, request, analysiser);
	}

	/**
	 * 提取学校的学院和一些组织机构，一般很少去更新这些信息
	 * 
	 * @return
	 * @throws FetchTimeoutException
	 */
	public List<Organization> fetchOrganizationData() throws FetchTimeoutException {
		String url = Constants.URL.ORGANIZATION_FETCH_RUL.getUrl();
		FetchRequest request = new FetchRequest();
		request.setRequestUrl(url);
		request.setReDoTimes(5);
		IAnalysiser analysiser = AnalysiserFactroy.getOrganizationAnalysiser();
		return fetcher.doFetch(null, request, analysiser);
	}

	/**
	 * 提前指定学院的 所有专业信息，这个也比较少去更新它，除非学校添加新专业的时候要更新
	 * 
	 * @param acadmey
	 * @return
	 * @throws FetchTimeoutException
	 */
	public List<Major> fetchMajorData(Academy acadmey) throws FetchTimeoutException {
		String url = Constants.URL.MAJOR_FETCH_RUL.getUrl() + "?yx=" + acadmey.getNo();
		FetchRequest request = new FetchRequest();
		request.setRequestUrl(url);
		request.setReDoTimes(5);
		IAnalysiser analysiser = AnalysiserFactroy.getMajorAnalysiser().setAcademyNo(acadmey.getNo());
		List list=fetcher.doFetch(null, request, analysiser);
		acadmey.setMajorList(list);
		return list;
	}
	/**
	 * 获取专业列表 ，分类项目分别放在 入参的  acadmey中 ，返回值是 所有的major
	 * @param academys
	 * @return
	 * @throws FetchTimeoutException
	 */
	public List<Major> fetchMajorData(List<Academy> academys) throws FetchTimeoutException {
		List<Major> majors = new ArrayList<Major>();
		for(Academy academy:academys){
			if(academy.validate())
			majors.addAll(fetchMajorData(academy));
		}
		return majors;
	}
	/**
	 * 获取指定 年级 和 专业 的 班级信息
	 * @param major
	 * @param grades
	 * @return
	 * @throws FetchTimeoutException
	 */
	public List<BaseClass> fetchClassData(Major major, List<KeyValue> grades) throws FetchTimeoutException {
		List<BaseClass> list = new ArrayList<BaseClass>();
		for (KeyValue grade : grades) {
			list.addAll(fetchClassData(major, grade.getValue()));
		}
		return list;
	}

	public List<BaseClass> fetchClassData(Major major, String grade) throws FetchTimeoutException {
		String url = Constants.URL.BASECLASS_FETCH_RUL.getUrl() + "?zy=" + major.getNo() + "&nj=" + grade;
		FetchRequest request = new FetchRequest();
		request.setRequestUrl(url);
		request.setReDoTimes(5);
		IAnalysiser analysiser = AnalysiserFactroy.getBaseClassAnalysiser().setMajor(major).setGrade(grade);
		return fetcher.doFetch(null, request, analysiser);
	}
}
