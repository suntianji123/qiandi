package com.qiandi.web.aop;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.qiandi.pojo.QianShangUser;
import com.qiandi.util.JsonUtils;

@Aspect
public class LogAspect
{
	private static final Logger logger = LogManager.getLogger("乾商操作员操作日志");

	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void controller()
	{

	}

	@Before("controller()")
	public void before(JoinPoint joinPoint) throws Throwable
	{

		if (!logger.isInfoEnabled())
		{
			return;
		}
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		QianShangUser qianShangUser = (QianShangUser) session.getAttribute("qianShangUser");
		Long userId = null;
		if (qianShangUser != null)
		{
			userId = qianShangUser.getId();
		}

		Object[] args = joinPoint.getArgs();
		for (int i = 0; i < args.length; i++)
		{
			if (args[i] instanceof ServletRequest)
			{
				args[i] = "request对象";
			} else if (args[i] instanceof ServletResponse)
			{
				args[i] = "response对象";
			} else if (args[i] instanceof MultipartFile)
			{
				args[i] = "MultipartFile对象";
			} else if (args[i] instanceof BindingResult)
			{
				args[i] = "BindingResult对象";
			}
		}

		logger.info("乾商账户id：{}，方法签名：{}，方法参数：{}", userId, joinPoint.getSignature(), JsonUtils.toJson(args));
	}
}
