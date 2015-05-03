package com.zhku.service.db;

import java.util.List;
import java.util.Set;

import com.zhku.bean.Term;
import com.zhku.exception.ObjectExistsException;

public interface ITermService {
	public void addTerm(Term term) throws ObjectExistsException;
	public void deleteTerm(Term term);
	public void updateTerm(Term term);
	public Term getTermById(int id);
	public Term getTermByNo(String no);
	public Term getTermByFullName(String name);
	public Term getCurrentTerm();
	public List<Term> getTermsByYear(String year);
	public List<Term> getTerms();
	public List<Term> getStudentTerms(String sNo);
	/**
	 * 获取显示的你学期
	 * @return
	 */
	public List<Term> getAvailabelTerms();
	/**
	 * 获取在校的年级
	 * @return
	 */
	public Set<String> getSchoolGrades();
}
