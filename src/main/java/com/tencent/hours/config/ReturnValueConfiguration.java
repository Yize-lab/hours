package com.tencent.hours.config;

import com.tencent.hours.common.CommonRsp;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ReturnValueConfiguration implements InitializingBean {

    @Autowired
    RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Override
    public void afterPropertiesSet() {
        List<HandlerMethodReturnValueHandler> unmodifiableList = requestMappingHandlerAdapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> list = new ArrayList<>(unmodifiableList.size());
        for (HandlerMethodReturnValueHandler returnValueHandler : unmodifiableList) {
            if (returnValueHandler instanceof RequestResponseBodyMethodProcessor) {
                list.add(new ResultWarpReturnValueHandler(returnValueHandler));
            } else {
                list.add(returnValueHandler);
            }
        }
        requestMappingHandlerAdapter.setReturnValueHandlers(list);
    }
}

class ResultWarpReturnValueHandler implements HandlerMethodReturnValueHandler {

    public static final String PROMETHEUS_STR = "prometheus";
    public static final String POLYVLIVE_STR = "polyvLiveAuth";
    public static final String PAY_CALL_BACK = "appPayCallBack";

    private final HandlerMethodReturnValueHandler delegate;

    public ResultWarpReturnValueHandler(HandlerMethodReturnValueHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return delegate.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
//        if (returnValue instanceof Page) {
//            Page pageReturnValue = (Page) returnValue;
//            PageHelper pageHelper = PageUtil.page2PageHelper(pageReturnValue);
//            returnValue = pageHelper;
//        } else if (returnValue instanceof QueryResults) {
//            QueryResults queryResultsValue = (QueryResults) returnValue;
//            PageHelper pageHelper = PageUtil.page2PageHelper(queryResultsValue);
//            returnValue = pageHelper;
//        }

        String requestURI = ((ServletWebRequest) webRequest).getRequest().getRequestURI();
        if (requestURI.contains(PROMETHEUS_STR)) {
            //普罗米修斯用
            delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
        } else if (requestURI.contains(POLYVLIVE_STR) || requestURI.contains(PAY_CALL_BACK)) {
            delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
        } else {
            delegate.handleReturnValue(CommonRsp.data(returnValue), returnType, mavContainer, webRequest);
        }

    }

}