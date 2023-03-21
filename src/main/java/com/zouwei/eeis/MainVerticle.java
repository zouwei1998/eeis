package com.zouwei.eeis;

import com.zouwei.eeis.db.DatabaseConnection;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.mqtt.MqttClient;
import io.vertx.mqtt.MqttClientOptions;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.*;

import java.sql.Timestamp;

public class MainVerticle extends AbstractVerticle {

  public String sql = "INSERT INTO temperaturehumiditydata (sensor_id, temperature, humidity, timestamp) VALUES (?, ?, ?, ?)";
  public String temperatureTopic = "esp/dht11/temperature";
  public String humidityTopic = "esp/dht11/humidity";
  public String temperature;
  public String humidity;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    MqttClientOptions options = new MqttClientOptions();
    MqttClient mqttClient = MqttClient.create(vertx, options);
    DatabaseConnection databaseConnection = new DatabaseConnection(vertx);
    MySQLPool mySQLPool = databaseConnection.getMySQLPool();
    mqttClient.connect(1883, "192.168.1.112", ar -> {
      if (ar.succeeded()) {
        mqttClient.subscribe(temperatureTopic, 0, subscribeResult -> {
          if (subscribeResult.succeeded()) System.out.println("订阅温度成功");
        });
        mqttClient.subscribe(humidityTopic, 0, subscribeResult -> {
          if (subscribeResult.succeeded()) System.out.println("订阅湿度成功");
        });
        mqttClient.publishHandler(msg -> {
          if(msg.topicName().equals(temperatureTopic)) temperature = msg.payload().toString();
          if(msg.topicName().equals(humidityTopic)) humidity = msg.payload().toString();
          System.out.println(temperature);
          System.out.println(humidity);
          if(temperature != null && humidity != null) {
            mySQLPool
              .preparedQuery(sql)
              .execute(Tuple.of("1",
                temperature,
                humidity,
                new Timestamp(System.currentTimeMillis())), arr -> {
                if (arr.succeeded()) {
                  RowSet<Row> rows = arr.result();
                  System.out.println("温湿度数据写入数据库，影响记录数：" + rows.rowCount());
                } else {
                  System.out.println("Failure: " + arr.cause().getMessage());
                }
              });
          }
        });
      } else {
        System.out.println("Failed to connect to MQTT server: " + ar.cause().getMessage());
      }
    });
  }

  public static void main(String[] args) {
    Vertx v = Vertx.vertx();
    v.deployVerticle(new MainVerticle());
    v.deployVerticle(new TestVerticle());
  }
}
