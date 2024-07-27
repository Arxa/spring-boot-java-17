package gr.pwc.assignment.controllers;


import gr.pwc.assignment.entities.Operator;
import gr.pwc.assignment.models.OperatorRequest;
import gr.pwc.assignment.services.OperatorService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/operators")
public class OperatorController {

    private final OperatorService operatorService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Operator> addOperator(@Valid @RequestBody OperatorRequest operatorRequest) {
        return ResponseEntity.ok(operatorService.addOperator(operatorRequest));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}/remove")
    public ResponseEntity<String> removeOperator(@PathVariable("id") UUID operatorId) {
        operatorService.removeOperator(operatorId);
        return ResponseEntity.ok("DELETED");
    }

}
