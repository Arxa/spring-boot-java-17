package gr.pwc.assignment.services;

import gr.pwc.assignment.entities.Operator;
import gr.pwc.assignment.entities.Role;
import gr.pwc.assignment.entities.User;
import gr.pwc.assignment.repositories.OperatorRepository;
import gr.pwc.assignment.repositories.RoleRepository;
import gr.pwc.assignment.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OperatorRepository operatorRepository;

    public User validateUserExists(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, User.USER_TABLE_NAME));
    }

    public Role validateRoleExists(String roleName) {
        return roleRepository.findByName(roleName).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, Role.ROLE_TABLE_NAME));
    }

    public Operator validateOperatorExists(UUID operatorId) {
        return operatorRepository.findById(operatorId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, Operator.OPERATOR_TABLE_NAME));
    }

}
