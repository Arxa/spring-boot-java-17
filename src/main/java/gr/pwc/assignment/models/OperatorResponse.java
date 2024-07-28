package gr.pwc.assignment.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gr.pwc.assignment.entities.Operator;
import gr.pwc.assignment.entities.Role;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperatorResponse {

    private UUID id;
    private String name;
    private String username;
    private Set<Role> roles;

    public OperatorResponse(Operator operator) {
        this.id = operator.getId();
        this.name = operator.getName();
        this.username = operator.getUsername();
        this.roles = operator.getRoles();
    }
}
