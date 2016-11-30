package br.com.tcc;

import com.opensymphony.workflow.StoreException;
import com.opensymphony.workflow.Workflow;
import com.opensymphony.workflow.config.SpringConfiguration;
import com.opensymphony.workflow.util.SpringTypeResolver;
import br.com.tcc.workflow.spring.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hernaneb on 17/10/16.
 */

@Configuration
public class ConfigurationBeans {

	@Autowired
	public DataSource dataSource;

	@Bean
	@Scope("prototype")
	public Workflow workflow(){
		Workflow workflow = new BaseWorkflow("System", workflowTypeResolver());
		try {
			workflow.setConfiguration( osWorkflowConfiguration() );
		} catch (StoreException e) {
			e.printStackTrace();
		}

		return workflow;
	}

	@Bean
	public SpringTypeResolver workflowTypeResolver(){
		SpringTypeResolver springTypeResolver = new SpringTypeResolver();
		return springTypeResolver;
	}

	@Bean
	public SpringConfiguration osWorkflowConfiguration() throws StoreException {
		SpringConfiguration springConfiguration = new SpringConfiguration();
		springConfiguration.setStore( workflowStore() );
		springConfiguration.setFactory( workflowFactory() );
		return springConfiguration;
	}

	@Bean
	public SpringJDBCWorkflowFactory workflowFactory(){
		SpringJDBCWorkflowFactory springJDBCWorkflowFactory = new SpringJDBCWorkflowFactory();
		springJDBCWorkflowFactory.setDs( dataSource );
		springJDBCWorkflowFactory.setReload(true);
		springJDBCWorkflowFactory.initSpring();
		return springJDBCWorkflowFactory;
	}

	@Bean
	public SpringJDBCWorkflowStore workflowStore() throws StoreException {
		SpringJDBCWorkflowStore springJDBCWorkflowStore = new SpringJDBCWorkflowStore();

		springJDBCWorkflowStore.setDatasource(dataSource);
		Map props = new HashMap();

		props.put("entry.sequence", "SELECT nextVal('seq_os_wfentry')");
		props.put("entry.table", "OS_WFENTRY");
		props.put("entry.id", "ID");
		props.put("entry.name", "NAME");
		props.put("entry.state", "STATE");
		props.put("step.sequence", "SELECT nextVal('seq_os_currentsteps')");
		props.put("history.table", "os_historystep");
		props.put("current.table", "OS_CURRENTSTEP");
		props.put("historyPrev.table", "OS_HISTORYSTEP_PREV");
		props.put("currentPrev.table", "OS_CURRENTSTEP_PREV");
		props.put("step.id", "ID");
		props.put("step.entryId", "ENTRY_ID");
		props.put("step.stepId", "STEP_ID");
		props.put("step.actionId", "ACTION_ID");
		props.put("step.owner", "OWNER");
		props.put("step.caller", "CALLER");
		props.put("step.startDate", "START_DATE");
		props.put("step.finishDate", "FINISH_DATE");
		props.put("step.dueDate", "DUE_DATE");
		props.put("step.status", "STATUS");
		props.put("step.previousId", "PREVIOUS_ID");

		props.put("step.sequence.increment", "INSERT INTO OS_STEPIDS (ID) values (null)" );
		props.put("step.sequence.retrieve", "SELECT max(ID) FROM OS_STEPIDS" );
		props.put("entry.sequence.increment", "INSERT INTO OS_ENTRYIDS (ID) values (null)" );
		props.put("entry.sequence.retrieve", "SELECT max(ID) FROM OS_ENTRYIDS" );

		springJDBCWorkflowStore.setProps(props);

		springJDBCWorkflowStore.initSpring();
		return springJDBCWorkflowStore;
	}

	@Bean
	@Scope("prototype")
	public SpringJDBCPropertySet jdbcPropertySet(){
		SpringJDBCPropertySet springJDBCPropertySet = new SpringJDBCPropertySet();
		springJDBCPropertySet.setDs( dataSource );
		return springJDBCPropertySet;
	}

	@Bean
	public SpringContext aplicationContextProvider(){
		SpringContext springContext = new SpringContext();
		return springContext;
	}


}
