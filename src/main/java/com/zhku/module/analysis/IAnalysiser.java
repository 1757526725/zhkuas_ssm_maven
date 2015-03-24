package com.zhku.module.analysis;

import java.util.List;

public interface IAnalysiser<T> {
	public List<T> doAnalysis(String html);

}
