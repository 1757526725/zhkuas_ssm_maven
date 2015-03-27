package com.zhku.module.weibo;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import zhku.jackcan.webCrawler.FetchUrl;
import zhku.jackcan.webCrawler.FetchUrlFactory;
import zhku.jackcan.webCrawler.exception.FetchTimeoutException;
import com.alibaba.fastjson.JSONException;
import com.zhku.module.weibo.bean.AccessToken;
import com.zhku.module.weibo.bean.User;
import com.zhku.module.weibo.bean.WeiboException;
import com.zhku.utils.WebConfigUtils;
import com.zhku.web.Constants;

public class WeiBoUserMgm {
	private FetchUrl fetchUrl = FetchUrlFactory.getFetchurl();
	private static Logger logger = Logger.getLogger(WeiBoUserMgm.class.getName());
	public AccessToken getAccessTokenByCode(String code) throws WeiboException, JSONException{
		Map<String,String> params =new HashMap<String,String>();
		params.put("client_id", WebConfigUtils.getValue("client_ID"));
		params.put("client_secret", WebConfigUtils.getValue("client_SERCRET"));
		params.put("grant_type", "authorization_code");
		params.put("code",code);
		params.put("redirect_uri", new StringBuilder().append(WebConfigUtils.getValue("rz_host")).append("/").append(WebConfigUtils.getValue("redirect_URI")).toString());
		fetchUrl.setPostData(params);
		String res = null;
		try {
			res = fetchUrl.post(WebConfigUtils.getValue("accessTokenURL"),Constants.MAX_FETCHURL_COUNT);
		} catch (FetchTimeoutException e) {
//			e.printStackTrace();
			logger.info(e.getMessage());
		}
//		String res =new HttpClientUtils().sendPostSSLRequest(WebConfigUtils.getValue("accessTokenURL"), params);
		return  new AccessToken(res);
	}
	
	public User getWeiboUser(AccessToken accessToken , String uid) throws WeiboException, JSONException{
		String url ="https://api.weibo.com/2/users/show.json?access_token="+accessToken.getAccessToken()+"&uid="+uid+"&source="+WebConfigUtils.getValue("client_ID");
		String res = null;
		try {
			res = fetchUrl.get(url,Constants.MAX_FETCHURL_COUNT);
		} catch (FetchTimeoutException e) {
//			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return new User(res);
	}
}