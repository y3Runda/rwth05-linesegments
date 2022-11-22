import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Plot extends JPanel {
    int marg = 50;

    static List<LineSegment> random = new ArrayList<>();
    static List<LineSegment> parallel = new ArrayList<>();

    protected void paintComponent(Graphics grf) {
        super.paintComponent(grf);
        Graphics2D graph = (Graphics2D)grf;

        int width = getWidth();
        int height = getHeight();

        // Box
        graph.draw(new Line2D.Double(marg, marg, marg, height-marg));
        graph.draw(new Line2D.Double(marg, height-marg, width-marg, height-marg));
        graph.draw(new Line2D.Double(marg, marg, width-marg, marg));
        graph.draw(new Line2D.Double(width-marg, marg, width-marg, height-marg));

        graph.setColor(Color.BLUE);
        for(LineSegment s: random)
            graph.draw(new Line2D.Double(marg + s.getA().x(), marg + s.getA().y(), s.getB().x() + marg, s.getB().y() + marg));

        graph.setColor(Color.RED);
        for(LineSegment s: parallel)
            graph.draw(new Line2D.Double(marg + s.getA().x(), marg + s.getA().y(), s.getB().x() + marg, s.getB().y() + marg));

        repaint();
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(50, 50);
        parallel = new ArrayList<LineSegment>(Arrays.asList(LineSegment.spawnParallel(600, 13)));

        JPanel panel_plot = new JPanel();
        panel_plot.setPreferredSize(new Dimension(700, 700));
        panel_plot.setMinimumSize(new Dimension(700, 700));
        panel_plot.setMaximumSize(new Dimension(700, 700));
        panel_plot.setLayout(new javax.swing.BoxLayout(panel_plot, javax.swing.BoxLayout.Y_AXIS));
        panel_plot.add(new Plot());

        JPanel panel = new JPanel();
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        panel.add(panel_plot);

        JPanel buttons_text_panel = new JPanel();
        buttons_text_panel.setLayout(new javax.swing.BoxLayout(buttons_text_panel, javax.swing.BoxLayout.X_AXIS));

        JButton button1 = new JButton("1");
        JButton button10 = new JButton("10000");
        JButton clear = new JButton("Clear");
        buttons_text_panel.add(button1);
        buttons_text_panel.add(Box.createRigidArea(new Dimension(5, 0)));
        buttons_text_panel.add(button10);
        buttons_text_panel.add(Box.createRigidArea(new Dimension(5, 0)));
        buttons_text_panel.add(clear);
        buttons_text_panel.add(Box.createRigidArea(new Dimension(10, 0)));
        JLabel text = new JLabel("");
        buttons_text_panel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttons_text_panel.add(text);
        button1.addActionListener(e -> {
            LineSegment[] parallel_lines = parallel.toArray(new LineSegment[0]);
            random.add(new LineSegment(600,50));
            LineSegment[] random_lines = random.toArray(new LineSegment[0]);
            text.setText("" + String.format("%.5g%n", LineSegment.computeValue(parallel_lines,random_lines)));
        });
        button10.addActionListener(e -> {
            for(int i = 0; i < 10000; i++)
                random.add(new LineSegment(600,50));
            LineSegment[] parallel_lines = parallel.toArray(new LineSegment[0]);
            LineSegment[] random_lines = random.toArray(new LineSegment[0]);
            text.setText("" + String.format("%.5g%n", LineSegment.computeValue(parallel_lines,random_lines)));
            });
        clear.addActionListener(e -> random.clear());
        panel.add(buttons_text_panel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
