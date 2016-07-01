package cn.zys.test;

import java.util.List;


import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class HelloWorld {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	//部署流程定义
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
	
	//启动流程实例
	@Test
	public void startProcessInstance(){
		String processDefinitionKey = "helloworld";
		ProcessInstance processInstance = processEngine.getRuntimeService()//执行与流程实例执行对象相关的service
		             .startProcessInstanceByKey(processDefinitionKey);
		System.out.println("流程ID"+processInstance.getId());
	}
	
	//查询当前人的个人任务
	@Test
	public void findMyPersonalTask(){
		String assignee = "李四";
		List<Task> list = processEngine.getTaskService()//与正在执行任务管理相关的service
		             .createTaskQuery()//创建任务查询对象
		             .taskAssignee(assignee)//制定办理人
		             .list();
		
		if(list != null && list.size() >0){
			for(Task task:list){
				System.out.println("任务ID:"+task.getId());
			}
		}
	}
	
	//完成我的任务
	@Test
	public void completeMyPersonalTask(){
		String taskId = "15002";
		processEngine.getTaskService()
		             .complete(taskId);
		System.out.println("完成任务：任务ID:"+taskId);
	}
}
