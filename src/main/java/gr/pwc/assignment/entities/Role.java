package gr.pwc.assignment.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static gr.pwc.assignment.entities.Role.ROLE_TABLE_NAME;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = ROLE_TABLE_NAME)
public class Role {

    public static final String ROLE_TABLE_NAME = "role";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, name = "name")
    private String name;
}
