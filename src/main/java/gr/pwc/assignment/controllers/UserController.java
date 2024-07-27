package gr.pwc.assignment.controllers;

import gr.pwc.assignment.entities.User;
import gr.pwc.assignment.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> read(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PreAuthorize("hasRole('ROLE_OPERATOR')")
    @PatchMapping("/{id}/balance/increment")
    public ResponseEntity<User> incrementUserBalance(@PathVariable("id") UUID userId) {
        return ResponseEntity.ok(userService.incrementUserBalance(userId));
    }

    @PreAuthorize("hasRole('ROLE_OPERATOR')")
    @PatchMapping("/{id}/balance/decrement")
    public ResponseEntity<User> decrementUserBalance(@PathVariable("id") UUID userId) {
        return ResponseEntity.ok(userService.decrementUserBalance(userId));
    }

    @GetMapping()
    public ResponseEntity<List<User>> list(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(userService.listUsers(PageRequest.of(0, limit)).getContent());

    }
}
