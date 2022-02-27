package com.xsz.job.service;

import com.xsz.common.service.IService;
import com.xsz.job.domain.JobLog;

import java.util.List;

public interface JobLogService extends IService<JobLog> {

	List<JobLog> findAllJobLogs(JobLog jobLog);

	void saveJobLog(JobLog log);
	
	void deleteBatch(String jobLogIds);
}
