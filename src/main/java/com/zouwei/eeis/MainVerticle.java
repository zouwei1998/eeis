package com.zouwei.eeis;

import com.zouwei.eeis.api.TemperatureHumidityApi;
import com.zouwei.eeis.mqtt.TemperatureHumidityMqttVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception { }

  public static void main(String[] args) {
    Vertx v = Vertx.vertx();
    v.deployVerticle(new MainVerticle());
    v.deployVerticle(new TemperatureHumidityApi());
    v.deployVerticle(new TemperatureHumidityMqttVerticle());
  }
}
