package ru.kpfu.springmessages.message_source;

import org.springframework.context.support.AbstractResourceBasedMessageSource;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Set;

public class DbMessageSource extends AbstractResourceBasedMessageSource {

    private String keyColumn = "key";

    private String valueColumn = "value";

    @PersistenceContext
    private EntityManager manager;

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        Set<String> names = getBasenameSet();

        for (String name : names) {
            Query query = manager.createNativeQuery(getSql(name, locale));
            query.setParameter("key", code);
            try {
                return new MessageFormat((String) query.getSingleResult(), locale);
            } catch (NoResultException ignored) {
            }
        }
        return null;
    }

    private String getSql(String name, Locale locale) {
        String table = name + "_" + locale.getLanguage();
        return "select " + valueColumn + " from " + table + " where " + keyColumn + " = :key";
    }

    public void setKeyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
    }

    public void setValueColumn(String valueColumn) {
        this.valueColumn = valueColumn;
    }
}
