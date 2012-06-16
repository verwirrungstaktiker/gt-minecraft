package gt.general.trigger;

import java.util.Map;

public interface YamlSerializable {

	// TODO abstract class?
	String getLabel ();
	void setLabel(String label);
	
	void setup(Map<String, Object> values);
	
	Map<String, Object> teardown();
}
