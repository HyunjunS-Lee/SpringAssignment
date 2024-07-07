package assignment.spring.model.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationRequest {
    private Long storeId;
    private Long userId;
    private LocalDateTime reservationTime;
}
