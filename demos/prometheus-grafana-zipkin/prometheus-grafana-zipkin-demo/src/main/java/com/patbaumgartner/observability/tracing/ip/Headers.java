package com.patbaumgartner.observability.tracing.ip;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Headers {

	@JsonProperty("Host")
	public String host;

	@JsonProperty("User-Agent")
	public String userAgent;

	@JsonProperty("Accept")
	public String accept;

	@JsonProperty("Accept-Encoding")
	public String acceptEncoding;

	@JsonProperty("Accept-Language")
	public String acceptLanguage;

	@JsonProperty("Cookie")
	public String cookie;

	@JsonProperty("Dnt")
	public String dnt;

	@JsonProperty("Priority")
	public String priority;

	@JsonProperty("Sec-Ch-Ua")
	public String secChUa;

	@JsonProperty("Sec-Ch-Ua-Mobile")
	public String secChUaMobile;

	@JsonProperty("Sec-Ch-Ua-Platform")
	public String secChUaPlatform;

	@JsonProperty("Sec-Fetch-Dest")
	public String secFetchDest;

	@JsonProperty("Sec-Fetch-Mode")
	public String secFetchMode;

	@JsonProperty("Sec-Fetch-Site")
	public String secFetchSite;

	@JsonProperty("Sec-Fetch-User")
	public String secFetchUser;

	@JsonProperty("Sec-Gpc")
	public String secGpc;

	@JsonProperty("Upgrade-Insecure-Requests")
	public String upgradeInsecureRequests;

}
