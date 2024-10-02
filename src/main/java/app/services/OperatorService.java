package app.services;

import app.entities.Operator;
import app.entities.OperatorHistory;
import app.entities.Role;
import app.models.OperatorActionEnum;
import app.models.OperatorResponse;
import app.repositories.OperatorHistoryRepository;
import app.repositories.OperatorRepository;
import app.models.OperatorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperatorService {

    private final ValidationService validationService;
    private final OperatorRepository operatorRepository;
    private final OperatorHistoryRepository operatorHistoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public OperatorResponse addOperator(OperatorRequest operatorRequest) {
        Role role = validationService.validateRoleExists(operatorRequest.getRole());
        operatorRepository.findOptionalByUsername(operatorRequest.getUsername()).ifPresent(s -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An Operator with this username already exists");
        });
        Operator operator = new Operator();
        operator.setName(operatorRequest.getName());
        operator.setUsername(operatorRequest.getUsername());
        operator.setPassword(passwordEncoder.encode(operatorRequest.getPassword()));
        operator.setRoles(Set.of(role));

        OperatorHistory operatorHistory = new OperatorHistory();
        operatorHistory.setOperation(OperatorActionEnum.ADD);
        operatorHistory.setRequesterOperatorUsername(getActiveOperatorUsername());
        operatorHistory.setTargetedOperatorUsername(operator.getUsername());
        operatorHistory.setTargetedOperatorRole(role.getName());
        operatorHistory.setDate(new Date());
        operatorHistoryRepository.save(operatorHistory);

        return new OperatorResponse(operatorRepository.save(operator));
    }

    @Transactional
    public void removeOperator(UUID operatorId) {
        Operator operator = validationService.validateOperatorExists(operatorId);
        OperatorHistory operatorHistory = new OperatorHistory();
        operatorHistory.setOperation(OperatorActionEnum.REMOVE);
        operatorHistory.setRequesterOperatorUsername(getActiveOperatorUsername());
        operatorHistory.setTargetedOperatorUsername(operator.getUsername());
        operatorHistory.setTargetedOperatorRole(operator.getRoles().stream().findFirst().get().getName());
        operatorHistory.setDate(new Date());
        operatorHistoryRepository.save(operatorHistory);
        operator.setRoles(Set.of());
        operatorRepository.delete(operator);
    }

    public String getActiveOperatorUsername() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
