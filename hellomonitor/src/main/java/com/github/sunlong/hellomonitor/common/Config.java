package com.github.sunlong.hellomonitor.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: sunlong Date: 12-12-28 Time: 下午2:29
 */
public class Config {
	public static final String CACHE_NAME = "csCache";

	public static final String ROLE_DEFAULT_NORMAL = "普通用户";

	public static final String USER_GROUP_DEFAULT = "默认用户组";

	public final static String UPLOAD_FOLDER;
	// cloudstack服务器地址
	public final static String CS_BASE_URL;
	// cloudstack api 路径
	public final static String CS_API_PATH;
	// cloudstack api_key
	public final static String CS_API_KEY;
	// cloudstack api 签名
	public final static String CS_SECRET_KEY;
	// 数据返回格式
	public final static String CS_RESPONSE_FORMAT;

    //UDCM url
    public final static String UDCM_URL;

    //UDCM username
    public final static String UDCM_USERNAME;

    //UDCM password
    public final static String UDCM_PASSWORD;

    public final static String UDCM_LOGIN_URL;

    public final static String UDCM_ENVIRONMENT;

	static {
		InputStream in = Config.class.getClassLoader().getResourceAsStream(
				"resources/config.properties");
		Properties properties = new Properties();
		try {
			properties.load(in);
		} catch (IOException e) {
			// TODO: log this exception
		}
		UPLOAD_FOLDER = (String) properties.get("upload_folder");
		CS_BASE_URL = (String) properties.get("cs_base_url");
		CS_API_PATH = (String) properties.getProperty("cs_api_path");
		CS_API_KEY = (String) properties.getProperty("cs_api_key");
		CS_SECRET_KEY = (String) properties.getProperty("cs_api_secret_key");
		CS_RESPONSE_FORMAT = (String) properties.getProperty("cs_response_format");
        UDCM_URL = (String) properties.getProperty("udcm_url");
        UDCM_USERNAME = (String) properties.getProperty("udcm_username");
        UDCM_PASSWORD = (String) properties.getProperty("udcm_password");
        UDCM_LOGIN_URL = UDCM_URL+"/zport/acl_users/cookieAuthHelper/login?__ac_name="+UDCM_USERNAME+"&__ac_password="+UDCM_PASSWORD+"&submitted=true&came_from=";
        UDCM_ENVIRONMENT = (String) properties.getProperty("environment_url");
	}
}
