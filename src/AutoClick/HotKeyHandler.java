package AutoClick;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.melloware.jintellitype.HotkeyListener;

class HotKeyHandler implements HotkeyListener {
    Clicker clicker = Clicker.getInstance();
    static Thread myThread = new Thread();

    static int recordFlag = 0;

    JFrame f = new JFrame();

    @Override
    public void onHotKey(int markCode) {
        int globalHotKey1_flag = 0;
        globalHotKey1_flag++;
        if (markCode == AutoClick.GLOBAL_HOT_KEY_1) {
            if (globalHotKey1_flag == 1 && myThread.isAlive() == false) {
                Clicker.toClick = true;
                myThread = new Thread(clicker);
                myThread.start();
                globalHotKey1_flag++;
                Clicker.toRecord = false;
            } else if (globalHotKey1_flag == 3 && !myThread.isAlive()) {
                Clicker.toClick = true;
                myThread = new Thread(clicker);
                myThread.start();
                globalHotKey1_flag = 0;
                Clicker.toRecord = false;
            } else if ((globalHotKey1_flag == 3 && myThread.isAlive() == true)
                    || (globalHotKey1_flag == 1 && myThread.isAlive() == true)) {
                myThread.stop();
                globalHotKey1_flag = 0;
                Clicker.toRecord = true;
            }
        } else if (markCode == AutoClick.GLOBAL_HOT_KEY_2) {
            if (Clicker.toRecord == true && myThread.isAlive() == false) {
                AutoClick.normalizeFrameState.setSelected(true);
                AutoClick.keepOnTop.setSelected(true);
                f.dispose();
                f.setUndecorated(true);
                f.setLayout(null);
                f.setSize(330, 100);
                f.setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 330, 50);
                f.setVisible(true);
                f.setAlwaysOnTop(true);

                JButton recordButton = new JButton("record");
                recordButton.setBounds(5, 15,75, 25);
                f.add(recordButton);

                JLabel xLabel = new JLabel("X :");
                xLabel.setBounds(85, 10, 20, 20);
                JLabel xAxis = new JLabel();
                xAxis.setBounds(105, 10, 40, 20);
                String[] xMove = new String[101];
                for (int i = 0; i < xMove.length; i++)
                    xMove[i] = i + "";
                JComboBox<String> xMoveComboBox = new JComboBox<String>(xMove);
                xMoveComboBox.setBounds(135, 10, 50, 20);
                f.add(xMoveComboBox);
                xMoveComboBox.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if (e.getItem() == xMove[xMoveComboBox.getSelectedIndex()])
                            Clicker.xMove.set(recordFlag - 1, xMoveComboBox.getSelectedIndex());
                    }
                });

                JLabel yLabel = new JLabel("Y :");
                yLabel.setBounds(85, 35, 20, 20);
                JLabel yAxis = new JLabel();
                yAxis.setBounds(105, 35, 40, 20);
                String[] yMove = new String[101];
                for (int i = 0; i < yMove.length; i++)
                    yMove[i] = i + "";
                JComboBox<String> yMoveComboBox = new JComboBox<String>(yMove);
                yMoveComboBox.setBounds(135, 35, 50, 20);
                f.add(yMoveComboBox);

                yMoveComboBox.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if (e.getItem() == yMove[yMoveComboBox.getSelectedIndex()])
                            Clicker.yMove.set(recordFlag - 1, yMoveComboBox.getSelectedIndex());
                    }
                });
                String[] xMinus = { "N", "Y" };
                JComboBox<String> xMinusComboBox = new JComboBox<String>(xMinus);
                xMinusComboBox.setBounds(190, 10, 40, 20);
                f.add(xMinusComboBox);

                xMinusComboBox.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        if (e.getItem() == xMinus[0])
                            Clicker.xMinus.set(recordFlag - 1, false);
                        else if (e.getItem() == xMinus[1])
                            Clicker.xMinus.set(recordFlag - 1, true);
                    }
                });
                String[] yMinus = { "N", "Y" };
                JComboBox<String> yMinusComboBox = new JComboBox<String>(yMinus);
                yMinusComboBox.setBounds(190, 35, 40, 20);
                f.add(yMinusComboBox);
                yMinusComboBox.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        if (e.getItem() == yMinus[0])
                            Clicker.yMinus.set(recordFlag - 1, false);
                        else if (e.getItem() == yMinus[1])
                            Clicker.yMinus.set(recordFlag - 1, true);
                    }
                });

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Point point = java.awt.MouseInfo.getPointerInfo().getLocation();
                        xAxis.setText("" + point.x);
                        yAxis.setText("" + point.y);
                    }
                }, 10, 10);

                JButton magnifyingGlass = new JButton("Magnify");
                magnifyingGlass.setBounds(240, 20, 80, 20);
                magnifyingGlass.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MagnifyingGlass.main(null);
                    }
                });
                f.add(magnifyingGlass);

                f.add(xLabel);
                f.add(xAxis);
                f.add(yLabel);
                f.add(yAxis);

                String[] buttonsToClick = { "left", "middle", "right" };
                JComboBox<String> buttonsToClickComboBox = new JComboBox<String>(buttonsToClick);
                buttonsToClickComboBox.setBounds(10, 75, 70, 20);
                f.add(buttonsToClickComboBox);
                buttonsToClickComboBox.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if (e.getItem() == buttonsToClick[0])
                            Clicker.buttonsToClickList.set(recordFlag - 1, InputEvent.BUTTON1_DOWN_MASK);
                        else if (e.getItem() == buttonsToClick[1])
                            Clicker.buttonsToClickList.set(recordFlag - 1, InputEvent.BUTTON2_DOWN_MASK);
                        else
                            Clicker.buttonsToClickList.set(recordFlag - 1, InputEvent.BUTTON3_DOWN_MASK);
                    }
                });

                String[] methodsOfClick = { "single", "double" };
                JComboBox<String> methodsOfClickComboBox = new JComboBox<String>(methodsOfClick);
                methodsOfClickComboBox.setBounds(95, 75, 80, 20);
                f.add(methodsOfClickComboBox);
                methodsOfClickComboBox.addItemListener(new ItemListener() {

                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if (e.getItem() == methodsOfClick[0])
                            Clicker.toDoubleClickList.set(recordFlag - 1, false);
                        else if (e.getItem() == methodsOfClick[1])
                            Clicker.toDoubleClickList.add(recordFlag - 1, true);
                    }
                });

                JButton moveWindow = new JButton("move");
                moveWindow.setBounds(190, 75, 75, 20);
                f.add(moveWindow);
                moveWindow.addActionListener(new ActionListener() {
                    int flag = 0;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        flag++;
                        if (flag == 1) {
                            f.setLocation(30, 30);
                            flag++;
                        } else if (flag == 3) {
                            f.setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 320, 50);
                            flag = 0;
                        }
                    }
                });

                recordButton.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        recordFlag++;
                        AutoClick.recordTextField.setText("" + recordFlag);
                        Point point = MouseInfo.getPointerInfo().getLocation();
                        Clicker.pointsList.add(point);
                        Robot r = null;
                        try {
                            r = new Robot();
                        } catch (AWTException e1) {
                            e1.printStackTrace();
                        }
                        Clicker.xMove.add(xMoveComboBox.getSelectedIndex());
                        Clicker.yMove.add(yMoveComboBox.getSelectedIndex());
                        if (xMinusComboBox.getSelectedIndex() == 0)
                            Clicker.xMinus.add(false);
                        else if (xMinusComboBox.getSelectedIndex() == 1)
                            Clicker.xMinus.add(true);

                        if (yMinusComboBox.getSelectedIndex() == 0)
                            Clicker.yMinus.add(false);
                        else if (yMinusComboBox.getSelectedIndex() == 1)
                            Clicker.yMinus.add(true);
                        Clicker.colorsList.add(new SelectColor(r.getPixelColor(0, 0), false));

                        if (buttonsToClickComboBox.getSelectedIndex() == 0)
                            Clicker.buttonsToClickList.add(InputEvent.BUTTON1_DOWN_MASK);
                        else if (buttonsToClickComboBox.getSelectedIndex() == 1)
                            Clicker.buttonsToClickList.add(InputEvent.BUTTON2_DOWN_MASK);
                        else if (buttonsToClickComboBox.getSelectedIndex() == 2)
                            Clicker.buttonsToClickList.add(InputEvent.BUTTON3_DOWN_MASK);

                        if (methodsOfClickComboBox.getSelectedIndex() == 0)
                            Clicker.toDoubleClickList.add(false);
                        else if (methodsOfClickComboBox.getSelectedIndex() == 1)
                            Clicker.toDoubleClickList.add(true);

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }
                });
                Clicker.toRecord = false;
            } else if (Clicker.toRecord == false || (Clicker.toRecord == true && myThread.isAlive() == true)) {
                AutoClick.normalizeFrameState.setSelected(false);
                f.dispose();
                f.setVisible(false);
                f.setAlwaysOnTop(false);
                Clicker.toRecord = true;
                globalHotKey1_flag = 0;
            }
        } else if (markCode == AutoClick.GLOBAL_HOT_KEY_3) {
            try {
                Robot r = new Robot();
                Point p = MouseInfo.getPointerInfo().getLocation();
                Color color = r.getPixelColor((int) p.getX(), (int) p.getY());
                SelectColor selectColor = new SelectColor(color, true);
                Clicker.colorsList.set(recordFlag - 1, selectColor);

                JOptionPane
                        .showMessageDialog(null,
                                String.format("Color %d>> R: %d G: %d B:%d", recordFlag, color.getRed(),
                                        color.getGreen(), color.getBlue()),
                                "Color Selected", JOptionPane.INFORMATION_MESSAGE);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }
}