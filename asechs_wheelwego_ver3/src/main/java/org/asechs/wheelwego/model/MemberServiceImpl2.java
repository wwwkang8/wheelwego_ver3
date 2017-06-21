package org.asechs.wheelwego.model;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl2 /*implements MemberService */{
	@Resource
	private MemberDAO memberDAO;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

}