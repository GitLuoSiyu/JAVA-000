package com.zjh.manager;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TransactionManager.
 *
 * @author zhongjinhui
 */
@Component
public class TransactionManager {
    private final Set<Long> tradingUsers = new HashSet<>();

    public synchronized boolean apply(List<Long> userIds) {
        long count = userIds.stream().filter(tradingUsers::contains).count();
        if (count > 0) {
            return false;
        }
        tradingUsers.addAll(userIds);
        return true;
    }

    public synchronized void release(List<Long> userIds) {
        tradingUsers.removeAll(userIds);
    }


}
