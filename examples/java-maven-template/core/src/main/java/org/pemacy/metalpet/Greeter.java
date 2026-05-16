package org.pemacy.metalpet;

public final class Greeter {

    private static final String GREETING_TEMPLATE = "Hello, %s!";

    public String nextGreeting(final String name) {
        return GREETING_TEMPLATE.formatted(name);
    }

}
