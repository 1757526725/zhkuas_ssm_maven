package com.zhku.dao;

import java.util.List;

import com.zhku.bean.Term;

public interface TermMapper {
	public void addTerm(Term term);
	public void deleteTerm(Term term);
	public void updateTerm(Term term);
	public Term getTermById(int id);
	public Term getTermByNo(String no);
	public Term getTermByFullName(String name);
	public List<Term> getTermsByYear(String year);
	public List<Term> getTerms();
	public void deleteTerms(String[] arrayTermNos);
	public List<Term> getAvailabelTerms();
	public Term getCurrentTerm();
}
			