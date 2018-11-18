package net.leolee.transfermoneyapi;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.leolee.transfermoneyapi.exception.ErrorCode;
import net.leolee.transfermoneyapi.message.ApiError;

public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
	@Override
    public void commence(final HttpServletRequest request, 
            final HttpServletResponse response, 
            final AuthenticationException authException) throws IOException, ServletException {
        //Authentication failed, send error response.
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
        
        ApiError error = new ApiError(authException.getMessage(), ErrorCode.E101.toString());
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), error);		
        
    }
     
    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("MONEY_TRANSFER_REALM");
        super.afterPropertiesSet();
    }
}
