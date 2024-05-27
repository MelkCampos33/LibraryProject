package Libary;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BorrowBook implements IOOperation {

    @Override
    public void oper(Database database, User user) {

        JFrame frame = Main.frame(400, 210);
        frame.setLayout(new BorderLayout());

        JLabel title = Main.title("Livro de Emprestimo: ");
        frame.getContentPane().add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        panel.setBackground(null);

        JLabel label = Main.label("Nome do Livro: ");
        JTextField name = Main.textfield();
        JButton borrow = Main.button("Livro de Emprestimo");
        JButton cancel = Main.button("Cancelar");

        panel.add(label);
        panel.add(name);
        panel.add(borrow);
        panel.add(cancel);


        borrow.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(name.getText().toString().matches("")) {

                    JOptionPane.showMessageDialog(new JFrame(), "O nome do livro deve ser preenchido!");
                    return;
                }

                int i = database.getBook(name.getText().toString());

                if(i > -1) {
                    Book book = database.getBook(i);
                    boolean n =  true;

                    for(Borrowing b : database.getBorrowingValue()) {
                        if(b.getBook().getName().matches(name.getText().toString()) &&
                        b.getUser().getName().matches(user.getName())) {

                            n = false;

                            JOptionPane.showMessageDialog(new JFrame(), "Voce já alugou esse livro antes!");
                        }
                    }

                    if(n) {
                        if(book.getBrwcopies() > 1) {

                            Borrowing borrowing = new Borrowing(book, user);
                            book.setBrwcopies(book.getBrwcopies() - 1);
                            database.borrowBook(borrowing, book, i);

                            JOptionPane.showMessageDialog(new JFrame(), "Voce deve fazer a devolução do livro em 14 dias a partir de agora\n" +
                                    "Prazo final: " + borrowing.getFinish() + "\n Aproveite!");
                            frame.dispose();

                        } else {
                            JOptionPane.showMessageDialog(new Frame(), "Esse livro não está disponivel para alugar no momento");
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "O livro selecionado não existe");
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










