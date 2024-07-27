package gr.pwc.assignment.services;

import gr.pwc.assignment.entities.User;
import gr.pwc.assignment.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ValidationService validationService;

    public User getUser(UUID userId) {
        return validationService.validateUserExists(userId);
    }

    public Page<User> listUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User incrementUserBalance(UUID userId) {
        User user = validationService.validateUserExists(userId);
        user.balance += 1;
        return userRepository.save(user);
    }

    public User decrementUserBalance(UUID userId) {
        User user = validationService.validateUserExists(userId);
        user.balance -= 1;
        if (user.balance < 0L) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User balance cannot go negative");
        }
        return userRepository.save(user);
    }

}
