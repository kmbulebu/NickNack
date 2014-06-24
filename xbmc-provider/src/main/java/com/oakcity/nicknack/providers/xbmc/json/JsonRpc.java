package com.oakcity.nicknack.providers.xbmc.json;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonRpc {
	
	private String jsonrpc;
	private String method;
	// Params is a dynamic map that doesn't fit well into a static model.
	private JsonNode params;
	//private Map<String, Object> params;
	
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
		return "JsonRpc [jsonrpc=" + jsonrpc + ", method=" + method + ", params=" + params + "]";
	}
	public JsonNode getParams() {
		return params;
	}
	public void setParams(JsonNode params) {
		this.params = params;
	}
	
	
	

}
