package zjh;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * accountMapper
 *
 * @author zhongjinhui
 */
@Mapper
public interface accountMapper {

    /**
     * save account.
     *
     * @param user account
     */
    void save(@Param("account") User user);

    /**
     * select account by id.
     *
     * @param id account id
     * @return account
     */
    User selectById(long id);

    /**
     * lock stock.
     *
     * @param id       account id
     * @param quantity quantity
     */
    void lockStock(long id, int quantity);

    /**
     * unlock stock on failure.
     *
     * @param id       account id
     * @param quantity quantity
     */
    void unlockStockOnFailure(long id, int quantity);

    /**
     * unlock stock on success.
     *
     * @param id       account id
     * @param quantity quantity
     */
    void unlockStockOnSuccess(long id, int quantity);
}
