package com.javalec.spring_pjt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javalec.spring_pjt.command.BCommand;
import com.javalec.spring_pjt.command.BContentCommand;
import com.javalec.spring_pjt.command.BDeleteCommand;
import com.javalec.spring_pjt.command.BListCommand;
import com.javalec.spring_pjt.command.BModifyCommand;
import com.javalec.spring_pjt.command.BReplyCommand;
import com.javalec.spring_pjt.command.BReplyViewCommand;
import com.javalec.spring_pjt.command.BWriteCommand;
import com.javalec.spring_pjt_board.util.Constant;

@Controller
public class BController {
	
//	1. pom.xml 에 JDBC template 설정
//	2. servlet-context.xml
//	   bean을 설정해주어야 함.
//	3. JDBCTemplate 변수선언 후 setter를 만든다. (결국 Beand에서 template 을 선언후 dataSource를 참조하기 때문에 우리는 template를 가지고 쓰면 된다
//	4. 패키지를 만들고 그안에 class를 만들어 static 변수를 선언   
//	5. sette를 자동으로 찾아서 설정 될수 있게 @Autowired 를 설정
	
	public JdbcTemplate template;

	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
		Constant.template = this.template;
	}

	BCommand command;
	
	@RequestMapping("/list")
	public String list(Model model) {		
		System.out.println("list()");
		
		command = new BListCommand();
		command.excute(model);
		
		return"list";
	}

	@RequestMapping("/write_view")
	public String write_view(Model model) {
		System.out.println("write_view()");
		
		return "write_view";
	}
	
	@RequestMapping("/write")
	public String write(HttpServletRequest request, Model model) {
		System.out.println("write()");
		
		model.addAttribute("request", request);
		command = new BWriteCommand();
		command.excute(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request, Model model) {
		System.out.println("content_view()");
		
		model.addAttribute("request", request);
		command = new BContentCommand();
		command.excute(model);
		
		return"content_view";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/modify")
	public String modify(HttpServletRequest request, Model model) {
		System.out.println("modify()");
		
		model.addAttribute("request", request);
		command = new BModifyCommand();
		command.excute(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("/reply_view")
	public String reply_view(HttpServletRequest request, Model model) {
		System.out.println("reply_view()");
		
		model.addAttribute("request", request);
		command = new BReplyViewCommand();
		command.excute(model);
		
		return "reply_view";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/reply")
	public String reply(HttpServletRequest request, Model model) {
		System.out.println("reply()");
		
		model.addAttribute("request", request);		
		command = new BReplyCommand();
		command.excute(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		System.out.println("delete()");
		
		model.addAttribute("request", request);
		command = new BDeleteCommand();
		command.excute(model);
		
		return "redirect:list";
	}
	
}
