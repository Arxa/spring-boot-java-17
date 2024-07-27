package gr.pwc.assignment.config;

import gr.pwc.assignment.entities.Operator;
import gr.pwc.assignment.repositories.OperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OperatorsDetailsService implements UserDetailsService {

    private final OperatorRepository operatorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Operator operator = operatorRepository.findByUsername(username);
        Set<GrantedAuthority> authorities = operator.getRoles().stream()
                .map((roles) -> new SimpleGrantedAuthority(roles.getName()))
                .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(
                username,
                operator.getPassword(),
                authorities
        );
    }
}
