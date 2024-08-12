package swiftescaper.backend.swiftescaper.apiPayload.exception;


import swiftescaper.backend.swiftescaper.apiPayload.code.BaseErrorCode;
import swiftescaper.backend.swiftescaper.apiPayload.code.ErrorReasonDTO;

public class GeneralException extends RuntimeException {
    private BaseErrorCode code;

    public GeneralException(BaseErrorCode code) {
        super(code.getReason().getMessage());
        this.code = code;
    }

    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}
