package com.zhku.service.db.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhku.bean.Term;
import com.zhku.dao.TermMapper;
import com.zhku.exception.ObjectExistsException;
import com.zhku.service.db.ITermService;
@Service("termService")
public class TermService implements ITermService {
	
	@Autowired
	private TermMapper termMapper;
	@Override
	public void addTerm(Term term) throws ObjectExistsException {
		Term temp_term =termMapper.getTermByNo(term.getNo());
		if(temp_term==null){
			termMapper.addTerm(term);
		}else{
			throw new ObjectExistsException();
		}
	}

	@Override
	public void deleteTerm(Term term) {
		termMapper.deleteTerm(term);
	}

	@Override
	public void updateTerm(Term term) {
		termMapper.updateTerm(term);
	}

	@Override
	public Term getTermById(int id) {
		return termMapper.getTermById(id);
	}

	@Override
	public Term getTermByNo(String no) {
		return termMapper.getTermByNo(no);
	}

	@Override
	public Term getTermByFullName(String name) {
		return termMapper.getTermByFullName(name);
	}

	@Override
	public List<Term> getTermsByYear(String year) {
		return termMapper.getTermsByYear(year);
	}

	@Override
	public List<Term> getTerms() {
		return termMapper.getTerms();
	}

	public TermMapper getTermMapper() {
		return termMapper;
	}

	public void setTermMapper(TermMapper termMapper) {
		this.termMapper = termMapper;
	}

	public void deleteTerms(String[] arrayTermNos) {
		this.termMapper.deleteTerms(arrayTermNos);
	}

	@Override
	public List<Term> getAvailabelTerms() {
		return termMapper.getAvailabelTerms();
	}

	@Override
	public Term getCurrentTerm() {
		return termMapper.getCurrentTerm();
	}

	@Override
	public List<Term> getStudentTerms(String sNo) {
		List<Term> terms = new ArrayList<Term>();
		String inYear = sNo.substring(0, 4);
		Term currentTerm = getCurrentTerm();
		String termNo = currentTerm.getNo();
		String currentYear = termNo.substring(0, 4);
		String currentTermNum = termNo.substring(4, 5);
		for(int i = Integer.valueOf(inYear);(i<Integer.valueOf(currentYear))&&(i-Integer.valueOf(inYear)<5);i++){
			Term  term = new Term();
			term.setNo(i+""+0);
			terms.add(term);
			term = new Term();
			term.setNo(i+""+1);
			terms.add(term);
		}
		if((Integer.valueOf(currentYear)-Integer.valueOf(inYear)<5)&&currentTermNum.equals("1")){
			Term  term = new Term();
			term.setNo(currentYear+""+0);
			terms.add(term);
			terms.add(currentTerm);
		}
		return terms;
	}

	@Override
	public Set<String> getSchoolGrades() {
		Set<String> grades = new HashSet<String>();
		List<Term> terms = this.termMapper.getTerms();
		for(Term term :terms){
			String termNo = term.getNo();
			String grade = termNo.substring(0, termNo.length()-1);
			grades.add(grade);
		}
		return grades;
	}

}
