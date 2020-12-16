package zjh;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * common result.
 *
 * @author zhongjinhui
 */
@Data
public class Result {
    private int code;
    private String message;
    private Object data;

    public static Result ok() {
        return ok(HttpStatus.OK.value(), "success", null);
    }

    public static Result ok(int code, String message, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMessage(message);
        r.setData(data);
        return r;
    }

    public static Result error(String message) {
        return ok(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
    }

}
