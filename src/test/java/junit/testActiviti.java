package junit;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class testActiviti {

	@Test
	public void activitiTest(){
		//ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();

		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
	    ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
	    System.out.println("processEngine"+processEngine);
	}
}
