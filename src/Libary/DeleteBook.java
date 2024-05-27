package Libary;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DeleteBook implements IOOperation{

    @Override
    public void oper(Database database, User user) {

        JFrame frame = Main.frame(400, 210);
        frame.setLayout(new BorderLayout());

        JLabel title = Main.title("Deletar Livro");
        frame.getContentPane().add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        panel.setBackground(null);


        JLabel label = Main.label("Nome do Livro: ");
        JTextField name = Main.textfield();

        JButton delete = Main.button("Deletar Livro ");
        JButton cancel = Main.button("Cencelar");
        panel.add(label);
        panel.add(name);
        panel.add(delete);
        panel.add(cancel);

        delete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(name.getText().toString().matches("")) {
                    JOptionPane.showMessageDialog(new JFrame(), "O nome do livro deve ser preenchido!");
                    return;
                }

                int i = database.getBook(name.getText().toString());

                if(i >- 1) {
                    database.deleteBook(i);
                    JOptionPane.showMessageDialog(new JFrame(), "Livro deletado com sucesso!");
                    frame.dispose();

                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "O livro não existe");
                }
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









