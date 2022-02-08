package es.miguelgsi.springboottestingprimer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJPARepository extends JpaRepository<ProductJPA, Long> {
}
