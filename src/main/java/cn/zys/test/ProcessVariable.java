package cn.zys.test;

import java.io.InputStream;
import java.util.Date;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.bpmn.data.Data;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import cn.zys.bean.Person;

public class ProcessVariable {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	//部署流程定义（方法三）
	@Test
	public void deploymentProcessDefinition(){
		
		InputStream inputStreambpmn = this.getClass().getResourceAsStream("/diagrams/processVariable.bpmn");
		InputStream inputStreampng = this.getClass().getResourceAsStream("/diagrams/processVariable.png");
		 Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
		              .createDeployment()//创建一个部署对象
		              .name("processVariable实例")//添加部署名称
		              .addInputStream("processVariable.bpmn", inputStreambpmn)//使用资源文件名称和输入流加载
		              .addInputStream("processVariable.png", inputStreampng)
		              .deploy();//完成部署
		 System.out.println(deployment.getId());
		 System.out.println(deployment.getName());
	}
	
	//启动流程实例
	@Test
	public void startProcessInstance(){
		String processDefinitionKey = "processVariable";
		ProcessInstance processInstance = processEngine.getRuntimeService()//执行与流程实例执行对象相关的service
		             .startProcessInstanceByKey(processDefinitionKey);
		System.out.println("流程ID："+processInstance.getId());
	}
	
	//设置流程变量
	@Test
	public void setVariable(){
		TaskService taskService = processEngine.getTaskService();
		
		String taskId = "32504";
		
//		taskService.setVariable(taskId, "请假天数", 3);
//		taskService.setVariable(taskId, "请假日起",new Date());
//		taskService.setVariable(taskId, "请假原因", "回家探亲");
		Person person = new Person();
		person.setId(1);
		person.setName("haha");
		
		taskService.setVariable(taskId, "person",person);
		System.out.println(person);
	}
	
	//获取流程变量
	@Test
		public void getVariable(){
			TaskService taskService = processEngine.getTaskService();
			
			String taskId = "32504";
//			
//			Integer days = (Integer) taskService.getVariable(taskId, "请假天数");
//			Date date = (Date) taskService.getVariable(taskId, "请假日起");
//			String reason = (String) taskService.getVariable(taskId, "请假原因");
//			System.out.println(days + " "+date + " "+ reason);
//			
			Person person = (Person) taskService.getVariable(taskId, "person");
			System.out.println(person.getId());
		}
		
		//模拟设置和获取流程变量的场景
		@Test
		public void setAndGetVariable(){
			RuntimeService runtimeService = processEngine.getRuntimeService();
			
			TaskService taskService = processEngine.getTaskService();
			
		//	runtimeService.setVariable(executionId, variableName, value);//表示使用执行对象ID、流程变量名称，设置流程变量的值（一次次只能设置一个）
		//	runtimeService.setVariables(executionId, variables);//表示使用执行对象ID、和map集合设置流程变量，key：名称，value：变量值

			//  taskService.setVariable(executionId, variableName, value);//表示任务ID、流程变量名称，设置流程变量的值（一次次只能设置一个）
			//	taskService.setVariables(executionId, variables);//表示使用任务ID、和map集合设置流程变量，key：名称，value：变量值
			
		//	runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);//启动流程实例的时候设置流程变量
		//	taskService.complete(taskId, variables);//完成任务时，设置流程变量
			
			//获取流程变量
		//	runtimeService.getVariable(executionId, variableName);//使用执行对象ID和流程变量名称，获取流程变量的值
		}
}
