package gr.pwc.assignment.services;

import gr.pwc.assignment.entities.Operator;
import gr.pwc.assignment.entities.OperatorHistory;
import gr.pwc.assignment.entities.Role;
import gr.pwc.assignment.models.OperatorActionEnum;
import gr.pwc.assignment.models.OperatorRequest;
import gr.pwc.assignment.repositories.OperatorRepository;
import gr.pwc.assignment.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperatorService {

    private final ValidationService validationService;
    private final RoleService roleService;
    private final OperatorRepository operatorRepository;
    private final PasswordEncoder passwordEncoder;

    public Operator addOperator(OperatorRequest operatorRequest) {
        Role role = validationService.validateRoleExists(operatorRequest.getRole());
        operatorRepository.findOptionalByUsername(operatorRequest.getUsername()).ifPresent(s -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An Operator with this username already exists");
        });
        Operator operator = new Operator();
        operator.setName(operatorRequest.getName());
        operator.setUsername(operatorRequest.getUsername());
        operator.setPassword(passwordEncoder.encode(operatorRequest.getPassword()));
        operator.setRoles(Set.of(role));
        return operatorRepository.save(operator);
    }

    @Transactional
    public void removeOperator(UUID operatorId) {
        Operator operator = validationService.validateOperatorExists(operatorId);

        OperatorHistory operatorHistory = new OperatorHistory();
        operatorHistory.setOperation(OperatorActionEnum.REMOVE);
        operatorHistory.setRequestOperator(roleService.getActiveOperator());
        operatorHistory.setDate(new Date());
        //operatorHistory.setTargetOperator();

        operator.setRoles(Set.of());
        operatorRepository.delete(operator);



    }
}
