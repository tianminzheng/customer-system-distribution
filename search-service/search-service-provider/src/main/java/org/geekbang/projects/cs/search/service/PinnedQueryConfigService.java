package org.geekbang.projects.cs.search.service;

import org.geekbang.projects.cs.search.entity.PinnedQueryConfig;

public interface PinnedQueryConfigService {

	//用于前台查询
	PinnedQueryConfig findActivePinnedQueryConfigBySubjectWord(String subjectWord, Integer businessType);
}
