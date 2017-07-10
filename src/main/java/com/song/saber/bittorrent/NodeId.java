package com.song.saber.bittorrent;

import java.math.BigInteger;

class NodeId extends BencodeString {

  private final BigInteger value;

  public NodeId(byte[] bs) {
    super(bs);
    if (bs.length != 20) {
      throw new RuntimeException();
    }

    byte[] dest = new byte[bs.length + 1];
    dest[0] = 0;
    System.arraycopy(bs, 0, dest, 1, bs.length);
    value = new BigInteger(dest);
  }

  public BigInteger getValue() {
    return value;
  }
}