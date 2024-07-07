package assignment.spring.expection;

import assignment.spring.type.ErrorCode;
import assignment.spring.type.StoreStatus;
import lombok.*;

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
