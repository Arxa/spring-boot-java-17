package gr.pwc.assignment.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import static gr.pwc.assignment.entities.User.USER_TABLE_NAME;

@Setter
@Getter
@Entity(name = USER_TABLE_NAME)
public class User {

    public static final String USER_TABLE_NAME = "user";

    @Id
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "vat")
    private Long vat;

    @Column(nullable = false, name = "counter_id")
    private Long counterId;

    @Column(nullable = false, name = "address")
    private String address;

    @Column(nullable = false, name = "post_code")
    private String postCode;

    @Column(nullable = false, name = "phone")
    private String phone;

    @Column(nullable = false, name = "date_of_birth")
    private String dateOfBirth;

    @Column(nullable = false, name = "balance")
    private Long balance;

}
