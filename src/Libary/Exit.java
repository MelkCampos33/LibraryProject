package Libary;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Exit implements IOOperation    {

    Database database;

    @Override
    public void oper(Database database, User user) {

        JFrame frame = Main.frame(500, 300);
        this.database = new Database();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 20, 15));
        panel.setBackground(null);

        JLabel title = Main.label("Bem vindo a E-esqueci o nome do trabalho");
        title.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        title.setFont(new Font("Tahoma", Font.BOLD, 21));
        title.setForeground(Color.decode("#1da1f2"));

        frame.getContentPane().add(title, BorderLayout.NORTH);

        JLabel label1 = Main.label("Número de telefone: ");
        JLabel label2 = Main.label("Email: ");
        JTextField phonenumber = Main.textfield();

        JTextField email = Main.textfield();
        JButton login = Main.button("Login");
        JButton newUser = Main.button("New User");

        login.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {

                if(phonenumber.getText().toString().matches("")) {
                    JOptionPane.showMessageDialog(new JFrame(), "Voce deve informar o telefone!");
                    return;
                }

                if(email.getText().toString().matches("")) {
                    JOptionPane.showMessageDialog(new JFrame(), "Voce deve informar o email!");
                    return;
                }

                login(phonenumber.getText().toString(), email.getText().toString(), frame);
            }
        });

        newUser.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                newUser();
                frame.dispose();
            }
        });


        panel.add(label1);
        panel.add(phonenumber);
        panel.add(label2);
        panel.add(email);
        panel.add(email);
        panel.add(login);
        panel.add(newUser);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void login(String phonenumber, String email, JFrame frame) {

        int value = database.login(phonenumber, email);

        if(value != -1) {

            User user = database.getUser(value);
            user.menu(database, user);
            frame.dispose();

        } else {
            JOptionPane.showMessageDialog(new JFrame(), "O usuário não existe");
        }
    }

    private void newUser() {

        JFrame frame = Main.frame(500, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 20, 15));
        panel.setBackground(null);

        JLabel title = Main.label("Criar uma nova conta: ");
        title.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        title.setFont(new Font("Tohoma", Font.BOLD, 21));
        title.setForeground(Color.decode("#1da1f2"));

        frame.getContentPane().add(title, BorderLayout.NORTH);

        JLabel label0 = Main.label("Nome: ");
        JLabel label1 = Main.label("Telefone: ");
        JLabel label2 = Main.label("Email: ");

        JTextField name = Main.textfield();
        JTextField phonenumber = Main.textfield();
        JTextField email = Main.textfield();

        JRadioButton admin = Main.radioButton("Admin");
        JRadioButton normaluser = Main.radioButton("Usuário Padrão");
        JButton createacc = Main.button("Criar Conta");
        JButton cancel = Main.button("Cancelar");


        admin.addActionListener(event -> {

            if(normaluser.isSelected()) {
                normaluser.setSelected(false);
            }
        });


        normaluser.addActionListener(event -> {

            if(admin.isSelected()) {
                admin.setSelected(false);
            }
        });

        panel.add(label0);
        panel.add(name);

        panel.add(label1);
        panel.add(phonenumber);

        panel.add(label2);
        panel.add(email);

        panel.add(admin);
        panel.add(normaluser);

        panel.add(createacc);
        panel.add(cancel);


        createacc.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {

                if (database.userExists(name.getText().toString())) {
                    JOptionPane.showMessageDialog(new JFrame(), "Nome já cadastrado!\nTente outro, por favor");
                    return;
                }

                if (name.getText().toString().matches("")) {
                    JOptionPane.showMessageDialog(new JFrame(), "O nome deve ser preenchido!");
                    return;
                }

                if (phonenumber.getText().toString().matches("")) {
                    JOptionPane.showMessageDialog(new JFrame(), "O telefone deve ser preenchido!");
                    return;
                }

                if (email.getText().toString().matches("")) {
                    JOptionPane.showMessageDialog(new JFrame(), "O e-mail deve ser preenchido!");
                    return;
                }

                if (!admin.isSelected() && !normaluser.isSelected()) {
                    JOptionPane.showMessageDialog(new JFrame(), "");
                    return;
                }

                User user;

                if (admin.isSelected()) {

                    user = new Admin(name.getText().toString(), email.getText().toString(),
                            phonenumber.getText().toString());

                } else {

                    user = new NormalUser(name.getText().toString(), email.getText().toString(),
                            phonenumber.getText().toString());
                }

                frame.dispose();
                database.AddUser(user);
                user.menu(database, user);
            }
        });

        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                frame.dispose();
            }
        });

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}








