

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id，表内自增长',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名称',
  `user_brith` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户出生日期，yyyymmdd',
  `user_age` int(11) NULL DEFAULT NULL COMMENT '用户年龄',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建用户时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;



SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for commodity_info
-- ----------------------------
DROP TABLE IF EXISTS `commodity_info`;
CREATE TABLE `commodity_info`  (
  `commodity_id` bigint(255) NOT NULL AUTO_INCREMENT COMMENT '商品的唯一标识id',
  `commodity_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `commodity_stocks` bigint(255) NULL DEFAULT NULL COMMENT '商品库存',
  `commodity_vendors` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品供应商',
  `commodity_produce_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品生产地址',
  `commodity_produce_date` datetime(0) NULL DEFAULT NULL COMMENT '商品生产日期',
  `commodity_expiration_date` datetime(0) NULL DEFAULT NULL COMMENT '商品保质期',
  `commodity_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品类型',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`commodity_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `order_id` bigint(20) NOT NULL COMMENT '订单id，业务唯一编号',
  `order_buyer_id` int(255) NULL DEFAULT NULL COMMENT '订单买家编号',
  `order_seller_id` int(11) NULL DEFAULT NULL COMMENT '订单卖家编号',
  `order_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '订单价格',
  `order_qty` int(11) NULL DEFAULT NULL COMMENT '订单数量',
  `order_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '订单金额',
  `order_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单发货地址',
  `order_create_time` datetime(0) NULL DEFAULT NULL COMMENT '订单创建时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
