package edu.kh.project.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.member.model.mapper.MemberMapper;

@Transactional(rollbackFor=Exception.class)
@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberMapper mapper;
}
