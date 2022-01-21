package com.mooop.board.controller.web;

import com.mooop.board.entity.MSBUser;
import com.mooop.board.exception.GlobalException;
import com.mooop.board.sec.SecurityDetailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Project : MSimpleBoard
 * Package :com.mooop.board.controller.web
 * Author :  MOoop
 * Date : 19/01/2022
 * Desc :
 */

@Controller
@RequestMapping(value = "/common")
public class CommonController {

    private final SecurityDetailService securityDetailService;
    public CommonController(SecurityDetailService securityDetailService) {
        this.securityDetailService = securityDetailService;
    }

    @GetMapping("/error/block")
    public ModelAndView blockPage() throws GlobalException {
        return MSBBaseController.makeModelAndView("common/block" , null , null);
    }


    @GetMapping("/error/hold")
    public ModelAndView holdPage() throws GlobalException {
        return MSBBaseController.makeModelAndView("common/hold" , null , null);
    }

    @GetMapping("/confirm/{token}")
    public ModelAndView confirmPage(@PathVariable String token) throws GlobalException{
        MSBUser userInfo = securityDetailService.userActivation(token);
        String result = (userInfo == null)?"FAILED":"OK";
        return MSBBaseController.makeModelAndView("common/confirm"
            , new String[]{ "result","xdata"}
            , new Object[]{result , userInfo});
    }
}
