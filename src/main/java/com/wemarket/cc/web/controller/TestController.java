package com.wemarket.cc.web.controller;

import com.wemarket.cc.common.dto.WebMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("test")
public class TestController extends CcBaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "hi", method = RequestMethod.POST)
    public DeferredResult<WebMessage<Map<String, Object>>> getByPage(@RequestBody Map<String, Object> map, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        return this.execute(httpRequest, httpResponse, null, timeout, (E, errors) -> {
            LOGGER.info(map.toString());
            System.out.println(map.toString());
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("list", "list");
            return toWebMessage(resultMap);
        });
    }
}
