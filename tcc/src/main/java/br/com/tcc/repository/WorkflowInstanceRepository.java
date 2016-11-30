package br.com.tcc.repository;

import br.com.tcc.model.WorkflowInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hernaneb on 21/10/16.
 */
@Repository
public interface WorkflowInstanceRepository extends JpaRepository<WorkflowInstance, Long> {

	@Query( nativeQuery = true,
			value = "select * from tcc_workflow_instance where fk_service_order = ?1 and closed_date is null")
	List<WorkflowInstance> getWorkflowInstanceByServiceOrderId(Long serviceOrderId);

	@Query( nativeQuery = true,
			value = "select * from tcc_workflow_instance where id_execution = ?1")
	WorkflowInstance getWorkflowInstanceByIdExecutionId(Long idExecution);
}
