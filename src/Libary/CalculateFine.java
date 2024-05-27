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

public class CalculateFine implements IOOperation {

    @Override
    public void oper(Database database, User user) {

        JFrame frame = Main.frame(400, 210);
        frame.setLayout(new BorderLayout());

        JLabel title = Main.title("Calcular Taxa");
        frame.getContentPane().add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(2,2,15,15));
        panel.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
        panel.setBackground(null);

        JLabel label = Main.label("Nome do Livro: ");
        JTextField name = Main.textfield();
        JButton calc = Main.button("Calcular Taxa");
        JButton cancel = Main.button("Cancelar");

        panel.add(label);
        panel.add(name);
        panel.add(calc);
        panel.add(cancel);

        calc.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {

                if(name.getText().toString().matches("")) {

                    JOptionPane.showMessageDialog(new JFrame(), "O nome do livro deve ser preenchido!");
                    return;
                }

                boolean value = true;

                for(Borrowing borrowingValue : database.getBorrowingValue()) {

                    if(borrowingValue.getBook().getName().matches(name.getText().toString()) &&
                       borrowingValue.getUser().getName().matches(user.getName())) {

                        if(borrowingValue.getDaysLeft() > 0) {
                            JOptionPane.showMessageDialog(new JFrame(), "Retorno em atraso!\n" +
                                    "Voce tera que pagar " + borrowingValue.getDaysLeft() * 50 + " de taxa de atraso");

                            frame.dispose();

                        } else {
                                JOptionPane.showMessageDialog(new JFrame(), "Voce não terá que pagar nehuma taxa.");
                                frame.dispose();
                        }

                        value = false;
                        break;
                    }
                }

                if(value) {
                    JOptionPane.showMessageDialog(new JFrame(), "Voce não alugou esse livro!");
                }
            }
        });

            cancel.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent event) {
                    frame.dispose();
                }
            });
    }
}
















