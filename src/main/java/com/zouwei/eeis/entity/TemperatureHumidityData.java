package com.zouwei.eeis.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TemperatureHumidityData {
  private int id;
  private String sensorId;
  private BigDecimal temperature;
  private BigDecimal humidity;
  private LocalDateTime timestamp;

  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getSensorId() {
    return sensorId;
  }
  public void setSensorId(String sensorId) {
    this.sensorId = sensorId;
  }
  public BigDecimal getTemperature() {
    return temperature;
  }
  public void setTemperature(BigDecimal temperature) {
    this.temperature = temperature;
  }
  public BigDecimal getHumidity() {
    return humidity;
  }
  public void setHumidity(BigDecimal humidity) {
    this.humidity = humidity;
  }
  public LocalDateTime getTimestamp() {
    return timestamp;
  }
  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }
}
