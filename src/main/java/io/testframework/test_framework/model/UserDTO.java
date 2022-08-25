package io.testframework.test_framework.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
public class UserDTO {

    private UUID id;

    @NotNull
    @Size(max = 255)
    private String firstname;

    @NotNull
    @Size(max = 255)
    private String lastname;

    @Size(max = 255)
    private String email;

    @NotNull
    private LocalDateTime registredAt;

    private LocalDateTime deletedAt;

    private List<UUID> userAssets;

}
