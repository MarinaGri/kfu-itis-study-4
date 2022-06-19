package ru.kpfu.springmessages.resolver;

import org.springframework.web.servlet.LocaleResolver;
import ru.kpfu.springmessages.exception.EmptyRequiredDataException;
import ru.kpfu.springmessages.exception.IllegalHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class HostLocaleResolver implements LocaleResolver {

    private final String SCHEMA = "http";

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        return new Locale(parseHost(request));
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        if (response != null && locale != null) {
            response.setStatus(HttpServletResponse.SC_FOUND);
            response.setHeader("Location", createUrl(request, locale));
        } else {
            throw new EmptyRequiredDataException("HttpServletResponse and Locale cannot be null");
        }
    }

    private String parseHost(HttpServletRequest request) {
        String host = request.getHeader("Host");
        return host.substring(0, getLangInd(request));
    }

    private String createUrl(HttpServletRequest request, Locale locale) {
        StringBuilder url = new StringBuilder();
        String host = request.getHeader("Host");

        url.append(SCHEMA + "://").append(locale.getLanguage())
                .append(host.substring(getLangInd(request))).append(request.getContextPath());

        return url.toString();
    }

    private int getLangInd(HttpServletRequest request) {
        String host = request.getHeader("Host");
        int ind = host.indexOf(".");

        if (ind == -1) {
            throw new IllegalHostException("First domain is not a language code");
        }

        return ind;
    }
}
