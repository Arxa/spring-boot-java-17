package gr.pwc.assignment.entities;

import gr.pwc.assignment.models.BalanceActionEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

import static gr.pwc.assignment.entities.BalanceHistory.BALANCE_HISTORY_TABLE_NAME;
import static jakarta.persistence.EnumType.STRING;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = BALANCE_HISTORY_TABLE_NAME)
public class BalanceHistory {

    public static final String BALANCE_HISTORY_TABLE_NAME = "balance_history";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "requester_operator_username", nullable = false)
    private String requesterOperatorUsername;

    @Column(name = "targeted_user_name", nullable = false)
    private String targetedUserName;

    @Enumerated(STRING)
    @Column(nullable = false)
    private BalanceActionEnum operation;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date date;

}
