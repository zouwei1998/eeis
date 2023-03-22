package com.zouwei.eeis.api;

import com.zouwei.eeis.db.DatabaseConnection;
import com.zouwei.eeis.db.TemperatureHumidityDataRepository;
import com.zouwei.eeis.entity.TemperatureHumidityData;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.mysqlclient.MySQLPool;

import java.util.List;

public class TemperatureHumidityApi extends AbstractVerticle {
  public TemperatureHumidityDataRepository temperatureHumidityDataRepository;
  @Override
  public void start() {
    DatabaseConnection databaseConnection = new DatabaseConnection(vertx);
    MySQLPool mySQLPool = databaseConnection.getMySQLPool();
    temperatureHumidityDataRepository = new TemperatureHumidityDataRepository(mySQLPool);
    Router router = Router.router(vertx);
    router.get("/api/th").handler(this::handleGetTemperatureHumidity);
    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080, http -> {
        if (http.succeeded()) {
          System.out.println("HTTP server started on port 8080");
        } else {
          System.out.println("HTTP server failed to start");
        }
      });
  }
  private void handleGetTemperatureHumidity(RoutingContext context) {
    Future<List<TemperatureHumidityData>> temperatureHumidityDataList = temperatureHumidityDataRepository.findLastN(50);
    JsonArray jsonArray = new JsonArray();
    temperatureHumidityDataList.onSuccess(dataList -> {
      for(TemperatureHumidityData t : dataList){
        JsonObject jsonObject = new JsonObject()
          .put("sensor_id", t.getSensorId())
          .put("temperature", t.getTemperature())
          .put("humidity", t.getHumidity())
          .put("timestamp", Long.toString(t.getTimestamp().getTime()));
        jsonArray.add(jsonObject);
      }
      context.response()
        .putHeader("Content-Type", "application/json")
        .putHeader("Access-Control-Allow-Origin", "*")
        .putHeader("Access-Control-Allow-Methods", "GET")
        .putHeader("Access-Control-Allow-Headers", "Content-Type")
        .end(jsonArray.encodePrettily());
    }).onFailure(cause -> {
      context.response()
        .setStatusCode(500)
        .putHeader("Content-Type", "text/plain")
        .end("Error: " + cause.getMessage());
    });
  }
}
