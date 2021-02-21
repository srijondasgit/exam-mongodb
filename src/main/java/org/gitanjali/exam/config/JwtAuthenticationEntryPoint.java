package org.gitanjali.exam.config;

import org.springframework.stereotype.Component;


@Component
public class JwtAuthenticationEntryPoint {//implements AuthenticationEntryPoint {

//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response,
//                         AuthenticationException authException) throws IOException, ServletException {
//
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//
//        Exception exception = (Exception) request.getAttribute("exception");
//
//        String message;
//
//        if (exception != null) {
//
//            if (exception.getCause() != null) {
//                message = exception.getCause().toString() + " " + exception.getMessage();
//            } else {
//                message = exception.getMessage();
//            }
//
//            byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("error", message));
//
//            response.getOutputStream().write(body);
//
//
//        } else {
//
//            if (authException.getCause() != null) {
//                message = authException.getCause().toString() + " " + authException.getMessage();
//            } else {
//                message = authException.getMessage();
//            }
//
//            byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("error", message));
//
//            response.getOutputStream().write(body);
//        }
//    }

}
