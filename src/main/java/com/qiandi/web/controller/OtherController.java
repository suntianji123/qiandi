package com.qiandi.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qiandi.pojo.QianShangUser;
import com.qiandi.service.QianShangUserService;
import com.qiandi.util.AjaxResult;

@Controller
public class OtherController
{
	@Autowired
	private QianShangUserService qianShangUserService;

	@RequestMapping("/")
	public @ResponseBody AjaxResult index(HttpServletRequest req)
	{
		HttpSession session = req.getSession();
		QianShangUser qianShangUser = (QianShangUser) req.getSession().getAttribute("qianShangUser");
		if (qianShangUser == null)
		{
			qianShangUser = qianShangUserService.selectOne(373117207938334721L);
		}

		session.setAttribute("qianShangUser", qianShangUser);
		session.setMaxInactiveInterval(60 * 60 * 24 * 30);

		return AjaxResult.successInstance("ok");
	}
}
