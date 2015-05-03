package com.zhku.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhku.bean.Notice;
import com.zhku.service.db.INoticeService;
import com.zhku.service.db.ITermService;

/**
 * 公告设置页面
 * @author JackCan
 *
 */
@Controller
@RequestMapping("/admin/notice")
public class NoticeEditorController {
	
	@Autowired
	private INoticeService noticeService;
	@Autowired
	private ITermService termService;
	@RequestMapping(value={"","/editor"})
	public String show(HttpServletRequest request){
		Notice notice = noticeService.getNoticeByTermNo(termService.getCurrentTerm().getNo());
		request.setAttribute("notice", notice);
		return "admin_notice";
	}

	public INoticeService getNoticeService() {
		return noticeService;
	}

	public void setNoticeService(INoticeService noticeService) {
		this.noticeService = noticeService;
	}

	public ITermService getTermService() {
		return termService;
	}

	public void setTermService(ITermService termService) {
		this.termService = termService;
	}
}
