package gt.general.trigger;

import java.util.Map;

public interface YamlSerializable {

	
	void setup(Map<String, Object> values);
	
	Map<String, Object> dispose();
}
