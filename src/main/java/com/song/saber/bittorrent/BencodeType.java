package com.song.saber.bittorrent;

interface BencodeType {
  int getLength();

  int getTotalLength();

  byte[] getData();

  byte[] getTotalData();
}