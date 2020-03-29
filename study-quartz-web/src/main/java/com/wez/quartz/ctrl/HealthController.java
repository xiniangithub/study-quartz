package com.wez.quartz.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HealthController {

    @RequestMapping(value="/health", method=RequestMethod.GET)
    public ModelAndView toHealth() {
        return new ModelAndView("/health");
    }

}
