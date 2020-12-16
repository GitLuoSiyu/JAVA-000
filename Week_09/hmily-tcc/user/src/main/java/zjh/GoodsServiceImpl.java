package zjh;

import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * accountServiceImpl
 *
 * @author zhongjinhui
 */
@Slf4j
@Service
@Component
public class accountServiceImpl implements IaccountService {

    @Resource
    private accountMapper accountMapper;

    @Override
    public void save(User user) {
        accountMapper.save(user);
    }

    @Override
    public User selectById(long id) {
        return accountMapper.selectById(id);
    }

    @Override
    @HmilyTCC(cancelMethod = "cancelBuy", confirmMethod = "confirmBuy")
    public void tryBuy(accountStockDTO accountStockDTO) {
        doBuy(accountStockDTO);
    }

    @Override
    @HmilyTCC(cancelMethod = "cancelBuy", confirmMethod = "confirmBuy")
    public void tryBuyExceptionWhenTry(accountStockDTO accountStockDTO) {
        doBuy(accountStockDTO);
        throw new IllegalStateException("--->>try buy exception");
    }

    @Override
    @HmilyTCC(cancelMethod = "cancelBuyException", confirmMethod = "confirmBuy")
    public void tryBuyExceptionWhenCancel(accountStockDTO accountStockDTO) {
        doBuy(accountStockDTO);
        throw new IllegalArgumentException("try bu exception");
    }

    @Override
    @HmilyTCC(cancelMethod = "cancelBuy", confirmMethod = "confirmBuyException")
    public void tryBuyExceptionWhenCommit(accountStockDTO accountStockDTO) {
        doBuy(accountStockDTO);
    }

    @Override
    public void cancelBuy(accountStockDTO accountStockDTO) {
        findaccountById(accountStockDTO.getaccountId());
        accountMapper.unlockStockOnFailure(accountStockDTO.getaccountId(), accountStockDTO.getQuantity());
        log.info("--->>cancel buy, account id = {}, quantity = {}", accountStockDTO.getaccountId(), accountStockDTO.getQuantity());
    }

    @Override
    public void cancelBuyException(accountStockDTO accountStockDTO) {
        throw new IllegalStateException("--->>cancel buy exception");
    }

    @Override
    public void confirmBuy(accountStockDTO accountStockDTO) {
        findaccountById(accountStockDTO.getaccountId());
        accountMapper.unlockStockOnSuccess(accountStockDTO.getaccountId(), accountStockDTO.getQuantity());
        log.info("--->>confirm buy, account id = {}, quantity = {}", accountStockDTO.getaccountId(), accountStockDTO.getQuantity());
    }

    @Override
    public void confirmBuyException(accountStockDTO accountStockDTO) {
        throw new IllegalStateException("--->>confirm buy exception");
    }

    private User findaccountById(long id) {
        User user = accountMapper.selectById(id);
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("account dose not exist");
        }
        return user;
    }

    private void doBuy(accountStockDTO accountStockDTO) {
        User user = findaccountById(accountStockDTO.getaccountId());
        if (user.getStock() < accountStockDTO.getQuantity()) {
            throw new IllegalArgumentException("insufficient inventory of account");
        }
        accountMapper.lockStock(accountStockDTO.getaccountId(), accountStockDTO.getQuantity());
        log.info("--->>try buy, account id = {}, quantity = {}", accountStockDTO.getaccountId(), accountStockDTO.getQuantity());
    }
}
