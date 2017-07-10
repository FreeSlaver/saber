package com.song.saber.bittorrent;

abstract class Callbacker {

  private int transactionId;

  public abstract void execute(BencodeMap response);

  public void destory() {
  }

  public final void setTransactionId(int transactionId) {
    this.transactionId = transactionId;
  }

  public final int getTransactionId() {
    return transactionId;
  }
}