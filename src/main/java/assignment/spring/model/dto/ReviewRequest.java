package assignment.spring.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequest {
    private Long storeId;
    private Long userId;
    private int rating;
    private String comment;
}
