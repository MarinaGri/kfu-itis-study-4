package ru.itis.cms.utils.impl;

import com.ibm.icu.text.Transliterator;
import org.springframework.stereotype.Component;
import ru.itis.cms.utils.SlugGenerator;

@Component
public class SlugFromTitleGenerator implements SlugGenerator {

    private final String CYRILLIC_TO_LATIN = "Russian-Latin/BGN";

    @Override
    public String generate(String title) {
        Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
        return toLatinTrans.transliterate(title.toLowerCase().replaceAll("\\s", "-"));
    }

}
