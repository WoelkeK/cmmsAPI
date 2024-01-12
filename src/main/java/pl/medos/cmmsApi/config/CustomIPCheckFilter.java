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

        String remoteIP = request.getHeader("X-FORWARDED-FOR");
        if (remoteIP == null) {
            remoteIP = request.getRemoteAddr();
        }

        // Log the remote IP
        log.info("IP Remote address: " + remoteIP);

        String requestURI = request.getRequestURI();
        Hardware ipAddressRole = hardwareService.findByIpAddress(remoteIP);
        req.setAttribute("isAdmin", false);

        if (ipAddressRole == null) {
            log.info("No match found in the repository for IP: " + remoteIP);
            throw new ServletException("Unauthorized IP Access");
        }
        Permission permission = ipAddressRole.getPermission();
        if (permission == null) {
            log.info("No permission sets in the repository for IP: " + remoteIP + " default: NO_ACCESS");
            permission = Permission.USER;
            log.info("Permission "+ permission.toString());
            ipAddressRole.setPermission(permission);
        }
        String access = permission.toString().toUpperCase();
        log.info(access);

        switch(access){
            case "ADMIN":
                req.setAttribute("isAdmin", true);
                break;
            case "USER":
                req.setAttribute("isAdmin", false);
                break;

            case "AWIZACJE_USER":
                req.setAttribute("nRead", true);
                req.setAttribute("eRead", true);
                req.setAttribute("pRead", true);
                req.setAttribute("nEdit", false);
                req.setAttribute("nFull", false);
                break;
            case "AWIZACJE_SU":
                req.setAttribute("nRead", true);
                req.setAttribute("nEdit", true);
                req.setAttribute("nFull", false);
                break;
            case "AWIZACJE_ADMIN":
                req.setAttribute("nRead", true);
                req.setAttribute("nEdit", true);
                req.setAttribute("nFull", true);
                break;
            case "PRACOWNICY_USER":
                req.setAttribute("nRead", true);
                req.setAttribute("eRead", true);
                req.setAttribute("pRead", true);
                req.setAttribute("eEdit", false);
                req.setAttribute("eFull", false);
                break;
            case "PRACOWNICY_SU":
                req.setAttribute("eRead", true);
                req.setAttribute("eEdit", true);
                req.setAttribute("eFull", false);
                break;
            case "PRACOWNICY_ADMIN":
                req.setAttribute("eRead", true);
                req.setAttribute("eEdit", true);
                req.setAttribute("eFull", true);
                break;
            case "PRZEPUSTKI_USER":
                req.setAttribute("nRead", true);
                req.setAttribute("eRead", true);
                req.setAttribute("pRead", true);
                req.setAttribute("pEdit", false);
                req.setAttribute("pFull", false);
                break;
            case "PRZEPUSTKI_SU":
                req.setAttribute("pRead", true);
                req.setAttribute("pEdit", true);
                req.setAttribute("pFull", false);
                break;
            case "PRZEPUSTKI_ADMIN":
                req.setAttribute("pRead", true);
                req.setAttribute("pEdit", true);
                req.setAttribute("pFull", true);
                break;
            case "WYDZIAŁY_USER":
                req.setAttribute("dRead", true);
                req.setAttribute("dEdit", false);
                req.setAttribute("dFull", false);
                break;
            case "WYDZIAŁY_SU":
                req.setAttribute("dRead", true);
                req.setAttribute("dEdit", true);
                req.setAttribute("dFull", false);
                break;
            case "WYDZIAŁY_ADMIN":
                req.setAttribute("dRead", true);
                req.setAttribute("dEdit", true);
                req.setAttribute("dFull", true);
                break;
            case "MASZYNY_USER":
                req.setAttribute("mRead", true);
                req.setAttribute("mEdit", false);
                req.setAttribute("mFull", false);;
                break;
            case "MASZYNY_SU":
                req.setAttribute("mRead", true);
                req.setAttribute("mEdit", true);
                req.setAttribute("mFull", false);
                break;
            case "MASZYNY_ADMIN":
                req.setAttribute("mRead", true);
                req.setAttribute("mEdit", true);
                req.setAttribute("mFull", true);
                break;
            case "AWARIE_USER":
                req.setAttribute("jRead", true);
                req.setAttribute("jEdit", false);
                req.setAttribute("jFull", false);
                break;
            case "AWARIE_SU":
                req.setAttribute("jRead", true);
                req.setAttribute("jEdit", true);
                req.setAttribute("jFull", false);
                break;
            case "AWARIE_ADMIN":
                req.setAttribute("jRead", true);
                req.setAttribute("jEdit", true);
                req.setAttribute("jFull", true);
                break;

                default:
                req.setAttribute("isAdmin", false);





        }


//        if (ipAddressRole.getPermission().toString().equalsIgnoreCase("admin")) {
//            req.setAttribute("isAdmin", true);
//
//        } else {
//            req.setAttribute("isAdmin", false);
//        }
//        req.setAttribute("userIP", remoteIP);
        chain.doFilter(request, res);
    }
}
