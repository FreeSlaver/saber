package com.song.saber.redis;

public class CacheItem {

    private String key;

    private String value;
    //放入时间
    private long bornTime;

    private long timeout;

    public CacheItem() {
    }

    public CacheItem(String key, String value, long bornTime, long timeout) {
        this.key = key;
        this.value = value;
        this.bornTime = bornTime;
        this.timeout = timeout;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getBornTime() {
        return bornTime;
    }

    public void setBornTime(long bornTime) {
        this.bornTime = bornTime;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("key:").append(key)
                .append(",values:").append(value)
                .append(",bornTime:").append(bornTime)
                .append(",timeout:").append(timeout).toString();
    }
}
