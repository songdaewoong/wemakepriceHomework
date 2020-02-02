package com.homework;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeWork {
	
	@RequestMapping(value="/homeWork", method = RequestMethod.POST)
    public String test( @RequestParam(value = "url") String url,
    		            @RequestParam(value = "type") String type, 
    		            @RequestParam(value = "mok") String mok, 
    		            Model model) throws Exception{
		
		
		//프로토콜이 없으면 url앞에 붙여준다.
		if( url.indexOf("http") == -1 ) {
			url = "http://" + url;
		}
		
//		System.out.println("url : " + url);
//		System.out.println("type : " + type);
//		System.out.println("mok : " + mok);
		
		try{
			Document doc = Jsoup.connect(url).get();
			String data = doc.toString();
			
			//중복제거, 오름차순 정렬
			data = orderString(data);
			
//			Stream<String> replaceStream = Stream.of("[ㄱ-힣]", "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]", "\\s");
//			replaceStream.forEach(f -> replaceString(data, f));
			
			String[] replaceArr = {"[ㄱ-힣]", "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]", "\\s"};
			for( String replace : replaceArr ) {
				data = replaceString(data, replace);
			}
			
			//한글제거
//			data = replaceString(data, "[ㄱ-힣]");
			
			//특수문자 제거
//			data = replaceString(data, "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]");
			
			//공백제거
//			data = replaceString(data, "\\s");
			
			if( type.equals("tag") ) {
				//태그제거
				data = replaceString(data, "\\<.*?\\>");
			}
			
//			System.out.println("data : " +data );
			
			String numberString = data.replaceAll("[^0-9]", "");
//			System.out.println("numberString : " + numberString);
			
			String alphabetString = data.replaceAll("[0-9]", "");
//			System.out.println("alphabetString : " + alphabetString);
			
			StringBuilder data2 = new StringBuilder();
			
			String[] strArray = alphabetString.split("");//알파벳 배열
			String[] numArray = numberString.split("");//숫자 배열
			int numIndex = 0;//숫자배열에서 사용할 인덱스
			
			for(int i = 0 ; i < strArray.length ; i++  ) {
				data2.append(strArray[i]);//기본적으로 다음문자를 붙임.
				
				//for문 마지막일 때는 실행하지 않는다.
				if( i+1 != strArray.length ) {
					//현재 알파벳과 다음 알파벳이 다를때만 숫자를 붙여준다.
					if( !strArray[i].toUpperCase().equals(strArray[i+1].toUpperCase()) ) {
						//숫자가 상대적으로 적으므로 배열길이보다 작을때만 숫자를 붙여준다.
						//ArrayIndexOutOfBoundsException
						if( numIndex < numArray.length ) {
						data2.append(numArray[numIndex]);
							numIndex++;
						}
					}
				}
			}
			
			//나머지 값
			int remainder = data2.toString().length() % Integer.parseInt(mok);
			
			//사용자 페이지에 전달할 값
			model.addAttribute("url", url);
			model.addAttribute("type", type);
			model.addAttribute("mok", mok);
			model.addAttribute("remainder", remainder);
			model.addAttribute("data", data2.toString());


		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "오류가 발생했습니다.");
		}
        
        return "homeWork";
    }
	
	//중복값 제거, 오름차순 정렬
	private String orderString(String str) {
		return Stream.of(str.split(""))
					 .distinct()
					 .sorted(Comparator.naturalOrder())
					 .sorted(String.CASE_INSENSITIVE_ORDER)
					 .collect(Collectors.joining());
	}
	
	//replaceAll 함수
	private String replaceString(String str, String pattern) {
		return str.replaceAll(pattern, "");
	}
}
