package edu.kh.project.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.MultipartConfigElement;

@Configuration
@PropertySource("classpath:/config.properties")
public class FileConfig implements WebMvcConfigurer {

	// 파일 업로드 임계값
	@Value("${spring.servlet.multipart.file-size-threshold}")
	private long fileSizeThreshold;
	
	// 요청당 파일 최대 크기
	@Value("${spring.servlet.multipart.max-request-size}")
	private long maxRequestSize;
	
	// 개별 파일당 최대 크기
	@Value("${spring.servlet.multipart.max-file-size}")
	private long maxFileSize;
	
	// 임계값 초과 시 임시 저장 폴더 경로
	@Value("${spring.servlet.multipart.location}")
	private String location;
	
	
	// --------------------------------------------------
		// 프로필 이미지
		@Value("${my.profile.resource-handler}")
		private String profileResourceHandler;
		
		@Value("${my.profile.resource-location}")
		private String profileResourceLocation;
		
		// --------------------------------------------------
		
		// 게시판 이미지
		@Value("${my.board.resource-handler}")
		private String boardResourceHandler; // 요청주소
		
		@Value("${my.board.resource-location}")
		private String boardResourceLocation; // 연결될 서버 폴더 경로
	
	
	
	
	
		// 요청 주소에 따라
		// 서버 컴퓨터의 어떤 경로에 접근할지 설정
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			
			registry
			.addResourceHandler("/myPage/file/**") // 클라이언트 요청 주소 패턴
			.addResourceLocations("file:///C:/uploadFiles/test/"); 
			// 클라이언트가 /myPage/file/** 패턴으로 이미지를 요청할 때
			// 요청을 연결해서 처리해줄 서버 폴더 경로 연결
			
			// 프로필 이미지 요청 - 서버 폴더 연결 추가
			registry.
			addResourceHandler(profileResourceHandler) //  /myPage/profile/**
			.addResourceLocations(profileResourceLocation); //  file:///C:/uploadFiles/profile/
			
			// file:///C: 는 파일 시스템의 루트 디렉토리 
			// file://  은  URL 스킴(Scheme), 파일 시스템의 리소스
			// /C: 는 Windows 시스템에서 C 드라이브 를 가리킴.
			// file:///C: 는 "C 드라이브의 루트 디렉토리"를 의미함.
			
			
			// 게시글 이미지 요청 - 서버 폴더 연결 추가
			registry
			.addResourceHandler(boardResourceHandler)
			.addResourceLocations(boardResourceLocation);
		
		
	}
	
	
	/* MultipartResolver 설정 */
	@Bean
	public MultipartConfigElement configElement() {
		// MultipartConfigElement :
		// 파일 업로드를 처리하는데 사용되는 MUltipartConfigElement를 구성하고 반환
		// 파일 업로드를 위한 구성 옵션을 설정하는데 사용
		// 업로드 파일의 최대 크기, 메모리에서의 임시 저장 경로 등을 설정 가능
		
		
		MultipartConfigFactory factory = new MultipartConfigFactory();
		
		factory.setFileSizeThreshold(DataSize.ofBytes(fileSizeThreshold));
		
		factory.setMaxFileSize(DataSize.ofBytes(maxFileSize));
		
		factory.setMaxRequestSize(DataSize.ofBytes(maxRequestSize));
		
		factory.setLocation(location);
		
		return factory.createMultipartConfig();
	}
	
	// MultipartResolver 객체를 Bean으로 추가
	// -> 추가 후 위에서 만든 MultipartConfig 자동으로 이용함
	@Bean
	public MultipartResolver multipartResolver() {
		// MultipartResolver : MultipartFile을 처리해주는 해결사...
		
		StandardServletMultipartResolver multipartResolver
			= new StandardServletMultipartResolver();
		
		return multipartResolver;
	}
}
