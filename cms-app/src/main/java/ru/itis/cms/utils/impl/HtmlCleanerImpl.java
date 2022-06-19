package ru.itis.cms.utils.impl;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Component;
import ru.itis.cms.utils.HtmlCleaner;


@Component
public class HtmlCleanerImpl implements HtmlCleaner {

    @Override
    public String keepOnlySafeTags(String html) {
        return Jsoup.clean(html, Safelist.relaxed());
    }
}
