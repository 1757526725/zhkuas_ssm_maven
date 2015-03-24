package com.zhku.service.db;

import java.util.List;

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
	public List<Term> getAvailabelTerms();
}
