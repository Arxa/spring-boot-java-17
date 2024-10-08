package app.repositories;

import app.entities.OperatorHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OperatorHistoryRepository extends JpaRepository<OperatorHistory, UUID> {

}