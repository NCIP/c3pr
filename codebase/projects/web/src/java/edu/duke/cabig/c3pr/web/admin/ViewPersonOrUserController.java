package edu.duke.cabig.c3pr.web.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

public class ViewPersonOrUserController extends ParameterizableViewController {

    public ViewPersonOrUserController() {
        setViewName("admin/person_user_confirmation");
    }

    public ModelAndView handleRequestInternal(HttpServletRequest request,
                    HttpServletResponse response) throws Exception {

        setViewName("admin/person_user_confirmation");
        ModelAndView mav = new ModelAndView("admin/person_user_confirmation");

        return mav;
    }
}
