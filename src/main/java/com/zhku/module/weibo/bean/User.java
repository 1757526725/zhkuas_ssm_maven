/*
Copyright (c) 2007-2009, Yusuke Yamamoto
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
 * Neither the name of the Yusuke Yamamoto nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY Yusuke Yamamoto ``AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Yusuke Yamamoto BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.zhku.module.weibo.bean;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;


/**
 * A data class representing Basic user information element
 */
public class User implements java.io.Serializable {

	private static final long serialVersionUID = -332738032648843482L;
	private String id;                      //用户UID
	private String screenName;            //微博昵称
	private String name;                  //友好显示名称，如Bill Gates,名称中间的空格正常显示(此特性暂不支持)
	private int province;                 //省份编码（参考省份编码表）
	private int city;                     //城市编码（参考城市编码表）
	private String location;              //地址
	private String description;           //个人描述
	private String url;                   //用户博客地址
	private String profileImageUrl;       //自定义图像
	private String userDomain;            //用户个性化URL
	private String gender;                //性别,m--男，f--女,n--未知
	private int followersCount;           //粉丝数
	private int friendsCount;             //关注数
	private int statusesCount;            //微博数
	private int favouritesCount;          //收藏数
	private Date createdAt;               //创建时间
	private boolean following;            //保留字段,是否已关注(此特性暂不支持)
	private boolean verified;             //加V标示，是否微博认证用户
	private int verifiedType;             //认证类型
	private boolean allowAllActMsg;       //是否允许所有人给我发私信
	private boolean allowAllComment;      //是否允许所有人对我的微博进行评论
	private boolean followMe;             //此用户是否关注我
	private String avatarLarge;           //大头像地址
	private int onlineStatus;             //用户在线状态
	private int biFollowersCount;         //互粉数
	private String remark;                //备注信息，在查询用户关系时提供此字段。
	private String lang;                  //用户语言版本
	private String verifiedReason;		  //认证原因
	private String weihao;				  //微號
	private String statusId;
	public String getVerified_reason() {
		return verifiedReason;
	}
	public void setVerified_reason(String verifiedReason) {
		this.verifiedReason = verifiedReason;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setProvince(int province) {
		this.province = province;
	}
	public void setCity(int city) {
		this.city = city;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	public void setUserDomain(String userDomain) {
		this.userDomain = userDomain;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setFollowersCount(int followersCount) {
		this.followersCount = followersCount;
	}
	public void setFriendsCount(int friendsCount) {
		this.friendsCount = friendsCount;
	}
	public void setStatusesCount(int statusesCount) {
		this.statusesCount = statusesCount;
	}
	public void setFavouritesCount(int favouritesCount) {
		this.favouritesCount = favouritesCount;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public void setFollowing(boolean following) {
		this.following = following;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public void setVerifiedType(int verifiedType) {
		this.verifiedType = verifiedType;
	}
	public void setAllowAllActMsg(boolean allowAllActMsg) {
		this.allowAllActMsg = allowAllActMsg;
	}
	public void setAllowAllComment(boolean allowAllComment) {
		this.allowAllComment = allowAllComment;
	}
	public void setFollowMe(boolean followMe) {
		this.followMe = followMe;
	}
	public void setAvatarLarge(String avatarLarge) {
		this.avatarLarge = avatarLarge;
	}
	public void setOnlineStatus(int onlineStatus) {
		this.onlineStatus = onlineStatus;
	}
	public void setBiFollowersCount(int biFollowersCount) {
		this.biFollowersCount = biFollowersCount;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public String getWeihao() {
		return weihao;
	}
	public void setWeihao(String weihao) {
		this.weihao = weihao;
	}

	public String getVerifiedReason() {
		return verifiedReason;
	}
	public void setVerifiedReason(String verifiedReason) {
		this.verifiedReason = verifiedReason;
	}
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	public String getUrl() {
		return url;
	}
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	public int getVerifiedType() {
		return verifiedType;
	}
	public boolean isAllowAllActMsg() {
		return allowAllActMsg;
	}
	public boolean isAllowAllComment() {
		return allowAllComment;
	}
	public boolean isFollowMe() {
		return followMe;
	}
	public String getAvatarLarge() {
		return avatarLarge;
	}
	public int getOnlineStatus() {
		return onlineStatus;
	}
	public int getBiFollowersCount() {
		return biFollowersCount;
	}
	/*package*/
	public User(JSONObject json) throws WeiboException {
		super();
		init(json);
	}

	public User(String res)throws WeiboException {
		JSONObject json =JSONObject.parseObject(res);
		init(json);
	}
	private void init(JSONObject json) throws WeiboException {
		if(json!=null){
			try {
				id = json.getString("id");
				screenName = json.getString("screen_name");
				name = json.getString("name");
				province = json.getInteger("province");
				city = json.getInteger("city");
				location = json.getString("location");
				description = json.getString("description");
				url = json.getString("url");
				profileImageUrl = json.getString("profile_image_url");
				userDomain = json.getString("domain");
				gender = json.getString("gender");
				followersCount = json.getInteger("followers_count");
				friendsCount = json.getInteger("friends_count");
				favouritesCount = json.getInteger("favourites_count");
				statusesCount = json.getInteger("statuses_count");
				verifiedType = json.getInteger("verified_type"); 
				verifiedReason = json.getString("verified_reason");
				allowAllActMsg = json.getBoolean("allow_all_act_msg");
				allowAllComment = json.getBoolean("allow_all_comment");
				followMe = json.getBoolean("follow_me");
				avatarLarge = json.getString("avatar_large");
				onlineStatus = json.getInteger("online_status");
				statusId = json.getString("status_id");
				biFollowersCount = json.getInteger("bi_followers_count");
				if(!json.getString("remark").isEmpty()){					
					remark = json.getString("remark");
				}
				lang = json.getString("lang");
				weihao = json.getString("weihao");
			} catch (JSONException jsone) {
				throw new WeiboException(jsone.getMessage() + ":" + json.toString(), jsone);
			}
		}
	}
	
	
	public String getId() {
		return id;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getName() {
		return name;
	}

	public int getProvince() {
		return province;
	}

	public int getCity() {
		return city;
	}

	public String getLocation() {
		return location;
	}

	public String getDescription() {
		return description;
	}

	public URL getProfileImageURL() {
		try {
			return new URL(profileImageUrl);
		} catch (MalformedURLException ex) {
			return null;
		}
	}

	public URL getURL() {
		try {
			return new URL(url);
		} catch (MalformedURLException ex) {
			return null;
		}
	}

	public String getUserDomain() {
		return userDomain;
	}

	public String getGender() {
		return gender;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public int getFriendsCount() {
		return friendsCount;
	}

	public int getStatusesCount() {
		return statusesCount;
	}

	public int getFavouritesCount() {
		return favouritesCount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public boolean isFollowing() {
		return following;
	}

	public boolean isVerified() {
		return verified;
	}

	public int getverifiedType() {
		return verifiedType;
	}

	public boolean isallowAllActMsg() {
		return allowAllActMsg;
	}

	public boolean isallowAllComment() {
		return allowAllComment;
	}

	public boolean isfollowMe() {
		return followMe;
	}

	public String getavatarLarge() {
		return avatarLarge;
	}

	public int getonlineStatus() {
		return onlineStatus;
	}


	public int getbiFollowersCount() {
		return biFollowersCount;
	}

	public String getRemark() {
		return remark;
	}

	public String getLang() {
		return lang;
	}


	


}
