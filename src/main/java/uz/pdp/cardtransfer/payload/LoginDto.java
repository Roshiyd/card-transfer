package uz.pdp.cardtransfer.payload;

import lombok.Data;

@Data
public class LoginDto {
    private String password;
    private String username;
}
