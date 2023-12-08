package pl.medos.cmmsApi.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.service.HardwareService;


import java.io.IOException;

@Component
@AllArgsConstructor
public class CustomIPCheckFilter extends GenericFilterBean {

    private final HardwareService hardwareService;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;

        String remoteIP = request.getHeader("X-FORWARDED-FOR");
        if (remoteIP == null) {
            remoteIP = request.getRemoteAddr();
        }

        // Log the remote IP
        logger.info("IP Remote address: " + remoteIP);

        String requestURI = request.getRequestURI();
        logger.info("URI: " + requestURI);

        // Extract the role from the endpoint
        String roleFromEndPoint = extractRoleFromURI(requestURI);
        logger.info("roleFromEndpoint: " + roleFromEndPoint);

        Hardware ipAddressRole = hardwareService.findByIpAddress(remoteIP);
        logger.info("roleFromDb: " + ipAddressRole.getIpAddress()+ " " + ipAddressRole.getRole());

        if (ipAddressRole == null) {
            logger.info("No match found in the repository for IP: " + remoteIP);
            throw new ServletException("Unauthorized IP Access");
        } else{
            logger.info("IP DB Address: " + ipAddressRole.getIpAddress());
            if (!roleFromEndPoint.equals(ipAddressRole.getRole()) && !(ipAddressRole.getRole().equals("admin") && roleFromEndPoint.equals("user"))) {
                logger.info("Role mismatch. Expected: " + roleFromEndPoint + ", Actual: " + ipAddressRole.getRole());
                throw new ServletException("Unauthorized IP Access");
            }
        }
        chain.doFilter(request, res);
    }

    private String extractRoleFromURI(String uri) throws ServletException {
        if (uri.contains("/user")) {
            return "user";
        } else if (uri.contains("/admin")) {
            return "admin";
        } else {
            throw new ServletException("Unknown role for URL: " + uri);
        }
    }
}
