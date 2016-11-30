package br.com.tcc.repository;

import br.com.tcc.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hernaneb on 18/10/16.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
