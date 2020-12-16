package zjh;

import lombok.Data;

import java.io.Serializable;

/**
 * accountStockDTO
 *
 * @author zhongjinhui
 */
@Data
public class accountStockDTO implements Serializable {
    private Long accountId;
    private Integer quantity;
}
