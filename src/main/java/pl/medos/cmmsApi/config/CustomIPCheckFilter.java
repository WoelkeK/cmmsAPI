package pl.medos.cmmsApi.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import pl.medos.cmmsApi.enums.Permission;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.service.HardwareService;


import java.io.IOException;
import java.io.InvalidObjectException;

@Component
@AllArgsConstructor
@Slf4j
public class CustomIPCheckFilter extends GenericFilterBean {

    private final HardwareService hardwareService;



    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String access= "";
        String remoteIP = request.getHeader("X-FORWARDED-FOR");
        if (remoteIP == null) {
            remoteIP = request.getRemoteAddr();
        }

        // Log the remote IP
        log.info("IP Remote address: " + remoteIP);

        String requestURI = request.getRequestURI();
        Hardware ipAddressRole = hardwareService.findByIpAddress(remoteIP);
        req.setAttribute("isAdmin", false);

        if (ipAddressRole.getId() == null) {
            log.info("No match found in the repository for IP: " + remoteIP);
            req.setAttribute("isAdmin", false);
            req.setAttribute("noAccess", true);
//            throw new ServletException("Unauthorized IP Access");
        }else if(ipAddressRole.getPermission()==null) {
            log.info("No permission sets in the repository for IP: " + remoteIP + " default: NO_ACCESS");
            Permission permission = Permission.USER;
            log.info("Permission " + permission.toString());
            ipAddressRole.setPermission(permission);
            access = ipAddressRole.getPermission().toString().toUpperCase();
        }else {
            log.info("start set");
            access = ipAddressRole.getPermission().toString().toUpperCase();
        }
            log.info("Dostęp: " + access);
            switch (access) {
                case "ADMIN":
                    req.setAttribute("isAdmin", true);
                    break;
                case "USER":
                    req.setAttribute("nRead", ipAddressRole.isNRead());
                    req.setAttribute("eRead", ipAddressRole.isERead());
                    req.setAttribute("pRead", ipAddressRole.isPRead());
                    req.setAttribute("dRead", ipAddressRole.isDRead());
                    req.setAttribute("mRead", ipAddressRole.isMRead());
                    req.setAttribute("jRead", ipAddressRole.isJRead());

                    req.setAttribute("nEdit", ipAddressRole.isNEdit());
                    req.setAttribute("eEdit", ipAddressRole.isEEdit());
                    req.setAttribute("pEdit", ipAddressRole.isPEdit());
                    req.setAttribute("dEdit", ipAddressRole.isDEdit());
                    req.setAttribute("mEdit", ipAddressRole.isMEdit());
                    req.setAttribute("jEdit", ipAddressRole.isJEdit());

                    req.setAttribute("nFull", ipAddressRole.isNDelete());
                    req.setAttribute("eFull", ipAddressRole.isEDelete());
                    req.setAttribute("pFull", ipAddressRole.isPDelete());
                    req.setAttribute("dFull", ipAddressRole.isDDelete());
                    req.setAttribute("mFull", ipAddressRole.isMDelete());
                    req.setAttribute("jFull", ipAddressRole.isJDelete());
                    break;

                default:
                    req.setAttribute("isAdmin", false);
                    req.setAttribute("noAccess", true);
            }


//        if (ipAddressRole.getPermission().toString().equalsIgnoreCase("admin")) {
//            req.setAttribute("isAdmin", true);
//
//        } else {
//            req.setAttribute("isAdmin", false);
//        }
        log.info("Send data to frontend");
        req.setAttribute("userIP", remoteIP);
        chain.doFilter(request, res);
    }
}
