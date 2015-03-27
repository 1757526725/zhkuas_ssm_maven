package com.zhku.module.validatecode;

import com.zhku.module.validatecode.bo.ValidateCode;


public interface CodePool {
	/**
	 * 补充验证码
	 * @return 
	 */
	public int fillPool();
	/**
	 * 刷新验证码池
	 */
	public void flushAll();
	/**
	 * 清空验证码池
	 */
	public void emptyPool();
	/**
	 * 获取缓存池可用连接
	 * @return 
	 */

	public int getAvailableNum();
	/**
	 * 获取一个可用的验证码的
	 * @return
	 */
	public ValidateCode getValidateCode();
	/**
	 * 移除一个验证码
	 */
	public void removeValidateCode(ValidateCode validateCode);
}
