package com.example.board.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//logback 객체 만드는 방법1
@Slf4j
public class LogController {

//    logback 객체 만드는 방법2
    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @GetMapping("/log/test")
    public String logTest() {
//    기본의 system print는 실무에서 사용하지 않음
//        이유는 1.성능이 좋지 않음 2.로그분류작업 불가
        System.out.println("시스템 프린트 로그");
//        로그레벨 : trace < debug < info < error
        logger.trace("trace 로그입니다");
        logger.debug("debug 로그입니다");
        logger.info("info 로그입니다");
        logger.error("error 로그입니다");

//        slf4j어노테이션을 선언시, log라는 변수로 logback객체 사용가능
        log.info("slf4j 테스트입니다");
        log.error("slf4j error 로그 테스트입니다");
//       error 로그는 에러가 터졌을때 사용
         try{
            logger.info("에러 테스트 시작");
            logger.debug("김선국 테스트 중 입니다");
             throw new RuntimeException("에러테스트");
        }catch (RuntimeException e){
             log.error(e.getMessage());
        }

        return "ok";
    }




}
