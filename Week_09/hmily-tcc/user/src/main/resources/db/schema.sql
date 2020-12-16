drop table if exists `t_account_0`;
CREATE TABLE `t_account_0`
(
    `id`                    bigint(20)          NOT NULL COMMENT '主键ID',
    `name`                  varchar(64)         NOT NULL COMMENT '商品名称',
    `stock`                 bigint(20)          NOT NULL COMMENT '库存',
    `lock_stock`            bigint(20)          NOT NULL COMMENT '锁定库存',
    `price`                 decimal(17, 8)      NOT NULL COMMENT '价格',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='商品';

drop table if exists `t_account_1`;
CREATE TABLE `t_account_1`
(
    `id`                    bigint(20)          NOT NULL COMMENT '主键ID',
    `name`                  varchar(64)         NOT NULL COMMENT '商品名称',
    `stock`                 bigint(20)          NOT NULL COMMENT '库存',
    `lock_stock`            bigint(20)          NOT NULL COMMENT '锁定库存',
    `price`                 decimal(17, 8)      NOT NULL COMMENT '价格',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='商品';