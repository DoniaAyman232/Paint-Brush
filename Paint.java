import java.applet.Applet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Button;
import java.awt.*;
import java.util.*;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Paint extends Applet {
    int x1;
    int y1;
    int x2;
    int y2;
    int width;
    int height;
    boolean c = false;
    boolean fillpress = false;
    Button btnLine;
    Button btnRectangle;
    Button btnOval;
    Button btnRed;
    Button btnGreen;
    Button btnBlue;
    Button btnClearAll;
    Button btnEraser;
    Button btnPencil;
    Checkbox chkFill;
    char currentShape;
    Color currentColor;

    ArrayList<Geoshape> geoshape;

    public void init() {
        geoshape = new ArrayList<Geoshape>();

        btnLine = new Button("Line");
        add(btnLine);
        btnLine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentShape = 'l';
            }
        });

        btnRectangle = new Button("Rectangle");
        add(btnRectangle);
        btnRectangle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentShape = 'r';
            }
        });

        btnOval = new Button("Oval");
        add(btnOval);
        btnOval.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentShape = 'o';
            }
        });

        btnRed = new Button("Red");
        add(btnRed);
        btnRed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentColor = Color.red;
            }
        });

        btnGreen = new Button("Green");
        add(btnGreen);
        btnGreen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentColor = Color.green;
            }
        });

        btnBlue = new Button("Blue");
        add(btnBlue);
        btnBlue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentColor = Color.blue;
            }
        });

        btnClearAll = new Button("ClearAll");
        add(btnClearAll);
        btnClearAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                geoshape.clear();
                c = true;
                repaint();
            }
        });

        btnEraser = new Button("Eraser");
        add(btnEraser);
        btnEraser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentShape = 'e';
            }
        });

        btnPencil = new Button("Pencil");
        add(btnPencil);
        btnPencil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentShape = 'p';
            }
        });

        chkFill = new Checkbox("Fill");
        add(chkFill);
        chkFill.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                fillpress = chkFill.getState();
                repaint();
            }
        });

        this.addMouseListener(new MousePress());
        this.addMouseMotionListener(new Mousedrag());
    }

    public void paint(Graphics g) {
        if (c == false) {
            for (int i = 0; i < geoshape.size(); i++) {
                geoshape.get(i).draw(g);
            }

            int minX = Math.min(x1, x2);
            int minY = Math.min(y1, y2);
            width = Math.abs(x2 - x1);
            height = Math.abs(y2 - y1);
            g.setColor(currentColor);

            switch (currentShape) {
                case 'r':
                    g.drawRect(minX, minY, width, height);
                    if (fillpress) {
                        g.fillRect(minX, minY, width, height);
                    }
                    break;

                case 'l':
                    g.drawLine(x1, y1, x2, y2);
                    break;

                case 'o':
                    g.drawOval(minX, minY, width, height);
                    if (fillpress) {
                        g.setColor(currentColor);
                        g.fillOval(minX, minY, width, height);
                    }
                    break;

                case 'e':
                    g.setColor(Color.white);
                    g.fillRect(x2, y2, 10, 10);
                    break;

                case 'p':
                    g.setColor(currentColor);
                    g.fillRect(x2, y2, 1, 1);
                    break;
            }
        } else {
            c = false;
        }
    }

    class MousePress implements MouseListener {
        public void mouseClicked(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
            x1 = e.getX();
            y1 = e.getY();
        }

        public void mouseReleased(MouseEvent e) {
            x2 = e.getX();
            y2 = e.getY();
            int minX = Math.min(x1, x2);
            int minY = Math.min(y1, y2);
            width = Math.abs(x2 - x1);
            height = Math.abs(y2 - y1);

            switch (currentShape) {
                case 'l':
                    geoshape.add(new Line(x1, y1, x2, y2, currentColor, fillpress));
                    break;
                case 'r':
                    geoshape.add(new Rectangle(minX, minY, width, height, currentColor, fillpress));
                    break;
                case 'o':
                    geoshape.add(new Oval(minX, minY, width, height, currentColor, fillpress));
                    break;
            }

            repaint();
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    }

    class Mousedrag implements MouseMotionListener {
        public void mouseDragged(MouseEvent e) {
            x2 = e.getX();
            y2 = e.getY();

            if (currentShape == 'e') {
                geoshape.add(new Rectangle(x2, y2, 10, 10, Color.white, fillpress));
            } else if (currentShape == 'p') {
                geoshape.add(new Rectangle(x2, y2, 1, 1, currentColor, fillpress));
            }

            repaint();
        }

        public void mouseMoved(MouseEvent e) {
        }
    }

    public abstract class Geoshape {
        protected int x1;
        protected int y1;
        protected int x2;
        protected int y2;
        protected Color color;
        protected boolean fillpress;

        public abstract void draw(Graphics g);

        public Geoshape() {

        }

        public Geoshape(int x1, int y1, int x2, int y2, Color color, boolean fillpress) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;
            this.fillpress = fillpress;
        }

        boolean isfill() {
            return fillpress;
        }

        public void setx1(int x1) {
            this.x1 = x1;
        }

        public void sety1(int y1) {
            this.y1 = y1;
        }

        public void setx2(int x2) {
            this.x2 = x2;
        }

        public void sety2(int y2) {
            this.y2 = y2;
        }

        public int getx1() {
            return x1;
        }

        public int gety1() {
            return y1;
        }

        public int getx2() {
            return x2;
        }

        public int gety2() {
            return y2;
        }
    }

    class Line extends Geoshape {
        public Line() {

        }

        public void draw(Graphics g) {
            g.setColor(color);
            g.drawLine(this.x1, this.y1, this.x2, this.y2);
        }

        public Line(int x1, int y1, int x2, int y2, Color color, boolean fillpress) {
            super(x1, y1, x2, y2, color, fillpress);
        }
    }

    class Rectangle extends Geoshape {
        public void draw(Graphics g) {
            g.setColor(color);
            g.drawRect(x1, y1, x2, y2);
            if (fillpress) {
                g.setColor(color);
                g.fillRect(x1, y1, x2, y2);
            }
        }

        public Rectangle() {

        }

        public Rectangle(int x1, int y1, int x2, int y2, Color color, boolean fillpress) {
            super(x1, y1, x2, y2, color, fillpress);
        }
    }

    class Oval extends Geoshape {
        public Oval() {

        }

        public void draw(Graphics g) {
            g.setColor(color);
            g.drawOval(x1, y1, x2, y2);
            if (fillpress) {
                g.setColor(color);
                g.fillOval(x1, y1, x2, y2);
            }
        }

        public Oval(int x1, int y1, int x2, int y2, Color color, boolean fillpress) {
            super(x1, y1, x2, y2, color, fillpress);
        }
    }
}
