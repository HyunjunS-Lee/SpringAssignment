package assignment.spring.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationStatus {
    VISIT_CONFIRMED("방문확인 되었습니다.");

    private String message;
}