package javdev.procesarorden.repository;

import javdev.procesarorden.entities.Cuentas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentasRepository extends JpaRepository<Cuentas, String> {
}
