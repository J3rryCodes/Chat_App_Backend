package de.ij3rry.chatApp.dots;

import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Data
public class SearchUserDetailsDTO {
    private String username;
    private UUID publicID;
}
