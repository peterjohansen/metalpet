package org.pemacy.metalpet;

public class GreeterCli {

    static void main(final String[] args) {
        if (args != null && args.length > 1) {
            System.err.println("Usage: greeter [greeting-name]");
            System.exit(1);
        }

        assert args != null;
        final var name = args.length == 1 ? args[0].trim() : "World";
        final var greeting = new Greeter().nextGreeting(name);
        System.out.println(greeting);
    }

}
