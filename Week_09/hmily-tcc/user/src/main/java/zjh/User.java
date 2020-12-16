package zjh;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zhongjinhui
 */
@Data
public class User implements Serializable {

    private long id;

    private String name;

    private long stock;

    private long lockStock;

    private BigDecimal price;

}