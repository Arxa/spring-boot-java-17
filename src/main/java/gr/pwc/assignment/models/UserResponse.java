package gr.pwc.assignment.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gr.pwc.assignment.entities.User;
import lombok.Data;

import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {

    private UUID userId;
    private String name;
    private Long vat;
    private Long counterId;
    private String address;
    private String postCode;
    private String phone;
    private String dateOfBirth;
    private Long balance;

    public UserResponse(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.vat = user.getVat();
        this.counterId = user.getCounterId();
        this.address = user.getAddress();
        this.postCode = user.getPostCode();
        this.phone = user.getPhone();
        this.dateOfBirth = user.getDateOfBirth();
        this.balance = user.getBalance();
    }
}
