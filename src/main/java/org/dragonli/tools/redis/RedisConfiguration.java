package org.dragonli.tools.redis;



import java.util.List;


public class RedisConfiguration {

	private String mode = "single";
	private List<String> nodes;

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<String> getNodes() {
		return nodes;
	}

	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}

}
