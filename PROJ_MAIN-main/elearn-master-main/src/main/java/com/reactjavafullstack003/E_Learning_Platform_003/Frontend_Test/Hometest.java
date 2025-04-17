package com.reactjavafullstack003.E_Learning_Platform_003.Frontend_Test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Hometest {
	
@RequestMapping("/home")
public String getHome() {
	return "home" ;
}
}
