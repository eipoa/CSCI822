/**
 * 
 */
package com.bugtrack.admin.exception;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bugtrack.admin.util.PublicFunction;

/**
 * @author Baoxing Li
 * @version 1.0.0
 * A global handle for exception to support Ajax and common exception
 * @see CoustomErrorInfo
 * @see CoustomJsonException
 */

@ControllerAdvice
@RestController
public class GlobalControllerExceptionHandler {
	public static final String DEFAULT_ERROR_VIEW = "Public/error";

	@Autowired  
	private PublicFunction pf;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Default exception handler for Spring Controller exception 
	 * if the request is via an Ajax method, then the exception will be throw as a CoustomJsonException.
	 *  http://blog.csdn.net/projectarchitect/article/details/42463471
	 * @param req in HttpServletRequest
	 * @param rep in HttpServletResponse
	 * @param e	in Exception
	 * @return out ModelAndView
	 * @throws Exception
	 */
	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, HttpServletResponse rep, Exception e)
			throws Exception {
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
			logger.info("----------- GlobalExceptionHandler, throw ResponseStatus Exceptions!");
			throw e;
		}
		if (this.pf.isAjax()) {
			logger.info("----------- GlobalExceptionHandler, throw Ajax Exceptions!");
			throw new CoustomJsonException(e.getMessage());
		}
		logger.info("----------- GlobalExceptionHandler, common Exceptions!");
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e.getMessage());
		mav.addObject("path", req.getRequestURI());
		mav.addObject("timestamp", new Date().toString());
		mav.addObject("status", rep.getStatus());
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}

	/**
	 * Exception handler for CoustomJsonException class
	 * 
	 * @param req in HttpServletRequest
	 * @param rep in HttpServletResponse
	 * @param e	in CoustomJsonException
	 * @return out CoustomErrorInfo
	 * @throws Exception
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = CoustomJsonException.class)
	@ResponseBody
	public CoustomErrorInfo<String> jsonErrorHandler(HttpServletRequest req, HttpServletResponse rep,
			CoustomJsonException e) throws Exception {
		logger.info("----------- GlobalExceptionHandler, catch Ajax Exceptions!");
		CoustomErrorInfo<String> r = new CoustomErrorInfo<>();
		r.setMessage(e.getMessage());
		r.setCode(CoustomErrorInfo.ERROR);
		r.setData("Some Data");
		r.setUrl(req.getRequestURL().toString());
		return r;
	}
}
