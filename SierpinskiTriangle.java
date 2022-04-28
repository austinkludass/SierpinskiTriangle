// Software created by Austin Kludass, Sierpinski Triangle visualiser

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SierpinskiTriangle extends JPanel implements MouseListener {
    private Dimension area;
    private Vector<Rectangle> circles;
    private JPanel drawingPane;
    private JButton play;
    private JButton reset;
    private JLabel counter;
    private boolean FLAG = false;
    private int startPointX = 0;
    private int startPointY = 0;
    private int count = 0;

    public SierpinskiTriangle() {
        super(new BorderLayout());

        area = new Dimension(0, 0);
        circles = new Vector<Rectangle>();

        // Triangle points
        Rectangle pA = new Rectangle(1000, 100, 5, 5);
        circles.addElement(pA);
        Rectangle pB = new Rectangle(700, 700, 5, 5);
        circles.addElement(pB);
        Rectangle pC = new Rectangle(1300, 700, 5, 5);
        circles.addElement(pC);

        //Set up the drawing pane
        drawingPane = new DrawingPane();
        drawingPane.setBackground(Color.black);
        drawingPane.addMouseListener(this);

        //Adding drawing pane to scroll pane
        JScrollPane scroller = new JScrollPane(drawingPane);
        scroller.setPreferredSize(new Dimension(1400, 1000));
        add(scroller, BorderLayout.CENTER);
        drawingPane.setLayout(null);

        // Plot counter and control buttons
        counter = new JLabel("<html> <head><style> .green {color: #00FF00;} </style></head><body><h3 style='color:white'>Points plotted: <span class='green'>" + count + "</span></h3> </body></html>");
        counter.setBounds(10, 600, 400, 50);
        drawingPane.add(counter);

        play = new JButton("Play");
        play.setBounds(10, 650, 100, 40);
        play.setEnabled(false);
        drawingPane.add(play);

        reset = new JButton("Reset");
        reset.setBounds(10, 700, 100, 40);
        reset.setEnabled(false);
        drawingPane.add(reset);


        // Play button listener
        play.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            play.setEnabled(false);
            int x = startPointX;
            int y = startPointY;
            int brushSize = 3;

            // Triangle point coords
            int aX = 1000;
            int aY = 100;
            int bX = 700;
            int bY = 700;
            int cX = 1300;
            int cY = 700;

            // Variables for main for loop
            Rectangle rect;
            int min = 1;
            int max = 3;
            int randPoint = 0;

            // Main algorithm that generates the points
            for (int i = 0; i < 30000; i++) {
              randPoint = (int)Math.floor(Math.random()*(max-min+1)+min);
              if (randPoint == 1) {
                x = (x + aX) / 2;
                y = (y + aY) / 2;
              }

              if (randPoint == 2) {
                x = (x + bX) / 2;
                y = (y + bY) / 2;
              }

              if (randPoint == 3) {
                x = (x + cX) / 2;
                y = (y + cY) / 2;
              }

              // Update every 50 points plotted
              if (count % 50 == 0) {
                waitMs(1);
                counter.setText("<html> <head><style> .green {color: #00FF00;} </style></head><body><h3 style='color:white'>Points plotted: <span class='green'>" + count + "</span></h3> </body></html>");
                drawingPane.paintImmediately(0, 0, drawingPane.getWidth(), drawingPane.getHeight());
              }

              rect = new Rectangle(x, y, brushSize, brushSize);
              circles.addElement(rect);
              count++;
          }
          reset.setEnabled(true);
        }
      });

      // Reset button listener
      reset.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          reset.setEnabled(false);

          // Removes all points and adds the triangle points
          circles.removeAllElements();
          Rectangle pA = new Rectangle(1000, 100, 5, 5);
          Rectangle pB = new Rectangle(700, 700, 5, 5);
          Rectangle pC = new Rectangle(1300, 700, 5, 5);
          circles.addElement(pA);
          circles.addElement(pB);
          circles.addElement(pC);

          drawingPane.repaint();
          FLAG = false;
          count = 0;
          counter.setText("<html> <head><style> .green {color: #00FF00;} </style></head><body><h3 style='color:white'>Points plotted: <span class='green'>" + count + "</span></h3> </body></html>");
        }
      });

      // All text labels
      JLabel heading = new JLabel("<html><h1 style='color:white; font-size:24px'>Sierpinski Triangle</h1></html>");
      heading.setBounds(10, 10, 400, 50);
      drawingPane.add(heading);

      JLabel rules = new JLabel("<html><h1 style='color:white'>Rules:</h1></html>");
      rules.setBounds(10, 80, 400, 50);
      drawingPane.add(rules);

      JLabel ruleOne = new JLabel("<html><h3 style='color:white'> • Pick any point inside (or out) of the triangle</h3></html>");
      ruleOne.setBounds(10, 115, 400, 50);
      drawingPane.add(ruleOne);

      JLabel ruleTwo = new JLabel("<html><h3 style='color:white'> • From that point the program will randomly choose point A, B or C and draw a point half way between the previous point and the selected point</h3></html>");
      ruleTwo.setBounds(10, 150, 400, 80);
      drawingPane.add(ruleTwo);

      JLabel note = new JLabel("<html><h3 style='color:white'>* Note that any deviation from the Sierpinski fractal is from the first few plotted points, depending on the user's first point</h3></html>");
      note.setBounds(10, 350, 400, 80);
      drawingPane.add(note);

      JLabel pointA = new JLabel("<html><h1 style='color:white'>A</h1></html>");
      pointA.setBounds(995, 55, 100, 50);
      drawingPane.add(pointA);

      JLabel pointB = new JLabel("<html><h1 style='color:white'>B</h1></html>");
      pointB.setBounds(695, 700, 100, 50);
      drawingPane.add(pointB);

      JLabel pointC = new JLabel("<html><h1 style='color:white'>C</h1></html>");
      pointC.setBounds(1295, 700, 100, 50);
      drawingPane.add(pointC);
    }

    // Creates small delay to visualise the triangle being created
    public static void waitMs(int ms) {
      try {
        Thread.sleep(ms);
      }
      catch(InterruptedException ex) {
        Thread.currentThread().interrupt();
      }
    }

    // Paint component
    public class DrawingPane extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.ORANGE);
            Rectangle rect;
            for (int i = 0; i < circles.size(); i++) {
                rect = circles.elementAt(i);
                g.fillOval(rect.x, rect.y, rect.width, rect.height);
            }
        }
    }

    // Mouse event listener
    public void mouseReleased(MouseEvent e) {
      if (!FLAG) {
        final int W = 5;
        final int H = 5;
        boolean changed = false;
        if (SwingUtilities.isLeftMouseButton(e)) {
            startPointX = e.getX() - W / 2;
            startPointY = e.getY() - H / 2;
            if (startPointX < 0)
                startPointX = 0;
            if (startPointY < 0)
                startPointY = 0;
            Rectangle rect = new Rectangle(startPointX, startPointY, W, H);
            circles.addElement(rect);
            drawingPane.scrollRectToVisible(rect);
            drawingPane.repaint();
            FLAG = true;
            play.setEnabled(true);
            count++;
            counter.setText("<html> <head><style> .green {color: #00FF00;} </style></head><body><h3 style='color:white'>Points plotted: <span class='green'>" + count + "</span></h3> </body></html>");
        }
      }
    }

    // Override all mouse events
    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    // GUI and window creation
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Sierpinski Triangle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        JComponent newContentPane = new SierpinskiTriangle();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);
    }

    // Main
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
