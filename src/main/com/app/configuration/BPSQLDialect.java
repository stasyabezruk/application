package com.app.configuration;

import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class BPSQLDialect extends MySQL5InnoDBDialect {

    public BPSQLDialect() {
        super();
        registerFunction("match_against", new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "match(?1) against (?2 in boolean mode)"));
    }
}
