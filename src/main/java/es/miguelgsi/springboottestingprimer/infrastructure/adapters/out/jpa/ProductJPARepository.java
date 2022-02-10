package es.miguelgsi.springboottestingprimer.infrastructure.adapters.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJPARepository extends JpaRepository<ProductJPA, Long> {
}
