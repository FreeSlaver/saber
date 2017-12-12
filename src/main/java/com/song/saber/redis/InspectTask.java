package com.song.saber.redis;

import java.util.*;

/**
 * Created by 00013708 on 2017/11/8.
 */
public class InspectTask extends TimerTask {
    private static final int RANDOM_PICK_NUM = 20;
    private static final double EXPIRE_OVER_RATIO = 0.25;
    private static final int EXPIRE_OVER_NUM = (int) (RANDOM_PICK_NUM * EXPIRE_OVER_RATIO);
    private Map<String, CacheItem> cache;

    public InspectTask(Map<String, CacheItem> cache) {
        this.cache = cache;
    }

    public void run() {
        List<CacheItem> list = randomPick();
        int expiredNum = inspect(list);
        if (expiredNum >= EXPIRE_OVER_NUM) {
            run();
        }
    }
    private List<CacheItem> randomPick() {
        List<CacheItem> list = new ArrayList<CacheItem>(cache.values());
        Collections.shuffle(list);
        return list.subList(0, RANDOM_PICK_NUM);
    }

    private int inspect(List<CacheItem> randomPickedItems) {
        if (randomPickedItems == null || randomPickedItems.isEmpty()) {
            throw new IllegalArgumentException("cache items null");
        }
        Iterator<CacheItem> iterator = randomPickedItems.iterator();
        int expiredItemCounter = 0;
        while (iterator.hasNext()) {
            CacheItem item = iterator.next();
            if (hasExpired(item)) {
                cache.remove(item.getKey());
                System.out.println("key:" + item.getKey() + ",value:" + item.getValue() + ",expired!");
                expiredItemCounter++;
            }
        }
        return expiredItemCounter;
    }

    private boolean hasExpired(CacheItem item) {
        if (item == null) {
            return false;
        }
        long bornTime = item.getBornTime();
        long timeout = item.getTimeout();
        long now = System.currentTimeMillis();
        if (now >= bornTime + timeout) {
            return true;
        } else {
            return false;
        }
    }

    public Map<String, CacheItem> getCache() {
        return cache;
    }

    public void setCache(Map<String, CacheItem> cache) {
        this.cache = cache;
    }
}
