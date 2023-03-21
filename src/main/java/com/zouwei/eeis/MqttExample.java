package com.zouwei.eeis;

import io.vertx.core.Vertx;
import io.vertx.mqtt.MqttClient;
import io.vertx.mqtt.MqttClientOptions;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Tuple;

import java.sql.Timestamp;

public class MqttExample {
  public static void main(String[] args) {
//    Vertx v = Vertx.vertx();
//    MqttClientOptions options = new MqttClientOptions();
//    MqttClient mqttClient = MqttClient.create(v, options);
//    MySQLConnectOptions connectOptions = new MySQLConnectOptions()
//      .setPort(3306)
//      .setHost("127.0.0.1")
//      .setDatabase("test")
//      .setUser("root")
//      .setPassword("root123456");
//    PoolOptions poolOptions = new PoolOptions()
//      .setMaxSize(5);
//    MySQLPool mySQLPool = MySQLPool.pool(v, connectOptions, poolOptions);
//    mqttClient.connect(1883, "192.168.1.112", ar -> {
//      if (ar.succeeded()) {
//        mqttClient.subscribe("esp/dht11/temperature", 0, subscribeResult -> {
//          if (subscribeResult.succeeded()) System.out.println("订阅温度成功");
//        });
//        mqttClient.subscribe("esp/dht11/humidity", 0, subscribeResult -> {
//          if (subscribeResult.succeeded()) System.out.println("订阅湿度成功");
//        });
//        mqttClient.publishHandler(message -> {   // 处理收到的消息
//          String msg = message.payload().toString();
//          System.out.println("msg:" + msg);
//          mySQLPool
//          .preparedQuery("INSERT INTO temperaturehumiditydata (sensor_id, temperature, humidity, timestamp) VALUES (?, ?, ?, ?)")
//          .execute(Tuple.of("1",
//            msg,
//            msg,
//            new Timestamp(System.currentTimeMillis())), arr -> {
//            if (arr.succeeded()) {
//              RowSet<Row> rows = arr.result();
//              System.out.println("温湿度数据写入数据库，影响记录数：" + rows.rowCount());
//            } else {
//              System.out.println("Failure: " + arr.cause().getMessage());
//            }
//          });
//        });
//      } else {
//        System.out.println("Failed to connect to MQTT server: " + ar.cause().getMessage());
//      }
//    });
  }
}
