package com.orbanszlrd.quizapi.controller;

import org.apache.commons.httpclient.HttpStatus;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class GlobalErrorController implements ErrorController {
    @RequestMapping("/error")
    public  String handleError(HttpServletRequest request, Model  model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            String responseMessage = HttpStatus.getStatusText(statusCode);
            model.addAttribute("errorMessage", statusCode + " " + responseMessage);
        } else {
            model.addAttribute("errorMessage", "Unknown Error");
        }

        return "error";
    }
}
