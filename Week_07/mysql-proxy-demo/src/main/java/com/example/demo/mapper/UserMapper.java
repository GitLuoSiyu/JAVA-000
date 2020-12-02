package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author chentz
 * @version v1.0
 * @date 2020/12/1 17:19
 */
@Mapper
public interface UserMapper {
    List<User> list();
}
