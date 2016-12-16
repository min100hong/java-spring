package com.javalec.spring_pjt.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.javalec.spring_pjt.dao.BDao;
import com.javalec.spring_pjt.dto.BDto;

public class BListCommand implements BCommand {

	@Override
	public void excute(Model model) {
		System.out.println("BlistCommand");
		BDao dao = new BDao();
		ArrayList<BDto> dtos = dao.list();
		model.addAttribute("list", dtos);

	}

}
