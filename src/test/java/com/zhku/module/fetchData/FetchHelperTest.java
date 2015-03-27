package com.zhku.module.fetchData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import zhku.jackcan.webCrawler.exception.FetchTimeoutException;

import com.alibaba.fastjson.JSON;
import com.zhku.bean.Academy;
import com.zhku.bean.Account;
import com.zhku.bean.BaseClass;
import com.zhku.bean.CampusArea;
import com.zhku.bean.ClassCourse;
import com.zhku.bean.Classroom;
import com.zhku.bean.Course;
import com.zhku.bean.CourseListPage;
import com.zhku.bean.Major;
import com.zhku.bean.MyPublicCourse;
import com.zhku.bean.Organization;
import com.zhku.bean.PublicCourse;
import com.zhku.bean.PublicCourseOption;
import com.zhku.bean.SchoolBuilding;
import com.zhku.bean.Teacher;
import com.zhku.bean.Term;
import com.zhku.bean.TermCourse;
import com.zhku.exception.ConnectionException;
import com.zhku.exception.LoginOutTimeException;
import com.zhku.exception.ObjectExistsException;
import com.zhku.exception.SnoOrPasswordIncorrectException;
import com.zhku.exception.ValidateCodeIncorrectException;
import com.zhku.module.bo.KeyValue;
import com.zhku.module.fetchData.bo.CoursePageBo;
import com.zhku.module.fetchData.bo.PublicCoursePageBo;
import com.zhku.module.fetchData.bo.StudentPage;
import com.zhku.module.fetchData.bo.TermClassCoursePageBo;
import com.zhku.module.fetchData.bo.TermTeacherCoursePageBo;
import com.zhku.module.utils.BOUtil;
import com.zhku.module.utils.HTMLUtil;
import com.zhku.service.db.IAccountService;
import com.zhku.service.db.IBaseClassService;
import com.zhku.service.db.IClassCourseService;
import com.zhku.service.db.IClassroomService;
import com.zhku.service.db.ICourseListPageService;
import com.zhku.service.db.ICourseService;
import com.zhku.service.db.IPublicCourseOptionService;
import com.zhku.service.db.IPublicCourseService;
import com.zhku.service.db.ITeacherService;
import com.zhku.service.db.ITermCourseService;
import com.zhku.service.db.ITermService;
import com.zhku.service.network.IWebLogin;
import com.zhku.service.network.impl.WebLogin;
import com.zhku.utils.ServiceUtils;
import com.zhku.utils.ValidateCodeInputHelper;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class FetchHelperTest {
	@Autowired
	private ITermService termService;

	@Autowired
	private IAccountService accountService;
	@Autowired
	private ITeacherService teacherService;
	@Autowired
	private IBaseClassService baseClassService;
	@Autowired
	private ITermCourseService termCourseService;
	@Autowired
	private IClassCourseService classCourseService;
	@Autowired
	private ICourseService courseService;
	@Autowired
	private IClassroomService classroomService;
	@Autowired
	private IPublicCourseService publicCourseService;
	@Autowired
	private ICourseListPageService courseListPageService;
	@Autowired
	private IPublicCourseOptionService publicCourseOptionService;
	public IAccountService getAccountService() {
		return accountService;
	}
	
	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}
	@Test
	public void testFetchPublicCourse() throws FetchTimeoutException{
		CampusArea campus = new CampusArea();
		campus.setId(3);
		Term term = new Term();
		term.setNo("20141");
		FetchHelper fetchHelper = new FetchHelper();
		List<PublicCoursePageBo> list = fetchHelper.fetchPublicCoursePage(term, campus);
		List<PublicCourse> publicCourses = publicCoursePageBos2PublicCourses(list);
		System.out.println(JSON.toJSONString(publicCourses));
		
		for(PublicCourse publicCourse:publicCourses){
			TermCourse termCourse = publicCourse.getTermCourse();
			TermCourse dbTermCourse = termCourseService.findTermCourseByConditions(termCourse);
			if(dbTermCourse!=null){
				termCourse = dbTermCourse;
			}else{
				termCourseService.addTermCourse(termCourse);
			}
			publicCourse.setTermCourse(termCourse);
			try {
				publicCourseService.addPublicCourse(publicCourse);
			} catch (ObjectExistsException e) {
				e.printStackTrace();
			}
		}
	}
	private List<PublicCourse> publicCoursePageBos2PublicCourses(List<PublicCoursePageBo> list) {
		//获取到所有的教室
		Map<String, String> classRoomMap = genClassroomMap(classroomService.getClassrooms());
		List<PublicCourse> publicCourses = new ArrayList<PublicCourse>();
		for(PublicCoursePageBo publicCoursePage :list){
			TermCourse termCourse  = new TermCourse();
			Classroom classroom = new Classroom();
			classroom.setName(publicCoursePage.getClassRoom());
			classroom.setNo(classRoomMap.get(publicCoursePage.getClassRoom()));
			termCourse.setClassroom(classroom);
			termCourse.setClassSection(HTMLUtil.getSections(publicCoursePage.getDayAndSection()));
			termCourse.setClassWeek(HTMLUtil.convertDay(publicCoursePage.getDayAndSection()));
			Course course = courseService.getCourseByCnameNo(HTMLUtil.cutNameNo(publicCoursePage.getCourse()));
			if(course==null) throw new RuntimeException("没有找到"+publicCoursePage.getCourse());
			termCourse.setCourse(course);
			termCourse.setCourseClassNo(publicCoursePage.getCourseClassNo());
			termCourse.setPeriods(publicCoursePage.getPeriods());
			termCourse.setStudentNum(Integer.valueOf(publicCoursePage.getStudentNum()));
			// 填充 Teacher
			Teacher teacher = null;
			if(publicCoursePage.getTeacher()==null||publicCoursePage.getTeacher().trim().equals("")){
				teacher = new Teacher();
				teacher.setNo("0000000");
			}else {
				List<Teacher> teachers = teacherService.getTeacherByTname(publicCoursePage.getTeacher());
				if(teachers.size()==1){
					teacher = teachers.get(0);
				}else if(teachers==null||teachers.size()==0){
					throw new RuntimeException("没找到"+publicCoursePage.getTeacher()+"请先更新老师信息数据");
				}else{
					List<TermTeacherCoursePageBo> teacherCoursePageBos  = null;
					try {
						teacherCoursePageBos = new FetchHelper().fetchTeacherCoursePageBos(publicCoursePage.getTerm(), teachers);
					} catch (FetchTimeoutException e) {
						e.printStackTrace();
					}
					for(TermTeacherCoursePageBo teacherCoursePageBo :teacherCoursePageBos){
						if(teacherCoursePageBo.getCourse().contains(HTMLUtil.cutName(publicCoursePage.getCourse()))){
							teacher = teacherCoursePageBo.getTeacher();
							break;
						}
					}
				}
			}
			//end
			termCourse.setTeacher(teacher );
			termCourse.setTerm(publicCoursePage.getTerm());
			PublicCourse publicCourse = new PublicCourse();
			publicCourse.setTermCourse(termCourse);
			publicCourse.setCampusId(publicCoursePage.getCampusArea().getId());
			publicCourses.add(publicCourse );
		}
		return publicCourses;
	}

	@Test
	public void crawlClassCourse(){ //classRoom　no is null
		String termNo="20141";
		Term term = new Term();
		term.setNo(termNo);
		//获取到 班级课表
		List<BaseClass> classes = baseClassService.getBaseClasses();
		List<Teacher> teachers = teacherService.getTeachers();
		List<TermClassCoursePageBo> classcourselist = new FetchHelper().fetchClassCoursePageBosMutilThread(term, classes, 100);
		List<TermTeacherCoursePageBo> teacherCourseList = new FetchHelper().fetchTeacherCoursePageBosMutilThread(term, teachers, 100);
//		System.out.println(list.size());
		List<TermCourse> list  = classCoursePageBo2TermCourse(classcourselist);
//		System.out.println(JSON.toJSONString(list));
		List<TermCourse> termClassCourseList = termCourse2TermClassCourse(list);
		// key -- : [courseNameNo]courseName+"-"+teacherName+"-"+classCourseNo
		Map<String,Teacher> teacherMap = genTeacherCourseMap(teacherCourseList);
		List<Course> courseList = courseService.getCourses();
		Map<String,String> courseNoWithNameNoMap = genNoByNameNo(courseList);
		List<Classroom> classroomList = classroomService.getClassrooms();
		Map<String,String> classroomNameWithNo = genClassroomMap(classroomList);
		///根据教师名字转换成  教师号。。
		for(TermCourse termCourse:termClassCourseList){
			String key = new StringBuffer().append("[").append(termCourse.getCourse().getNameNo()).append("]").append(termCourse.getCourse().getName().trim()).append("-")
					.append(termCourse.getTeacher().getName().trim()).append("-")
					.append(termCourse.getCourseClassNo().trim()).toString();
			Teacher teacher = teacherMap.get(key);
			if(teacher == null){
				teacher = new Teacher();
				teacher.setNo("0000000");
			}
			String courseNo =courseNoWithNameNoMap.get(termCourse.getCourse().getNameNo());
			if(courseNo==null){
				throw new RuntimeException("没有找到对应的课程【"+termCourse.getCourse().getNameNo()+"】,请先更新课程信息	");
			}
			String classroomNo=classroomNameWithNo.get(termCourse.getClassroom().getName());
			termCourse.getClassroom().setNo(classroomNo);
			termCourse.getCourse().setNo(courseNo);
			termCourse.setTeacher(teacher);
		}
		//写入 数据库
		TermCourse dbTermCourse= null;
		for(TermCourse termCourse:termClassCourseList){
			dbTermCourse = termCourseService.findTermCourseByConditions(termCourse);
			if(dbTermCourse==null){
				termCourseService.addTermCourse(termCourse);
				dbTermCourse = termCourse;
			}
			
			for(BaseClass baseClass:termCourse.getClassList()){
				ClassCourse dbClassCourse = classCourseService.findClassCourse(baseClass, dbTermCourse);
				if(dbClassCourse==null){
					ClassCourse classCourse = new ClassCourse();
					classCourse.setBaseClass(baseClass);
					classCourse.setTermCourseId(dbTermCourse.getId());
					classCourseService.addClassCourse(classCourse);
				}
			}
		}
	}
	
	
	private Map<String, String> genClassroomMap(List<Classroom> classroomList) {
		Map<String,String> roomMap= new HashMap<String,String>();
		for(Classroom classroom:classroomList){
			roomMap.put(classroom.getName(), classroom.getNo());
		}
		return roomMap;
	}

	private Map<String, String> genNoByNameNo(List<Course> courseList) {
		Map<String, String> noByNameNo= new HashMap<String ,String>();
		for(Course course:courseList){
			noByNameNo.put(course.getNameNo(), course.getNo());
		}
		return noByNameNo;
	}

	private Map<String, Teacher> genTeacherCourseMap(List<TermTeacherCoursePageBo> teacherCourseList) {
		Map<String,Teacher> teacherMap = new HashMap<String, Teacher>();
		for(TermTeacherCoursePageBo teacherCourse:teacherCourseList){
			String key = new StringBuffer().append(teacherCourse.getCourse().trim()).append("-")
			.append(teacherCourse.getTeacher().getName().trim()).append("-")
			.append(teacherCourse.getCourseClassNo().trim()).toString();
			teacherMap.put(key, teacherCourse.getTeacher());
		}
		return teacherMap;
	}

	private List<TermCourse> termCourse2TermClassCourse(List<TermCourse> list) {
		Map<String,TermCourse> map = new HashMap<String,TermCourse>();
		//将 同一门 课程 的 班级合并起来
		for(TermCourse termCourse :list){
			String nameNo = termCourse.getCourse().getNameNo();
			String classCourseNo=termCourse.getCourseClassNo();
			String teacher =termCourse.getTeacher().getName();
			String period = termCourse.getPeriods();
			String day = termCourse.getClassWeek()+"";
			String sections = termCourse.getClassSection();
			String classroom= termCourse.getClassroom().getName();
			String str =ServiceUtils.toMD5( new StringBuffer().append(nameNo).append(classCourseNo).append(teacher)
					.append(period).append(day).append(sections).append(classroom).toString());
			
			TermCourse termCourseTemp = map.get(str);
			if(termCourseTemp==null){
				map.put(str, termCourse);
			}else{
				termCourseTemp.getClassList().addAll(termCourse.getClassList());
			}
		}
		return new ArrayList<TermCourse>(map.values());
	}

	private List<TermCourse> classCoursePageBo2TermCourse(List<TermClassCoursePageBo> classcourselist){
		List<TermCourse> classTermCourseList = new ArrayList<TermCourse>();
		TermCourse termCourse = null;
		for(TermClassCoursePageBo tcpbo:classcourselist){
			termCourse = new TermCourse();
			List<BaseClass> baseCLassList=new ArrayList<BaseClass>();
			baseCLassList.add(tcpbo.getBaseClass());
			termCourse.setClassList(baseCLassList);
			Course course = new Course();
			course.setName(HTMLUtil.cutName(tcpbo.getCourse()));
			course.setNameNo(HTMLUtil.cutNameNo(tcpbo.getCourse()));
			Classroom classroom = new Classroom();
			classroom.setName(tcpbo.getClassroom());
			termCourse.setClassroom(classroom);
			termCourse.setCourse(course );
			String classSection = HTMLUtil.getSections(tcpbo.getDayAndsection());
			termCourse.setClassSection(classSection );
			int classWeek = HTMLUtil.convertDay(tcpbo.getDayAndsection());
			termCourse.setClassWeek(classWeek);
			termCourse.setCourseClassNo(tcpbo.getCourseClassNo());
			termCourse.setPeriods(tcpbo.getPeriods());
			if(tcpbo.getDayAndsection().contains("单")){
				termCourse.setPeriods(termCourse.getPeriods()+"单");
			}else if(tcpbo.getDayAndsection().contains("双")){
				termCourse.setPeriods(termCourse.getPeriods()+"双");
			}
			termCourse.setStudentNum(Integer.valueOf(tcpbo.getStudentNum()));
			Teacher teacher = new Teacher();
			teacher.setName(tcpbo.getTeacher());
			termCourse.setTeacher(teacher );
			termCourse.setTerm(tcpbo.getTerm());
			classTermCourseList.add(termCourse);
		}
		
		return classTermCourseList;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Test
	public void fetchTeacherCoursePageBos(){
		Term term = new Term();
		term.setNo("20140");
		Teacher teacher = new Teacher();
		teacher.setNo("0001407");
		try {
			List<TermTeacherCoursePageBo> list = new FetchHelper().fetchTeacherCoursePageBos(term, teacher);
			System.out.println(JSON.toJSONString(list));
		} catch (FetchTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testMultiFetchTermTeacherCoursePage(){
		Term term = new Term();
		term.setNo("20140");
		List<Teacher> teachers = teacherService.getTeachers();
		List<TermTeacherCoursePageBo> list = new FetchHelper().fetchTeacherCoursePageBosMutilThread(term, teachers, 100);
		System.out.println(list.size());
	}
	@Test
	public void testMultiFetchTermClassCoursePage(){
		Term term = new Term();
		term.setNo("20140");
		List<BaseClass> classes = baseClassService.getBaseClasses();
		List<TermClassCoursePageBo> list = new FetchHelper().fetchClassCoursePageBosMutilThread(term, classes, 100);
		for(TermClassCoursePageBo tcc:list){
			String nameNo = HTMLUtil.cutNameNo(tcc.getCourse());
			if(nameNo==null||nameNo.equals("")){
				System.out.println();
			}
		}
		System.out.println(list.size());
	}
	@Test
	public void testFetchTermClassCoursePage(){
		Term term = new Term();
		term.setNo("20140");
		List<BaseClass> classes = baseClassService.getBaseClasses();
		try {
			List<TermClassCoursePageBo> list = new FetchHelper().fetchClassCoursePageBos(term, classes);
			System.out.println(list.size());
			
		} catch (FetchTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		BaseClass baseClass = new BaseClass();
//		baseClass.setNo("2013020102");
//		try {
//			List<TermClassCoursePageBo> list = new FetchHelper().fetchClassCoursePageBos(term, baseClass);
//			System.out.println(JSON.toJSONString(list));
//		} catch (FetchTimeoutException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	@Test
	public void testFectStudentInfo () throws FetchTimeoutException, LoginOutTimeException, ConnectionException, SnoOrPasswordIncorrectException, ValidateCodeIncorrectException{
		List<Account> studentAccounts = accountService.getAccountsByCampusId(1);
		Account studentAccount = studentAccounts.get(0);
		IWebLogin webLogin = new WebLogin();
		String vCode = ValidateCodeInputHelper.showValidateCode(webLogin.getValidateCodeImg());
		String username =studentAccount.getUsername();
		String password = studentAccount.getPassword();
		String cookies = webLogin.login(username, password, vCode);
		FetchHelper fetchHelper = new FetchHelper();
		StudentPage studentPage = fetchHelper.fetchStudentPage(cookies,username);
		System.out.println(JSON.toJSONString(studentPage));
	}
	@Test
	public void testFetchCourse() throws FetchTimeoutException, LoginOutTimeException, ConnectionException, SnoOrPasswordIncorrectException, ValidateCodeIncorrectException{
		List<Account> studentAccounts = accountService.getAccountsByCampusId(1);
		Account studentAccount = studentAccounts.get(0);
		IWebLogin webLogin = new WebLogin();
		String vCode = ValidateCodeInputHelper.showValidateCode(webLogin.getValidateCodeImg());
		String username =studentAccount.getUsername();
		String password = studentAccount.getPassword();
		String cookies = webLogin.login(username, password, vCode);
		CoursePageBo coursePage = new FetchHelper().fetchCourseFullInfo("110570", cookies);
		System.out.println(JSON.toJSONString(coursePage));
	}
	@Test
	public void testFetchCourseListPage() throws FetchTimeoutException, LoginOutTimeException, ConnectionException, SnoOrPasswordIncorrectException, ValidateCodeIncorrectException{
		List<Account> studentAccounts = accountService.getAccountsByCampusId(3);
		Account studentAccount = studentAccounts.get(0);
		IWebLogin webLogin = new WebLogin();
		String vCode = ValidateCodeInputHelper.showValidateCode(webLogin.getValidateCodeImg());
		String username =studentAccount.getUsername();
		String password = studentAccount.getPassword();
		String cookies = webLogin.login(username, password, vCode);
		 CourseListPage coursePage = new FetchHelper().fetchZXCourseListPage(cookies, "3");
//		courseListPageService.addCourseListPage(coursePage);
		System.out.println(coursePage.getPageContent());
	}
	/**
	 * @throws FetchTimeoutException
	 * @throws LoginOutTimeException
	 * @throws ConnectionException
	 * @throws SnoOrPasswordIncorrectException
	 * @throws ValidateCodeIncorrectException
	 */
	@Test
	public void testFetchMyPublicCourse() throws FetchTimeoutException, LoginOutTimeException, ConnectionException, SnoOrPasswordIncorrectException, ValidateCodeIncorrectException{
		List<Account> studentAccounts = accountService.getAccountsByCampusId(1);
		Account studentAccount = studentAccounts.get(0);
		IWebLogin webLogin = new WebLogin();
		String vCode = ValidateCodeInputHelper.showValidateCode(webLogin.getValidateCodeImg());
		String username =studentAccount.getUsername();
		String password = studentAccount.getPassword();
		String cookies = webLogin.login(username, password, vCode);
		List<MyPublicCourse> myPublicCourses = new FetchHelper().fetchMyPublicCourses(cookies, 1,termService.getStudentTerms(username));
//		courseListPageService.addCourseListPage(coursePage);
		System.out.println(JSON.toJSONString(myPublicCourses));
	}
	@Test
	public void testFetchpPublicCoureOption() throws FetchTimeoutException, LoginOutTimeException, ConnectionException, SnoOrPasswordIncorrectException, ValidateCodeIncorrectException{
		int campusId = 3;
		String termNo = "20141";
		List<Account> studentAccounts = accountService.getAccountsByCampusId(campusId);
		Account studentAccount = studentAccounts.get(0);
		IWebLogin webLogin = new WebLogin();
		String vCode = ValidateCodeInputHelper.showValidateCode(webLogin.getValidateCodeImg());
		String username =studentAccount.getUsername();
		String password = studentAccount.getPassword();
		String cookies = webLogin.login(username, password, vCode);
		CampusArea campus = new CampusArea();
		campus.setId(campusId);
		List<PublicCourse> publiCourses = publicCourseService.getPublicCoursesByTermNoAndCampusId(termNo, campusId);
//		System.out.println(JSON.toJSON(publiCourses));
//		 CourseListPage coursePage = new FetchHelper().fetchZXCourseListPage(cookies, "3");
//		courseListPageService.addCourseListPage(coursePage);
//		System.out.println(coursePage.getPageContent());
		Set<String> cNos  = new HashSet<String>();
		for(PublicCourse pc:publiCourses){
			cNos.add(pc.getTermCourse().getCourse().getNo());
		}
		
		for(String cNo:cNos){
			upDatePublicCourseOption(cookies, termNo, campus, cNo);
		}
	}
	
	public void upDatePublicCourseOption(String cookies ,String termNo ,CampusArea campus, String cNo) throws FetchTimeoutException {
		PublicCourseOption publicCourseOption = new PublicCourseOption();
		publicCourseOption.setcNo(cNo);
		publicCourseOption.setCampus(campus);
		publicCourseOption.setTermNo(termNo);

		publicCourseOption = publicCourseOptionService.findPublicCourseOption(publicCourseOption);
		if (publicCourseOption == null) {
			publicCourseOption = new FetchHelper().fetchPublicCourseOption(cookies, campus, termNo, cNo);
			if (publicCourseOption != null) {
				publicCourseOptionService.addPublicCourseOption(publicCourseOption);
			}
		}
	}
	@Test
	public void testFetchClassroomData() throws FetchTimeoutException {
		SchoolBuilding schoolBuilding = new SchoolBuilding();
		schoolBuilding.setNo(302+"");
		List list =new FetchHelper().fetchClassrooms(schoolBuilding);
		System.out.println(JSON.toJSONString(list));
	}
	@Test
	public void testFetchBuildingData() throws FetchTimeoutException {
		CampusArea campusArea = new CampusArea();
		campusArea.setId(3);
		FetchHelper ft = new FetchHelper();
		List list =ft.fetchSchoolBuildings(campusArea );
		System.out.println(JSON.toJSONString(list));
	}
	@Test
	public void testFetchCampusData() throws FetchTimeoutException {
		List list =new FetchHelper().fetchCampusAreas();
		System.out.println(JSON.toJSONString(list));
	}
	
	
	@Test
	public void testFetchBasicalCourseData() throws FetchTimeoutException {
		List list =new FetchHelper().fetchCourseBasicInfo("20140");
		System.out.println(JSON.toJSONString(list));
	}
	@Test
	public void testFetchTeacherData() throws FetchTimeoutException {
		long begin = System.currentTimeMillis();
		String termNos[] = {"20110","20111","20120","20121","20130","20131","20140","20142"};
		for(String term:termNos){
			List<Teacher> list = new FetchHelper().fetchTeacherData(term);
			for(Teacher teacher:list){
				try {
					teacherService.addTeacher(teacher);
				} catch (ObjectExistsException e) {
					System.err.println(JSON.toJSONString(teacher));
				}
			}
		}
		long end =System.currentTimeMillis();
		
		System.out.println("用时:"+(end-begin)/1000+"秒");
		
	}
	@Test
	public void testFetchGradeData() throws FetchTimeoutException {
		List list =new FetchHelper().fetchGradesData();
		System.out.println(JSON.toJSONString(list));
	}
	@Test
	public void testFetchOrganizationData() throws FetchTimeoutException {
		List list =new FetchHelper().fetchOrganizationData();
		System.out.println(JSON.toJSONString(list));
	}

	@Test
	public void testFetchMajorData() throws FetchTimeoutException {
	
		List<Organization> olist =new FetchHelper().fetchOrganizationData();
		for(Organization o : olist){
			Academy acadmey=new Academy();
			acadmey.setNo(o.getNo());
			List list =new FetchHelper().fetchMajorData(acadmey);
			System.out.println(JSON.toJSONString(list));
		}
	
	}
	
	@Test 
	public void  testFetchBaseClassData() throws FetchTimeoutException{
		Major major=new Major();
		major.setNo("1101");
		String grade="2011";
		List list=new FetchHelper().fetchClassData(major, grade);
		System.out.println(JSON.toJSONString(list));
	}
	@Test 
	public void  testFetchAllBaseClassData() throws FetchTimeoutException{
		long begintime = System.currentTimeMillis();
		FetchHelper fetcher = new FetchHelper();
		//先获取 年级列表
		List<KeyValue> grades = fetcher.fetchGradesData();
		grades.add(new KeyValue("2014", "2014"));
		System.out.println(JSON.toJSONString(grades));
		List<Academy> acadmeys = BOUtil.academyfilter(fetcher.fetchOrganizationData());
		System.out.println(JSON.toJSONString(acadmeys));
		List<Major> majors = fetcher.fetchMajorData(acadmeys);
		List<BaseClass> list= new ArrayList<BaseClass>();
		for(Major major :majors){
			list .addAll(fetcher.fetchClassData(major, grades));
		}
		System.out.println("抓取结束，用时:"+(System.currentTimeMillis()-begintime)/1000.0+"秒");
		System.out.println(JSON.toJSONString(list));
	}

	public IPublicCourseOptionService getPublicCourseOptionService() {
		return publicCourseOptionService;
	}

	public void setPublicCourseOptionService(IPublicCourseOptionService publicCourseOptionService) {
		this.publicCourseOptionService = publicCourseOptionService;
	}

	public ITeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(ITeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public ITermCourseService getTermCourseService() {
		return termCourseService;
	}

	public void setTermCourseService(ITermCourseService termCourseService) {
		this.termCourseService = termCourseService;
	}

	public IClassCourseService getClassCourseService() {
		return classCourseService;
	}

	public void setClassCourseService(IClassCourseService classCourseService) {
		this.classCourseService = classCourseService;
	}

	public ICourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(ICourseService courseService) {
		this.courseService = courseService;
	}

	public IClassroomService getClassroomService() {
		return classroomService;
	}

	public void setClassroomService(IClassroomService classroomService) {
		this.classroomService = classroomService;
	}

	public IPublicCourseService getPublicCourseService() {
		return publicCourseService;
	}

	public void setPublicCourseService(IPublicCourseService publicCourseService) {
		this.publicCourseService = publicCourseService;
	}

	public ICourseListPageService getCourseListPageService() {
		return courseListPageService;
	}

	public void setCourseListPageService(ICourseListPageService courseListPageService) {
		this.courseListPageService = courseListPageService;
	}
	public ITermService getTermService() {
		return termService;
	}

	public void setTermService(ITermService termService) {
		this.termService = termService;
	}

}
