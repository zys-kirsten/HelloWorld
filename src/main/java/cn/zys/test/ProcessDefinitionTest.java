package cn.zys.test;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;


public class ProcessDefinitionTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	//查询历程的最新版本
	@Test
	public void findLastVersion(){
		List<ProcessDefinition> list = processEngine.getRepositoryService()
		             .createProcessDefinitionQuery()
		             .orderByProcessDefinitionVersion()
		             .asc()
		             .list();
		
		Map<String, ProcessDefinition> map = new LinkedHashMap<>();
		if (list != null && list.size() > 0) {
			for(ProcessDefinition processDefinition:list){
				map.put(processDefinition.getKey(), processDefinition);
			}
		}
	}
	
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
		
	//流程部署第二种方式
	@Test
	public void deploymentProcessDefinition_zip(){
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip");
		ZipInputStream zipInputStream = new ZipInputStream(in);
		Deployment deployment = processEngine.getRepositoryService()
				                             .createDeployment()
				                             .name("流程定义2")
				                             .addZipInputStream(zipInputStream)
				                             .deploy();
		 System.out.println(deployment.getId());
		 System.out.println(deployment.getName());
	}
	
	//查询流程定义
	@Test
	public void findProcessDefinition(){
		List<ProcessDefinition> list =  processEngine.getRepositoryService()
		             .createProcessDefinitionQuery()//创建流程定义查询
		             //相当于查询语句中的where后的内容
	//	             .deploymentId(deploymentId)//使用部署对象ID查询  
	//	             .processDefinitionId(processDefinitionId)//使用流程定义ID查询
	//	             .processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询
	
		             //排序
	//	             .orderByProcessDefinitionVersion().asc()//按照版本的升序排列
		             .orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列
		             
		             //返回结果集
                     .list();//返回一个集合列表  封装流程定义
	//	             .singleResult()//返回唯一结果
	//	             .count()//返回结果集数量
	//	             .listPage(firstResult, maxResults)//分页查询
		
		if (list != null && list.size() > 0) {
			for(ProcessDefinition processDefinition:list){
				System.out.println("流程定义ID"+processDefinition.getId());
			}
		}
		             
	}
	
	//删除流程定义
	@Test
	public void deleteProcessDefinition(){
		String deploymentId = "1";
		
		//不带级联的删除，如果流程启动了  删除就会报错
		//带布尔型的就可以删除
		processEngine.getRepositoryService()
		             .deleteDeployment(deploymentId);
		System.out.println("删除成功！");
	}
	
	//查看流程图
	@Test
	public void viewPic(){
		String deploymentId="";
		//获取图片资源名称
		List<String> list = processEngine.getRepositoryService()
		                                 .getDeploymentResourceNames(deploymentId);
		//定义图片资源的名称
		String resourceName = "";
		if(list != null && list.size() >0){
			for(String name:list){
				if(name.indexOf(".png")>=0){
					resourceName = name;
				}
			}
		}
		//获取图片输入流
		InputStream in = processEngine.getRepositoryService()
		             .getResourceAsStream(deploymentId, resourceName);
	} 
}