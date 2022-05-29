package kanfu.old_city_memo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
class MemoryControllerAdvice {

    @ResponseBody
    @ExceptionHandler(MemoryUnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String notAuthorizedHandler(Exception ex) {
        return ex.getMessage();
    }
    
    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String notFoundHandler(Exception ex) {
        return ex.getMessage();
    }
}
