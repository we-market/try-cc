package com.wemarket.cc.web.controller;

import com.wemarket.cc.common.dto.BaseDTO;
import com.wemarket.cc.common.dto.BaseResponseDTO;
import com.wemarket.cc.common.dto.BizErrors;
import com.wemarket.cc.common.dto.WebMessage;
import com.wemarket.cc.common.utils.BaseResponseStatus;
import com.wemarket.cc.common.utils.ExecServiceTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author felixyechen
 *
 */
public class CcBaseController extends AbstractBaseController {

    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    /**
  	 * 超时时间
  	 */
  	protected long timeout = 6000;
    
    @Autowired
    @Qualifier("frontTaskExecutor")
    private ThreadPoolTaskExecutor frontTaskExecutor;


    public void setFrontTaskExecutor(ThreadPoolTaskExecutor frontTaskExecutor) {
        this.frontTaskExecutor = frontTaskExecutor;
    }

    @Override
    protected ThreadPoolTaskExecutor getFrontTaskExecutor() {
        return this.frontTaskExecutor;
    }

    protected <E extends BaseDTO> BaseResponseDTO preService(E requestDto,
                                                             HttpServletRequest httpRequest, HttpServletResponse httpResponse, Method callerMethod,
                                                             String requestUri) {
        return super.preService(requestDto, httpRequest, httpResponse, callerMethod, requestUri);
    }

    @Override
    protected <E extends BaseDTO, T> void postService(E requestDto,
        T responseDto, HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse, Method callerMethod, String requestUri, BizErrors errors) {

    }

    protected <T> WebMessage<T> toWebMessage(T t) {
  		WebMessage<T> webMessage = new WebMessage<T>();
  		webMessage.setResult(t);
  		webMessage.setResponseStatus(BaseResponseStatus.SUCCESS);
  		return webMessage;
  	}

	protected <E extends BaseDTO, T> DeferredResult<T> execute(
			HttpServletRequest httpServletRequest, HttpServletResponse httpServlvetResponse,
			E requestDto, long timeoutMilliSeconds, ExecServiceTemplate<E, T> template) {
				return this.execute(httpServletRequest, httpServlvetResponse, requestDto, timeoutMilliSeconds, template, null, null);
	}

}
