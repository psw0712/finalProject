package edu.kh.project.member.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.member.model.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;

@Transactional(rollbackFor=Exception.class)
@Service // 비즈니스 로직 처리 역할 명시 + Bean 등록
@Slf4j
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberMapper mapper;
	
	// Bcrypt 암호화 객체 의존성 주입(SecurityConfig 참고)
	@Autowired
	private BCryptPasswordEncoder bcrypt;

	// 로그인 서비스
	@Override
	public Member login(Member inputMember) {
		
		// 테스트
		
		//bcrypt.encode(문자열) : 문자열을 암호화하여 반환
				String bcryptPassword = bcrypt.encode(inputMember.getMemberPw());
				//log.debug("bcryptPassword : " + bcryptPassword);
				
				//boolean result = bcrypt.matches(inputMember.getMemberPw(), bcryptPassword);
				//log.debug("result : " + result);
				
				// 1. 이메일이 일치하면서 탈퇴하지 않은 회원 조회
				Member loginMember = mapper.login(inputMember.getMemberEmail());
				
				// 2. 만약에 일치하는 이메일이 없어서 조회 결과가 null인 경우
				if(loginMember == null) return null;
				
				// 3. 입력 받은 비밀번호(inputMember.getMemberPw() 평문)dhk
				// 암호화된 비밀번호(loginMember.getMemberPw())
				// 두 비밀번호가 일치하는지 확인
				
				// 일치하지 않으면
				if( !bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
					return null;
				}
				
				// 로그인 결과에서 비밀번호 제거
				loginMember.setMemberPw(null);
				
				return loginMember;
	}

	// 이메일 중복 검사
	@Override
	public int checkEmail(String memberEmail) {
		return mapper.checkEmail(memberEmail);
	}

	// 닉네임 중복 검사
	@Override
	public int checkNickname(String memberNickname) {
		return mapper.checkNickname(memberNickname);
	}

	// 회원 가입 서비스
	@Override
	public int signup(Member inputMember, String[] memberAddress) {
		
		// 주소가 입력되지 않으면
		// inputMember.getMemberAddress() -> ",,"
		// memberAddress -> [,,]
		
		// 주소가 입력된 경우
		if( !inputMember.getMemberAddress().equals(",,") ) {
			
			// String.join("구분자", 배열)
			// -> 배열의 모든 요소 사이에 "구분자"를 추가하여
			// 하나의 문자열로 만들어 반환하는 메서드
			
			// 구분자로 "^^^" 쓴 이유 :
			// -> 주소, 상세주소에 없는 특수문자 작성
			// -> 나중에 다시 3분할 할 때 구분자로 이용할 예정
			String address = String.join("^^^", memberAddress);
			
			// inputMember 주소로 합쳐진 주소를 세팅
			inputMember.setMemberAddress(address);
			
			
		} else { // 주소 입력 X
			
			inputMember.setMemberAddress(null); // null 저장
		}
		
		// 비밀번호를 암호화 하여 inputMember에 세팅
		String encPw = bcrypt.encode(inputMember.getMemberPw());
		inputMember.setMemberPw(encPw);
		
		
		// 회원 가입 매퍼 메서드 호출
		return mapper.signup(inputMember);
	}

	// 빠른 로그인
	// -> 일반 로그인에서 비밀번호 비교만 제외
	@Override
	public Member quickLogin(String memberEmail) {
		
		Member loginMember = mapper.login(memberEmail);
		
		// 탈퇴 or 없는 회원
		if(loginMember == null) return null;
		
		// 조회된 비밀번호 null 로 변경
		loginMember.setMemberPw(null);
		return loginMember;
	}

	// 회원 목록 조회
	@Override
	public List<Member> selectMemberList() {
		
		return mapper.selectMemberList();
	}
	
}
