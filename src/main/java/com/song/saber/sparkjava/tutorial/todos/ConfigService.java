package com.sparkjava.tutorial.todos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by 00013708 on 2017/6/21.
 */
@Service
public class ConfigService {

  @Value("${server.ip}")
  private String serverIp;
  @Value("${server.port}")
  private int serverPort;

  @Value("${server.maxThreads}")
  private int maxThreads;
  @Value("${server.minThreads}")
  private int minThreads;
  @Value("${server.idleTimeoutMillis}")
  private int idleTimeoutMillis;

  public String getServerIp() {
    return serverIp;
  }

  public int getServerPort() {
    return serverPort;
  }

  public int getMaxThreads() {
    return maxThreads;
  }

  public int getMinThreads() {
    return minThreads;
  }

  public int getIdleTimeoutMillis() {
    return idleTimeoutMillis;
  }
}
