package br.com.tcc.repository;

import br.com.tcc.model.HistoryStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by hernaneb on 11/11/16.
 */
public interface HistoryStepRepository extends JpaRepository<HistoryStep, Long> {

	@Query( nativeQuery = true,
			value = "select * from os_historystep where entry_id = ?1 order by id asc")
	List<HistoryStep> getHistoryStepByEntryId(Long entryId);
}
