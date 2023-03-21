package com.zouwei.eeis;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class TestVerticle extends AbstractVerticle {

  @Override
  public void start() {
    HttpServer server = vertx.createHttpServer();
    Router router = Router.router(vertx);

    router.get("/json").handler(this::handleJsonRequest);

    server.requestHandler(router).listen(8080, http -> {
      if (http.succeeded()) {
        System.out.println("HTTP server started on port 8080");
      } else {
        System.out.println("HTTP server failed to start");
      }
    });
  }

  private void handleJsonRequest(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();
    JsonObject json = new JsonObject()
      .put("message", "Hello, Vert.x!")
      .put("timestamp", System.currentTimeMillis());
    response.putHeader("Content-Type", "application/json");
    response.end(json.encode());
  }
}
