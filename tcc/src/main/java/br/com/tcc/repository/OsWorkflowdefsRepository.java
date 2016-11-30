package br.com.tcc.repository;

import br.com.tcc.model.OsWorkflowdefs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hernaneb on 23/10/16.
 */
@Repository
public interface OsWorkflowdefsRepository extends JpaRepository<OsWorkflowdefs, Long> {
}
