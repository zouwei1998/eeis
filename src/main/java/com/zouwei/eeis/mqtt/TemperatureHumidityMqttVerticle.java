package com.zouwei.eeis.mqtt;

import com.zouwei.eeis.db.DatabaseConnection;
import com.zouwei.eeis.db.TemperatureHumidityDataRepository;
import com.zouwei.eeis.entity.TemperatureHumidityData;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.mqtt.MqttClient;
import io.vertx.mqtt.MqttClientOptions;
import io.vertx.mysqlclient.MySQLPool;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TemperatureHumidityMqttVerticle extends AbstractVerticle {
  public TemperatureHumidityDataRepository temperatureHumidityDataRepository;
  public String dht11Topic = "dht11/temperatureAndHumidity";
  public String temperatureAndHumidity;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    MqttClientOptions options = new MqttClientOptions();
    MqttClient mqttClient = MqttClient.create(vertx, options);
    DatabaseConnection databaseConnection = new DatabaseConnection(vertx);
    MySQLPool mySQLPool = databaseConnection.getMySQLPool();
    temperatureHumidityDataRepository = new TemperatureHumidityDataRepository(mySQLPool);
    mqttClient.connect(1883, "192.168.1.112", ar -> {
      if (ar.succeeded()) {
        mqttClient.subscribe(dht11Topic, 0, subscribeResult -> {
          if (subscribeResult.succeeded()) System.out.println("订阅温湿度成功，topic：" + dht11Topic);
        });
        mqttClient.publishHandler(msg -> {
          temperatureAndHumidity = msg.payload().toString();
          JsonObject jsonObject = new JsonObject(temperatureAndHumidity);
          TemperatureHumidityData temperatureHumidityData = new TemperatureHumidityData();
          temperatureHumidityData.setSensorId("1");
          temperatureHumidityData.setTemperature(BigDecimal.valueOf(jsonObject.getDouble("temperature")));
          temperatureHumidityData.setHumidity(BigDecimal.valueOf(jsonObject.getDouble("humidity")));
          temperatureHumidityData.setTimestamp(new Timestamp(System.currentTimeMillis()));
          temperatureHumidityDataRepository.save(temperatureHumidityData);
        });
      } else {
        System.out.println("Failed to connect to MQTT server: " + ar.cause().getMessage());
      }
    });
  }
}
