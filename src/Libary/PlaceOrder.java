package Libary;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlaceOrder implements IOOperation{

    @Override
    public void oper(Database database, User user) {

        JFrame frame = Main.frame(400, 270);
        frame.setLayout(new BorderLayout());

        JLabel title = Main.title("Fazer Encomenda");
        frame.getContentPane().add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(3, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        panel.setBackground(null);

        JLabel label = Main.label("Nome do Livro: ");
        JTextField name = Main.textfield();
        JLabel label2 = Main.label("Quantidade: ");
        JTextField qty = Main.textfield();
        JButton placeorder = Main.button("encomanda");
        JButton cancel = Main.button("Cancelar");

        panel.add(label);
        panel.add(name);
        panel.add(label2);
        panel.add(qty);
        panel.add(placeorder);
        panel.add(cancel);


        placeorder.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(name.getText().toString().matches("")) {
                    JOptionPane.showMessageDialog(new JFrame(), "O nome do livro deve ser preechido!");
                    return;
                }

                if(qty.getText().toString().matches("")) {
                    JOptionPane.showMessageDialog(new JFrame(), "Quantidade nao pode estar vazia!");
                    return;
                }

                try {
                    Integer.parseInt(qty.getText().toString());

                } catch (Exception event1) {
                    JOptionPane.showMessageDialog(new JFrame(), "A descrição de quantidade deve ser dita em número inteiros!");
                    return;
                }

                Order order = new Order();
                int i = database.getBook(name.getText().toString());

                if(i <= -1) {
                    JOptionPane.showMessageDialog(new JFrame(), "O livro não existe");

                } else {

                    Book book = database.getBook(i);
                    order.setBook(book);
                    order.setUser(user);

                    int valueQt = Integer.parseInt(qty.getText().toString());
                    order.setBook(book);
                    order.setPrice(book.getPrice() * valueQt);

                    int bookindex = database.getBook(book.getName());
                    book.setQty(book.getQty() - valueQt);
                    database.addOrder(order, book, bookindex);
                    JOptionPane.showMessageDialog(new JFrame(), "Pedido feito com sucesso!!");
                    frame.dispose();
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




