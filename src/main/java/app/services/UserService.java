package app.services;

import app.entities.BalanceHistory;
import app.entities.User;
import app.models.BalanceActionEnum;
import app.models.UserResponse;
import app.repositories.BalanceHistoryRepository;
import app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ValidationService validationService;
    private final OperatorService operatorService;
    private final BalanceHistoryRepository balanceHistoryRepository;

    public UserResponse getUser(UUID userId) {
        return new UserResponse(validationService.validateUserExists(userId));
    }

    public List<UserResponse> listUsers(Pageable pageable) {
        return userRepository.findAll(pageable).getContent()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponse incrementUserBalance(UUID userId) {
        User user = validationService.validateUserExists(userId);
        saveBalanceHistory(user, BalanceActionEnum.INCREMENT);
        user.setBalance(user.getBalance() + 1);
        return new UserResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponse decrementUserBalance(UUID userId) {
        User user = validationService.validateUserExists(userId);
        saveBalanceHistory(user, BalanceActionEnum.DECREMENT);
        user.setBalance(user.getBalance() - 1);
        if (user.getBalance() < 0L) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User balance cannot go negative");
        }
        return new UserResponse(userRepository.save(user));
    }

    private void saveBalanceHistory(User user, BalanceActionEnum balanceActionEnum) {
        BalanceHistory balanceHistory = new BalanceHistory();
        balanceHistory.setOperation(balanceActionEnum);
        balanceHistory.setRequesterOperatorUsername(operatorService.getActiveOperatorUsername());
        balanceHistory.setTargetedUserName(user.getName());
        balanceHistory.setDate(new Date());
        balanceHistoryRepository.save(balanceHistory);
    }

}
