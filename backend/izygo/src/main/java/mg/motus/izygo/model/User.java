package mg.motus.izygo.model;

import mg.motus.izygo.validation.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mg.motus.izygo.validator.MalagasyPhoneNumber;
import org.springframework.data.annotation.Id;

@Getter
@Builder
@ToString(doNotUseGetters = true)
public class User {
    @Id
    private Long id;

    @NotNull(message = "Prénom requis")
    @NotBlank(message = "Prénom vide")
    private String firstname;

    @NotNull(message = "Nom de famille requis")
    @NotBlank(message = "Nom de famille vide")
    private String lastname;

    @Setter
    @NotNull(message = "Numéro de téléphone requis")
    @NotBlank(message = "Numéro de téléphone vide")
    @MalagasyPhoneNumber
    private String phoneNumber;

    @Setter
    @NotNull(message = "Un utilisateur doit avoir un mot de passe")
    @NotBlank(message = "Le mot de passe d'un utilisateur ne peut pas être vide")
    @Size(min = 5, message = "Un mot de passe doit avoir au minimum 5 caractères")
    private String password;

    @Setter
    private String account_balance;


    @NotNull
    @Builder.Default
    private Short roleId = 1;
}
