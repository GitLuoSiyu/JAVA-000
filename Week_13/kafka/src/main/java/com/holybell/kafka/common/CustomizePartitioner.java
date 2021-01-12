package com.holybell.kafka.common;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * kafka中每个topic被划分为多个分区，那么生产者将消息发送到topic时，具体追加到哪个分区呢？这就是所谓的分区策略，
 * Kafka 为我们提供了默认的分区策略，同时它也支持自定义分区策略。
 * 其路由机制为：
 * <p>
 * 若发送消息时指定了分区（即自定义分区策略），则直接将消息append到指定分区；
 * <p>
 * 若发送消息时未指定 patition，但指定了 key（kafka允许为每条消息设置一个key），则对key值进行hash计算，
 * 根据计算结果路由到指定分区，这种情况下可以保证同一个 Key 的所有消息都进入到相同的分区；
 * <p>
 * patition 和 key 都未指定，则使用kafka默认的分区策略，轮询选出一个 patition；
 * <p>
 * 开启指定分区机制，取消application.yml中的相关属性注释
 */
public class CustomizePartitioner implements Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        // 自定义分区规则(这里假设全部发到0号分区)
        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
