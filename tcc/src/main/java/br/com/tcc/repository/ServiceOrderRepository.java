package br.com.tcc.repository;

import br.com.tcc.model.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hernaneb on 19/10/16.
 */
@Repository
public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long>{
}
