package assignment.spring.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    STORE_NOT_FOUND("상점을 찾을 수가 없습니다."),
    RESERVATION_NOT_FOUND("예약을 찾을 수가 없습니다."),
    USER_NOT_FOUND("사용자를 찾을 수가 없습니다."),
    REVIEW_NOT_FOUND("리뷰를 찾을 수가 없습니다."),
    ID_NOT_EXIT("존재하지 않는 ID 입니다."),
    PASSWORD_UN_MATCH("비밀번호가 일치하지 않습니다."),
    USER_ID_ALREADY_EXIT("회원은 존재합니다.");

    private String message;
}