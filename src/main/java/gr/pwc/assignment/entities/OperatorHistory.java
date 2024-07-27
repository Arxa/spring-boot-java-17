package gr.pwc.assignment.entities;

import gr.pwc.assignment.models.OperatorActionEnum;
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

import static jakarta.persistence.EnumType.STRING;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = OperatorHistory.OPERATOR_HISTORY_TABLE_NAME)
public class OperatorHistory {

    public static final String OPERATOR_HISTORY_TABLE_NAME = "operator_history";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "request_operator_id", nullable = false)
    private Operator requestOperator;

    @ManyToOne
    @JoinColumn(name = "target_operator_id", nullable = false)
    private Operator targetOperator;

    @Enumerated(STRING)
    @Column(nullable = false)
    private OperatorActionEnum operation;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date date;

}
