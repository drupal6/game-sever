package until;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ParamObject extends HashMap<String, Object> {
    /**
     * 
     */
    private static final long serialVersionUID = -3547594935968099444L;
    private String typeName = "";

    public ParamObject() {
        this("");
    }

    public ParamObject(String typeName) {
        super(8);
        if (typeName == null) {
            typeName = "";
        }
        this.typeName = typeName.trim();
    }

    public ParamObject(Map<String, Object> map) {
        super(map);
    }

    public ParamObject(Object... params) {
        for (int i = 0; i < params.length; i += 2) {
            this.put((String) params[i], params[i + 1]);
        }
    }

    public String getTypeName() {
        return this.typeName;
    }

    public boolean isTypedObject() {
        return this.typeName != null && !this.typeName.isEmpty();
    }

    /**
     * 
     */
    @Override
    public ParamObject clone() {
        return new ParamObject(this);
    }

    public Number getNumber(String key, Number def) {
        Object value = get(key);
        if (value == null) {
            return def;
        }
        if (value instanceof Number) {
            return (Number) value;
        }
        if (value instanceof String) {
            try {
                return Long.parseLong((String) value);
            } catch (Exception e) {
                try {
                    return Double.parseDouble((String) value);
                } catch (Exception ex) {
                    return def;
                }
            }
        }
        return def;
    }

    public int getInt(String key) {
        return getNumber(key, 0).intValue();
    }

    public int getInt(String key, int def) {
        return getNumber(key, def).intValue();
    }

    public long getLong(String key) {
        return getNumber(key, 0L).longValue();
    }

    public long getLong(String key, long def) {
        return getNumber(key, def).longValue();
    }

    public String getString(String key) {
        return (String) get(key);
    }

    public Date getTimestamp(String key) {
        Object value = get(key);
        if (value == null) {
            return null;
        }
        if (value instanceof Date) {
            return (Date) value;
        }
        if (value instanceof Number) {
            long ts = ((Number) value).longValue();
            if (ts == 0) {
                return null;
            }
            return new Date(ts);
        }
        return null;
    }

    public boolean getBoolean(String key) {
        Object value = get(key);
        if (value == null) {
            return false;
        }
        if (value instanceof Boolean) {
            return ((Boolean) value).booleanValue();
        } else if (value instanceof Number) {
            return ((Number) value).intValue() != 0;
        } else if (value instanceof String) {
            return ((String) value).length() > 0;
        } else {
            return true;
        }
    }

    public ParamObject getASObject(String key) {
        return (ParamObject) get(key);
    }
}
