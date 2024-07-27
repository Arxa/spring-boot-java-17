package gr.pwc.assignment.services;

import gr.pwc.assignment.entities.Operator;
import gr.pwc.assignment.repositories.OperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final OperatorRepository operatorRepository;

    public Operator getActiveOperator() {
        UserDetails activeUser = (UserDetails)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return operatorRepository.findOptionalByUsername(activeUser.getUsername())
                .orElseThrow(() -> new RuntimeException("active username not founds in db"));
    }
}
