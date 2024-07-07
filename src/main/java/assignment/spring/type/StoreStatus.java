package assignment.spring.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StoreStatus {
    PENDING("보류 중입니다."),
    CONFIRMED("확인 되었습니다."),
    CANCELLED("취소 되었습니다.");

    private String message;
}