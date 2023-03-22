package com.zouwei.eeis.db;

import com.zouwei.eeis.entity.TemperatureHumidityData;
import io.vertx.core.Future;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TemperatureHumidityDataRepository {
  private final MySQLPool mySQLPool;
  public TemperatureHumidityDataRepository(MySQLPool mySQLPool) {
    this.mySQLPool = mySQLPool;
  }
  public Future<List<TemperatureHumidityData>> findAll() {
    String query = "SELECT * FROM temperaturehumiditydata";
    return mySQLPool.query(query).execute()
      .map(rows -> {
        List<TemperatureHumidityData> dataList = new ArrayList<>();
        for (Row row : rows) {
          dataList.add(rowToTemperatureHumidityData(row));
        }
        return dataList;
      });
  }
  public Future<List<TemperatureHumidityData>> findLastN(int n) {
    String query = "SELECT * FROM temperaturehumiditydata ORDER BY timestamp DESC LIMIT ?";
    return mySQLPool.preparedQuery(query).execute(Tuple.of(n))
      .map(rows -> {
        List<TemperatureHumidityData> dataList = new ArrayList<>();
        for (Row row : rows) {
          dataList.add(rowToTemperatureHumidityData(row));
        }
        return dataList;
      });
  }
  public Future<Void> save(TemperatureHumidityData data) {
    String query = "INSERT INTO temperaturehumiditydata (sensor_id, temperature, humidity, timestamp) VALUES (?, ?, ?, ?)";
    return mySQLPool.preparedQuery(query).execute(Tuple.of(data.getSensorId(), data.getTemperature(), data.getHumidity(), data.getTimestamp()))
      .mapEmpty();
  }
  public Future<Void> update(TemperatureHumidityData data) {
    String query = "UPDATE temperaturehumiditydata SET sensor_id = ?, temperature = ?, humidity = ?, timestamp = ? WHERE id = ?";
    return mySQLPool.preparedQuery(query).execute(Tuple.of(data.getSensorId(), data.getTemperature(), data.getHumidity(), data.getTimestamp(), data.getId()))
      .mapEmpty();
  }
  public Future<Void> delete(int id) {
    String query = "DELETE FROM temperaturehumiditydata WHERE id = ?";
    return mySQLPool.preparedQuery(query).execute(Tuple.of(id))
      .mapEmpty();
  }
  private TemperatureHumidityData rowToTemperatureHumidityData(Row row) {
    TemperatureHumidityData data = new TemperatureHumidityData();
    data.setId(row.getInteger("id"));
    data.setSensorId(row.getString("sensor_id"));
    data.setTemperature(row.getBigDecimal("temperature"));
    data.setHumidity(row.getBigDecimal("humidity"));
    data.setTimestamp(Timestamp.valueOf(row.getLocalDateTime("timestamp")));
    return data;
  }
}
