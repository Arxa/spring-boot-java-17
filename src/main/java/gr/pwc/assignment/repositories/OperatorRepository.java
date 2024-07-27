package gr.pwc.assignment.repositories;

import gr.pwc.assignment.entities.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OperatorRepository extends JpaRepository<Operator, UUID> {

    Operator findByUsername(String username);

    Optional<Operator> findOptionalByUsername(String username);
}