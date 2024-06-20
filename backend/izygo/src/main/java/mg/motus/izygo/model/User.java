package mg.motus.izygo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@Builder
@ToString(doNotUseGetters = true)
public class User {
    @Id
    private Long id;

    @NotNull(message = "Un utilisateur doit avoir un prénom")
    @NotBlank(message = "Le prénom d'un utilisateur ne peut pas être vide")
    private String firstname;

    @NotNull(message = "Un utilisateur doit avoir un nom de famille")
    @NotBlank(message = "Le nom d'un utilisateur ne peut pas être vide")
    private String lastname;

    @NotNull(message = "Un utilisateur doit avoir un numéro de téléphone")
    @NotBlank(message = "Le numéro de téléphone d'un utilisateur ne peut pas être vide")
    @Setter
    private String phoneNumber;

    @NotNull(message = "Un utilisateur doit avoir un mot de passe")
    @NotBlank(message = "Le mot de passe d'un utilisateur ne peut pas être vide")
    @Size(min = 5, message = "Un mot de passe doit avoir au minimum 5 caractères")
    private String password;

    @NotNull
    @Builder.Default
    private Short roleId = 1;
}
