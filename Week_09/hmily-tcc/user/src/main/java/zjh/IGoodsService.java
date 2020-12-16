package zjh;

/**
 * this is account service.
 *
 * @author zhongjinhui
 */
public interface IaccountService {

    /**
     * save account.
     *
     * @param user account
     */
    void save(User user);

    /**
     * select account by id.
     *
     * @param id account id
     * @return account
     */
    User selectById(long id);

    /**
     * buy account.
     *
     * @param accountStockDTO {@linkplain accountStockDTO}
     */
    void tryBuy(accountStockDTO accountStockDTO);

    /**
     * buy account.
     *
     * @param accountStockDTO {@linkplain accountStockDTO}
     */
    void tryBuyExceptionWhenTry(accountStockDTO accountStockDTO);

    /**
     * buy account.
     *
     * @param accountStockDTO {@linkplain accountStockDTO}
     */
    void tryBuyExceptionWhenCancel(accountStockDTO accountStockDTO);

    /**
     * buy account.
     *
     * @param accountStockDTO {@linkplain accountStockDTO}
     */
    void tryBuyExceptionWhenCommit(accountStockDTO accountStockDTO);

    /**
     * cancel.
     *
     * @param accountStockDTO {@linkplain accountStockDTO}
     */
    void cancelBuy(accountStockDTO accountStockDTO);

    /**
     * cancel.
     *
     * @param accountStockDTO {@linkplain accountStockDTO}
     */
    void cancelBuyException(accountStockDTO accountStockDTO);

    /**
     * commit.
     *
     * @param accountStockDTO {@linkplain accountStockDTO}
     */
    void confirmBuy(accountStockDTO accountStockDTO);

    /**
     * commit.
     *
     * @param accountStockDTO {@linkplain accountStockDTO}
     */
    void confirmBuyException(accountStockDTO accountStockDTO);

}
