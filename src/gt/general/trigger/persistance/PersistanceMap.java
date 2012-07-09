package gt.general.trigger.persistance;

import static com.google.common.collect.Maps.*;

import java.util.Map;


public class PersistanceMap {

	private Map<String, Object> map;

	public PersistanceMap() {
		map = newHashMap();
	}
	public PersistanceMap(final Map<String, Object> map) {
		this.map = map;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(final String key) {
		return (T) map.get(key);
	}
	
	public void put(final String key, final Object value) {
		map.put(key, value);
	}

}
