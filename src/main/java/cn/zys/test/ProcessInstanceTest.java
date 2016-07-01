package cn.zys.test;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class ProcessInstanceTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	//部署流程定义方式一
			@Test
			public void deploymentProcessDefinition(){
				 Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
				              .createDeployment()//创建一个部署对象
				              .name("hello")//添加部署名称
				              .addClasspathResource("diagrams/helloworld.bpmn")//从classpath的资源中加载，一次只能加载一个
				              .addClasspathResource("diagrams/helloworld.png")
				              .deploy();//完成部署
				 System.out.println(deployment.getId());
				 System.out.println(deployment.getName());
			}
		//启动	
			@Test
			public void startProcessInstance(){
				String processDefinitionKey = "helloworld";
				ProcessInstance processInstance = processEngine.getRuntimeService()//执行与流程实例执行对象相关的service
				             .startProcessInstanceByKey(processDefinitionKey);
				System.out.println("流程ID"+processInstance.getId());
			}
			
			//查询流程状态
			@Test
			public void idProcessed(){
				String processInstanceId="";
			ProcessInstance pi = processEngine.getRuntimeService()
				             .createProcessInstanceQuery()
				             .processInstanceId(processInstanceId)
				             .singleResult();
			
			if (pi == null) {
				System.out.println("流程结束");
			} else {
				System.out.println("流程没有结束");
			}
			}
			
			//查询历史任务
			@Test
			public void findHistoryTask(){
				String assignee = "张三";
				List<HistoricTaskInstance> list = processEngine.getHistoryService()
				             .createHistoricTaskInstanceQuery()
				             .taskAssignee(assignee)
				             .list();
				
				if(list != null && list.size() >0){
					for(HistoricTaskInstance hti:list){
						System.out.println(hti.getId()+" "+hti.getName());
					}
				}
			}
			
			//查询历史流程实例
			@Test
			public void findHistoryProcessInstance(){
				String processInstanceId = "5008";
				HistoricProcessInstance historicProcessInstance = processEngine.getHistoryService()
				             .createHistoricProcessInstanceQuery()
				             .processInstanceId(processInstanceId)
				             .singleResult();
				
				System.out.println(historicProcessInstance.getId()+" ");
			}
}
