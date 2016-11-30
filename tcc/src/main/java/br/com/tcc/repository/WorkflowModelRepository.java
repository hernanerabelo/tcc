package br.com.tcc.repository;

import br.com.tcc.model.WorkflowModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hernaneb on 21/10/16.
 */
@Repository
public interface WorkflowModelRepository extends JpaRepository<WorkflowModel, Long>{

	@Query( nativeQuery = true,
			value = "select * from tcc_workflow_model where name = ?1 and version = ?2 " +
					"and status = 'ATIVO'")
	List<WorkflowModel> getValidWorkflowModelByName(String name, String version);
}
