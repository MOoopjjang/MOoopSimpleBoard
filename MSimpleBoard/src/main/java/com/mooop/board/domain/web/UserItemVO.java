package com.mooop.board.domain.web;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserItemVO {
	private String email;
	private String password;
	private String status;
	private String role;
	private String userName;
	private String nickName;
	private String addr;
	private String enable;
	private String desc;
	private String lastLogin;
	private List<MultipartFile> files = new ArrayList<>();

}
