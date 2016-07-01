package cn.zys.test;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.junit.Test;

public class HIstoryQueryTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	//查询历史活动
	@Test
	public void findHistoryActivities(){
		
		String processInstanceId = "";
		List<HistoricActivityInstance> list = processEngine.getHistoryService()
		             .createHistoricActivityInstanceQuery()
		             .processInstanceId(processInstanceId)
		             .orderByHistoricActivityInstanceStartTime().asc()
		             .list();
	}
}
