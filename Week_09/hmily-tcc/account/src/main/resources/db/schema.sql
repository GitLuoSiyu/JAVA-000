drop table if exists `t_user`;
CREATE TABLE `t_user`
(
    `id`          bigint(20)  NOT NULL COMMENT '主键ID',
    `username`    varchar(64) NOT NULL COMMENT '用户名',
    `nickname`    varchar(64) NOT NULL COMMENT '昵称',
    `create_time` bigint(20)  NOT NULL COMMENT '冻结时间',
    `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='用户';

insert into t_user (`username`, `nickname`)
values ('cuijing', 'cuicui');


drop table if exists `t_account`;
CREATE TABLE `t_account`
(
    `id`             bigint(20) NOT NULL COMMENT '主键ID',
    `user_id`        bigint(20) NOT NULL COMMENT '用户id',
    `dollar_balance` bigint(20) NOT NULL COMMENT '美元余额（单位分）',
    `rmb_balance`    bigint(20) NOT NULL COMMENT '人民币余额（单位分）',
    `create_time`    bigint(20) NOT NULL COMMENT '冻结时间',
    `update_time`    bigint(20) DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    unique KEY (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='账户';


drop table if exists `t_transaction`;
CREATE TABLE `t_transaction`
(
    `id`          bigint(20)  NOT NULL COMMENT '主键ID',
    `number`      varchar(64) NOT NULL COMMENT '交易编号',
    `user_id`     bigint(20)  NOT NULL COMMENT '用户id',
    `dollar`      bigint(20)  NOT NULL COMMENT '交易金额（单位分）',
    `rmb`         bigint(20)  NOT NULL COMMENT '交易金额（单位分）',
    `status`      bigint(20)  NOT NULL COMMENT '交易状态，0-待核对,1-交易完成,2-交易失败',
    `create_time` bigint(20)  NOT NULL COMMENT '交易时间',
    `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    key (`number`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='交易';

