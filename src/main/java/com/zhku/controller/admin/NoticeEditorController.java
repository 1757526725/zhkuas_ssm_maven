package com.zhku.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 公告设置页面
 * @author JackCan
 *
 */
@Controller
@RequestMapping("/admin/notice")
public class NoticeEditorController {
	@RequestMapping(value={"","/editor"})
	public String show(){
		return "admin_notice";
	}
}
