package Libary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public abstract class User {

    protected String name;
    protected String email;
    protected String phonenumber;
    protected IOOperation[] operations;

    public User() {}

    public User(String name) {
        this.name = name;
    }

    public User(String name, String email, String phonenumber) {

        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    abstract public String toString();
    abstract public void menu(Database database, User user);

    public JFrame frame(String[] data, Database database, User user) {

        JFrame frame = new JFrame();

        frame.setSize(400, 500);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setTitle(" E-Livroteca: Biblioteca Virtual Multidisciplinar ");
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(null);
        frame.setBackground(null);

        JLabel label1 = Main.title("Seja vindo " + this.name + "!");
        frame.getContentPane().add(label1, BorderLayout.NORTH);

        JPanel panel_1 = new JPanel();

        panel_1.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
        panel_1.setLayout(new GridLayout(data.length, 1, 15, 15));
        panel_1.setBackground(null);

        for(int i = 0; i < data.length; i++) {

            JButton button = new JButton(data[i]);

            button.setFont(new Font("Tahoma", Font.BOLD, 17));
            button.setForeground(Color.white);
            button.setHorizontalAlignment(SwingConstants.CENTER);
            button.setBackground(Color.decode("#1da1f2"));
            button.setBorder(null);

            int index = i;

            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent event) {
                    operations[index].oper(database, user);

                    if(data[index].matches("Sair") || data[index].matches("Excluir dados salvos!")) {
                        frame.dispose();
                    }
                }
            });

            panel_1.add(button);
        }
        frame.getContentPane().add(panel_1, BorderLayout.CENTER);
        return frame;
    }
}









