package com.zouwei.eeis.db;

import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;

public class DatabaseConnection {
  private final MySQLPool mySQLPool;
  public DatabaseConnection(Vertx vertx) {
    MySQLConnectOptions connectOptions = new MySQLConnectOptions()
      .setPort(3306)
      .setHost("127.0.0.1")
      .setDatabase("test")
      .setUser("root")
      .setPassword("root123456");
    PoolOptions poolOptions = new PoolOptions().setMaxSize(10);
    mySQLPool = MySQLPool.pool(vertx, connectOptions, poolOptions);
  }
  public MySQLPool getMySQLPool() {
    return mySQLPool;
  }
}
