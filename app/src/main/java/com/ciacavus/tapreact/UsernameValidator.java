package com.ciacavus.tapreact;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ciaran on 29/06/2016.
 */
public class UsernameValidator {

    private Pattern pattern;
    private Matcher matcher;

    private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";

    public UsernameValidator()
    {
        pattern = Pattern.compile(USERNAME_PATTERN);
    }

    public boolean validate(final String username)
    {
        matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
