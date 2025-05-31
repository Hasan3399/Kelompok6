import aplikasipembukuanbarang.StokBarangApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginForm extends JFrame {
    public LoginForm() {
        setTitle("Login");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(173, 216, 230)); // Warna biru muda

        Font titleFont = new Font("SansSerif", Font.BOLD, 32);
        Font textFont = new Font("SansSerif", Font.PLAIN, 16);

        // Logo Warung
        ImageIcon logo = new ImageIcon("src/images/warung.png");
        Image image = logo.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(image));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelWelcome = new JLabel("Welcome to Aplikasi", SwingConstants.CENTER);
        labelWelcome.setFont(titleFont);
        labelWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 20));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelKelompok = new JLabel("by: Kelompok 6", SwingConstants.CENTER);
        labelKelompok.setFont(textFont);
        labelKelompok.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new StokBarangApp();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        panel.add(Box.createVerticalGlue());
        panel.add(logoLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(labelWelcome);
        panel.add(Box.createVerticalStrut(80));
        panel.add(btnLogin);
        panel.add(Box.createVerticalStrut(20));
        panel.add(labelKelompok);
        panel.add(Box.createVerticalGlue());

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}

