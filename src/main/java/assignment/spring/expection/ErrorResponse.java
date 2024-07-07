package assignment.spring.expection;

import assignment.spring.type.ErrorCode;
import assignment.spring.type.StoreStatus;
import lombok.*;

/**
 * 예외 처리를 위한 커스텀 에러 응답을 정의하는 Java 클래스입니다.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse extends RuntimeException {
    private ErrorCode errorCode;
    private StoreStatus storeStatus;
    private String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
