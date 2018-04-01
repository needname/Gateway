/*
 * Copyright (c) 2018 by Duong Thai Minh.
 * All rights reserved.
 */

package hcmut.co3003.server;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DuongThaiMinh
 */
public class MemoryDataManager {
    private Map<String, byte[]> database = new HashMap<>();

    public boolean createDatabase() {
        this.database = new HashMap<>();
        return true;
    }

    public boolean dropDatabase() {
        this.database.clear();
        return true;
    }

    public byte[] get(String key) {
        return this.database.get(key);
    }

    public boolean set(String key, String value) {
        return (this.database.put(key, value.getBytes()) != null);
    }

    public boolean set(String key, byte[] value) {
        return (this.database.put(key, value) != null);
    }

}
