package com.douzone.mysite.web.mvc.user;

import com.douzone.mysite.web.mvc.main.MainAction;
import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class UserActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if("joinform".equals(actionName)) {
			action = new JoinFormAction();
		} else if ("join".equals(actionName)) {
			action = new JoinAction();
		} else if ("joinsuccess".equals(actionName)) {
			action = new JoinSuccessAction();
		} else if ("logout".equals(actionName)) {
			action = new MainAction();
		} else if ("updateform".equals(actionName)) {
			action = new MainAction();
		} else if ("loginform".equals(actionName)) {
			action = new LoginFormAction();
		} else {
			action = new MainAction();
		}
		
		return action;
	}

}
