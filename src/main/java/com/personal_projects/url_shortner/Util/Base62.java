package com.personal_projects.url_shortner.Util;

public class Base62 {
    private static final String CHARSET =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String encode(long value) {
        StringBuilder sb = new StringBuilder();

        while (value > 0) {
            int remainder = (int) (value % 62);
            sb.append(CHARSET.charAt(remainder));
            value /= 62;
        }

        return sb.reverse().toString();
    }
}
