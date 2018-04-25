package com.song.saber.bittorrent;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Main {

  public static void main(String[] args) throws Exception {
    //218.17.99.85
    // 填写外网ip, 百度一下ip就能看到了
    //final byte[] ip = {218, (byte) 17, (byte) 99, 85};
    final byte[] ip = InetAddress.getByName("218.17.99.85").getAddress();
    // 如果想收到其他节点发送过来的消息必须把外网端口映射到内网端口 这里是内网端口 只要登录路由器做一个端口映射就好
    final int port = 50000;
    // 节点数 这里表示从50000到50255这256个端口将会被监听
    final int nodeCount = 256;
    final int processorCount = Runtime.getRuntime().availableProcessors();
    final String id = "javadht:541241544";

    List<byte[]> ips = new ArrayList<byte[]>();
    List<Integer> ports = new ArrayList<Integer>();
    List<String> ids = new ArrayList<String>();
    for (int i = 0; i < nodeCount; i++) {
      if (i % (nodeCount / processorCount) == 0 && ips.size() != 0) {
        new DHTServer(ips, ports, ids);
        ips = new ArrayList<byte[]>();
        ports = new ArrayList<Integer>();
        ids = new ArrayList<String>();
      }
      ips.add(ip);
      ports.add(port + i);
      ids.add((i + 740) + id);
    }
    if (ips.size() != 0) {
      new DHTServer(ips, ports, ids);
    }
  }
}

