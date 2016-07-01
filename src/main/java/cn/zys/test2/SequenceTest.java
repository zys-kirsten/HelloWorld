package cn.zys.test2;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class SequenceTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void deploymentProcessDefinition_zip(){
		InputStream inputStreambpmn = this.getClass().getResourceAsStream("sequence.bpmn");
		InputStream inputStreampng = this.getClass().getResourceAsStream("sequence.png");

		Deployment deployment = processEngine.getRepositoryService()
				                             .createDeployment()
				                             .name("连线")
				                             .addInputStream("sequence.bpmn", inputStreambpmn)
                                             .addInputStream("sequence.png", inputStreampng)
				                             .deploy();
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
	
	
	@Test
	public void testSequence(){
		
	}
}
