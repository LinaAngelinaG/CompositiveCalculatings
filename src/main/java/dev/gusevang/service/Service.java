package dev.gusevang.service;

import dev.gusevang.*;
import com.google.gson.Gson;
import dev.gusevang.threadpool.ThreadPool;
import dev.gusevang.tree.Tree;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import redis.clients.jedis.Jedis;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

import static clustererService.InitLogger.initLogger;

public class Service {
    private Logger logger;
    private Jedis jedis;
    String bootstrapServers;
    String groupId;
    String rqTopic;
    String rsTopic;

    private void setUpRedis() {
        var jedisHost = System.getenv("REDIS_HOST");
        if (jedisHost == null) {
            jedisHost = "localhost";
        }
        var jedisPort = System.getenv("REDIS_PORT");
        if (jedisPort == null) {
            jedisPort = "6379";
        }
        jedis = new Jedis(jedisHost, Integer.parseInt(jedisPort));
    }

    public Service() {
        logger = initLogger();
        logger.info("Starting");
        setUpRedis();
    }

    private void extractEnvVars() {
        groupId = System.getenv("CL_KAFKA_GROUP_ID");
        if (groupId == null) {
            groupId = "cl.workers";
        }

        rqTopic = System.getenv("CL_KAFKA_RQ_TOPIC");
        if (rqTopic == null) {
            rqTopic = "cl.worker.rq_nd";
        }

        rsTopic = System.getenv("CL_KAFKA_RS_TOPIC");
        if (rsTopic == null) {
            rsTopic = "cl.worker.rs_topic";
        }

        bootstrapServers = System.getenv("CL_KAFKA_BOOTSTRAP_SERVERS");
        if (bootstrapServers == null) {
            bootstrapServers = "localhost:29092";
        }
    }

    private KafkaConsumer<String, String> generateConsumer() {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", bootstrapServers);
        properties.setProperty("group.id", groupId);
        properties.setProperty("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("auto.offset.reset","latest");
        return new KafkaConsumer<>(properties);
    }

    private KafkaProducer<String, String> generateProducer() {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", bootstrapServers);
        properties.setProperty("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(properties);
    }

    private Tree extract2DData(String uuid) {
        var rawData = jedis.get(uuid);
        var gson = new Gson();
        return gson.fromJson(rawData, Tree.class);
    }

    private void saveTwoDResponse(String uuid, CopyOnWriteArrayList<CopyOnWriteArrayList<Double>> result) {
        var gson = new Gson();
        jedis.del(uuid);
        jedis.set(uuid, gson.toJson(result));
    }

    private void handleTwoD(String uuid) {
        var data = extract2DData(uuid);
        logger.info("Extract data from redis");
        logger.info("Clustering...");
        var res = data.calculatingResultThreaded();
        logger.info("Save result");
        saveTwoDResponse(uuid, res);
    }

    public void serve() {
        extractEnvVars();

        var consumer = generateConsumer();
        consumer.subscribe(Collections.singleton(rqTopic));
        var producer = generateProducer();

        logger.info("Consumer and producer are generated");

        while (true) {
            logger.info("Selecting messages from kafka...");
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(5000));
            if (records.count() == 0) {
                logger.info("Nothing is received");
                continue;
            }

            for (ConsumerRecord<String, String> record: records) {
                var uuid = record.value();

                logger.info("Cluster point system " + uuid + "...");

                if (Objects.equals(rqTopic, "cl.worker.rq_2d")) {
                    handleTwoD(uuid);
                }

                try {
                    producer.send(new ProducerRecord<>(rsTopic, uuid)).get();
                }
                catch (Exception ex) {
                    logger.info("Cant post message to kafka");
                }
            }
        }
    }
}
