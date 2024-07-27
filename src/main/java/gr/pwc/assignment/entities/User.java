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
    public UUID userId;

    @Column(nullable = false, name = "name")
    public String name;

    @Column(nullable = false, name = "vat")
    public Long vat;

    @Column(nullable = false, name = "counter_id")
    public Long counterId;

    @Column(nullable = false, name = "address")
    public String address;

    @Column(nullable = false, name = "post_code")
    public String postCode;

    @Column(nullable = false, name = "phone")
    public String phone;

    @Column(nullable = false, name = "date_of_birth")
    public String dateOfBirth;

    @Column(nullable = false, name = "balance")
    public Long balance;

}
