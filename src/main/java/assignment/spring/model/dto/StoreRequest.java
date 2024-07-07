package assignment.spring.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreRequest {
    private String name;
    private String location;
    private String description;
    private Long ownerId;
}
