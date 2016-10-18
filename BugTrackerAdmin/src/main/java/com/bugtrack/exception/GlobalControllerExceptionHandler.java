/**
 * 
 */
package com.bugtrack.exception;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.bugtrack.util.PublicFunction;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Baoxing Li
 * @version 1.0.0 A global handle for exception to support Ajax and common
 *          exception
 * @see CustomErrorInfo
 * @see CustomJsonException
 */

@ControllerAdvice
public class GlobalControllerExceptionHandler {
	public static final String DEFAULT_ERROR_VIEW = "/error";

	@Autowired
	private PublicFunction pf;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return HttpStatus.valueOf(statusCode);
	}

	/**
	 * Default exception handler for Spring Controller exception if the request
	 * is via an Ajax method, then the exception will be throw as a
	 * CoustomJsonException.
	 * http://blog.csdn.net/projectarchitect/article/details/42463471
	 */
//	@ExceptionHandler(value = Exception.class)
//	public ModelAndView defaultErrorHandler(HttpServletRequest req, HttpServletResponse rep, Exception e)
//			throws Exception {
//		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
//			logger.info("----------- MVC GlobalExceptionHandler, throw ResponseStatus Exceptions!");
//			throw e;
//		}
//		if (this.pf.isAjax()) {
//			logger.info("----------- MVC GlobalExceptionHandler, throw Ajax Exceptions!");
//			logger.info("----------- Exception message : " + e.getMessage());
//			throw new CustomJsonException(e.getMessage());
//		}
//		logger.info("----------- MVC GlobalExceptionHandler, common Exceptions!");
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("exception", e.getMessage());
//		mav.addObject("path", req.getRequestURI());
//		mav.addObject("timestamp", new Date().toString());
//		mav.addObject("status", this.getStatus(req));
//		mav.setViewName(DEFAULT_ERROR_VIEW);
//		return mav;
//	}

	/**
	 * Exception handler for CoustomJsonException class
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = CustomJsonException.class)
	@ResponseBody
	public CustomErrorInfo jsonErrorHandler(HttpServletRequest req, HttpServletResponse rep,
			CustomJsonException e) throws Exception {
		logger.info("----------- GlobalExceptionHandler, catch Ajax Exceptions!");
		CustomErrorInfo r = new CustomErrorInfo();
		r.setMessage(e.getMessage());
		r.setCode(CustomErrorInfo.ERROR);
		r.setData(this.getStatus(req).toString());
		r.setUrl(req.getRequestURL().toString());
		return r;
	}

	// only ajax,is not good
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<CustomErrorInfo> exceptionHandler(HttpServletRequest req, HttpServletResponse rep, Exception ex) {
//		CustomErrorInfo error = new CustomErrorInfo();
//		error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//		error.setMessage(ex.getMessage());
//		error.setData(this.getStatus(req).toString());
//		error.setUrl(req.getRequestURL().toString());
//		return new ResponseEntity<CustomErrorInfo>(error, HttpStatus.BAD_REQUEST);
//	}
	
	@ExceptionHandler(Exception.class)
	public void defaultErrorHandler(HttpServletRequest req, 
			HttpServletResponse rep, Exception ex)  throws IOException, ServletException {
//		CustomErrorInfo error = new CustomErrorInfo();
//		error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//		error.setMessage(ex.getMessage());
//		error.setData(this.getStatus(req).toString());
//		error.setUrl(req.getRequestURL().toString());
//		return new ResponseEntity<CustomErrorInfo>(error, HttpStatus.BAD_REQUEST);
		if (this.pf.isAjax()) {
			CustomErrorInfo error = new CustomErrorInfo();
			error.setCode(this.getStatus(req).value());
			error.setMessage(ex.getMessage());
			error.setData(this.getStatus(req).toString());
			error.setUrl(req.getRequestURL().toString());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", false);
			map.put("info", ex.getMessage());
			map.put("data", error);
			map.put("timestamp", new Date().toString());
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(map);
			logger.info("------------------Catch an Ajax exception!   " + jsonString);
			// normailize status to 500
			rep.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			OutputStream out = rep.getOutputStream();
			out.write(jsonString.getBytes());
		} else {
			logger.info("------------------Catch a non-Ajax exception!");
			defaultErrorHandlerForPage(req, rep, ex);
		}
	}
	
	public ModelAndView defaultErrorHandlerForPage(HttpServletRequest req, HttpServletResponse rep, Exception e){
		//maybe forword or redirect
		logger.info("----------- MVC ExceptionHandler, page Exceptions!");
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e.getMessage());
		mav.addObject("path", req.getRequestURI());
		mav.addObject("timestamp", new Date().toString());
		mav.addObject("status", this.getStatus(req));
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}
}
