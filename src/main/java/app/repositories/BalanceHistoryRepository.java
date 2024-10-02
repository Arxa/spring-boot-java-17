package app.repositories;

import app.entities.BalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BalanceHistoryRepository extends JpaRepository<BalanceHistory, UUID> {

}