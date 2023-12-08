//package pl.medos.cmmsApi.config;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import pl.medos.cmmsApi.service.HardwareService;
//
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//import static pl.medos.cmmsApi.enums.Permission.TAK;
//
//@Component
//@WebFilter
//@Slf4j
//public class IpFilter extends OncePerRequestFilter {
//
//    private static final List<String> ALLOWED_IPS = Arrays.asList("0:0:0:0:0:0:0:1", "127.0.0.1", "10.0.0.89"); // Add your allowed IP addresses
//    @Autowired
//    private HardwareService hardwareService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String clientIp = getClientIp(request);
//        log.info(clientIp);
//
//        if (isAllowedIp(clientIp)) {
//            filterChain.doFilter(request, response);
//        } else {
//            log.info("Brak autoryzacji");
//            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
//        }
//    }
//
//    private String getClientIp(HttpServletRequest request) {
//        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
//        if (xForwardedForHeader != null && !xForwardedForHeader.isEmpty()) {
//            return xForwardedForHeader.split(",")[0].trim();
//        }
//        return request.getRemoteAddr();
//    }
//
//    private boolean isAllowedIp(String clientIp) {
//        log.info("isAlowedIp?()");
//        if (clientIp.equals("0:0:0:0:0:0:0:1") || clientIp.equals("127.0.0.1")) {
//            return false;
//        } else {
//            return hardwareService.findHardwareByIpAddress(clientIp, TAK);
//        }
//    }
//}
