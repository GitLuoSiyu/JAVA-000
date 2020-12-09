package me.bob;

import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class StudentService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    @ShardingTransactionType(TransactionType.XA)  // 支持TransactionType.LOCAL, TransactionType.XA, TransactionType.BASE
    public void insert() {
        jdbcTemplate.execute("INSERT INTO t_student (uuid, name) VALUES (?, ?)", (PreparedStatementCallback<Object>) ps -> {
            ps.setObject(1, 1);
            ps.setObject(2, "Bob1");
            ps.executeUpdate();
            return null;
        });
    }

    @Transactional
    @ShardingTransactionType(TransactionType.XA)
    public void insertFailed() {
        jdbcTemplate.execute("INSERT INTO t_student (uuid, name) VALUES (?, ?)", (PreparedStatementCallback<Object>) ps -> {
            ps.setObject(1, 2);
            ps.setObject(2, "Bob2");
            ps.executeUpdate();
            throw new RuntimeException("execute error");
        });
    }
}
