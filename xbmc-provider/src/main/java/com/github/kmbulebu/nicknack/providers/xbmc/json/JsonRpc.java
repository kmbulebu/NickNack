package com.github.kmbulebu.nicknack.providers.xbmc.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonRpc {
	
	private String jsonrpc = "2.0";
	private String method;
	// Params is a dynamic map that doesn't fit well into a static model.
	private JsonNode params;
	
	@JsonInclude(Include.NON_EMPTY)
	private String id;
	
	@JsonInclude(Include.NON_EMPTY)
	private String result;
	
	public String getJsonrpc() {
		return jsonrpc;
	}
	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	/*public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}*/
	
	@Override
	public String toString() {
		return "JsonRpc [jsonrpc=" + jsonrpc + ", method=" + method + ", params=" + params + ", id=" + id + "]";
	}
	public JsonNode getParams() {
		return params;
	}
	public void setParams(JsonNode params) {
		this.params = params;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
	

}
