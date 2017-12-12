package com.song.saber.redis;

import java.util.*;

/**
 * java实现redis expire机制
 */
public class RedisExpire {
    //redis中本来存的是byte[]，这里方便起见用对象
    private Map<String, CacheItem> cache;

    public RedisExpire() {
        this.cache = new HashMap<String, CacheItem>();
    }
    //简单处理，不考虑之前是否有此key
    public void expire(String key, String value, long timeout) {
        CacheItem item = new CacheItem(key, value, System.currentTimeMillis(), timeout);
        cache.put(key, item);
    }
    public static void main(String[] args) {
        final RedisExpire expire = new RedisExpire();

        Random random = new Random();
        //1.添加1千个值，超时时间设置在1000ms到10000ms之间
        for (int i = 0; i < 1000; i++) {
            String strI = String.valueOf(i);
            double next = random.nextDouble();
            long timeout = (long) (1000 + next * 9000);
            expire.expire(strI, strI, timeout);
        }
        System.out.println(expire.getCache().toString());
        //2.添加一个定时任务，检查过期项
        Timer t = new Timer();
        TimerTask task = new InspectTask(expire.getCache());

        t.scheduleAtFixedRate(task, 0, 1000);
    }
    public Map<String, CacheItem> getCache() {
        return cache;
    }
    public void setCache(Map<String, CacheItem> cache) {
        this.cache = cache;
    }
}
