package org.pemacy.metalpet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.function.Predicate.not;

public class GreeterGui {

    private static final String DEFAULT_NAME = "World";

    static void main() {
        new GreeterGui(new Greeter());
    }

    private final Greeter greeter;

    private final JFrame frame;
    private final JTextField nameInput;

    public GreeterGui(final Greeter greeter) {
        this.greeter = requireNonNull(greeter);

        this.frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Greeter");

        final var panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        panel.setLayout(new BorderLayout(4, 4));

        final var nameLabel = new JLabel();
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameLabel.setText("Name:");
        panel.add(nameLabel, BorderLayout.WEST);

        this.nameInput = new JTextField();
        nameInput.setColumns(16);
        nameInput.setText(DEFAULT_NAME);
        panel.add(nameInput, BorderLayout.CENTER);

        final var greetButton = new JButton();
        greetButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                showGreeting();
            }
        });
        greetButton.setText("Greet");
        panel.add(greetButton, BorderLayout.EAST);
        frame.getRootPane().setDefaultButton(greetButton);

        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private Optional<String> getInputName() {
        return Optional.ofNullable(nameInput.getText())
                .map(String::trim)
                .filter(not(String::isBlank));
    }

    private void showGreeting() {
        final var name = getInputName().orElse(DEFAULT_NAME);
        nameInput.setText(name);

        final var greeting = greeter.nextGreeting(name);
        JOptionPane.showMessageDialog(frame, greeting, "Greeting", JOptionPane.INFORMATION_MESSAGE);
    }

}
