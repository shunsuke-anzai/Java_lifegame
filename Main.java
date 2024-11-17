package lifegame09B22002;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main implements Runnable {

    private BoardView view;
    private JButton nextButton;
    private JButton undoButton;
    private JButton newButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }

    public void run() {
        BoardModel model = new BoardModel(12, 12);
        model.addListener(new ModelPrinter());

        JFrame frame = new JFrame("Lifegame");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel base = new JPanel();
        frame.setContentPane(base);
        base.setPreferredSize(new Dimension(400, 300));
        frame.setMinimumSize(new Dimension(300, 200));

        base.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        base.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setLayout(new FlowLayout());

        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.next();
                undoButton.setEnabled(true);
                view.repaint();
            }
        });
        buttonPanel.add(nextButton);

        undoButton = new JButton("Undo");
        undoButton.setEnabled(false);
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.undo();
                if (!model.isUndoable()) {
                    undoButton.setEnabled(false);
                }
                view.repaint();
            }
        });
        buttonPanel.add(undoButton);

        newButton = new JButton("New");
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Main());
            }
        });
        buttonPanel.add(newButton);

        view = new BoardView(model, undoButton);
        base.add(view, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }
}
