package de.ij3rry.chatApp.dots;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
public class CheckInDTO {
    private UUID topicId;
}
