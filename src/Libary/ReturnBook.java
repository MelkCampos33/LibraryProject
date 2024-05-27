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

public class ReturnBook implements IOOperation {

        @Override
    public void oper(Database database, User user) {

            JFrame frame = Main.frame(400, 210);
            frame.setLayout(new BorderLayout());

            JLabel title = Main.title("Devolver Livro");
            frame.getContentPane().add(title, BorderLayout.NORTH);

            JPanel panel = new JPanel(new GridLayout(2,2,15,15));
            panel.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
            panel.setBackground(null);


            JLabel label = Main.label("Nome do Livro: ");
            JTextField name = Main.textfield();
            JButton returnbook = Main.button("Devolver Livro");
            JButton cancel = Main.button("Cancelar");

            panel.add(label);
            panel.add(name);
            panel.add(returnbook);
            panel.add(cancel);

            returnbook.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent event) {

                    if(name.getText().toString().matches("")) {
                        JOptionPane.showMessageDialog(new JFrame(), "O nome do livro deve ser preenchido!");
                        return;
                    }

                    if(!database.getBorrowingValue().isEmpty()) {

                        for(Borrowing borrowValue : database.getBorrowingValue()) {
                            if(borrowValue.getBook().getName().matches(name.getText().toString()) &&
                               borrowValue.getUser().getName().matches(user.getName())) {


                                Book book = borrowValue.getBook();

                                int i = database.getAllBooks().indexOf(book);

                                if(borrowValue.getDaysLeft() > 0) {
                                    JOptionPane.showMessageDialog(new JFrame(), "Voce está em atraso com sua entrega!\n" +
                                    "Voce devera pegar " + Math.abs(borrowValue.getDaysLeft() * 50) + " de taxa");
                                }

                                book.setBrwcopies(book.getBrwcopies() + 1);
                                database.returnBook(borrowValue, book, i);
                                JOptionPane.showMessageDialog(new JFrame(), "O livro foi devolvido, obrigado!");
                                frame.dispose();
                                break;

                            } else {
                                JOptionPane.showMessageDialog(new JFrame(), "Voce nao alugou esse livro!");
                            }
                        }

                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Voce nao alugou esse livro!");
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
















