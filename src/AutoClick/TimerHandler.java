package AutoClick;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.*;
import java.io.IOException;
import java.util.Calendar;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;


public class TimerHandler implements ItemListener {
    StartClicking stc = new StartClicking();
    StopClicking spc = new StopClicking();
    Robot r;
    JFrame f = new JFrame();
    Operation o = Operation.DO_NOTHING;
    private final JLabel timeInterval[] = { new JLabel("hour"), new JLabel("minute"), new JLabel("second") };
    private final JSpinner timeIntervalSpinner[] = { new JSpinner(new SpinnerNumberModel(0, 0, 999, 1)),
            new JSpinner(new SpinnerNumberModel(0, 0, 59, 1)), new JSpinner(new SpinnerNumberModel(0, 0, 59, 1)), };
    private final JRadioButton laterCheckBox = new JRadioButton("after");
    private long timeLeft = Integer.MAX_VALUE;
    private final JLabel timeToTask[] = { new JLabel("year"), new JLabel("month"), new JLabel("date"), new JLabel("hour"),
            new JLabel("minute"), new JLabel("second") };
    private final JSpinner timeToTaskSpinner[] = { new JSpinner(new SpinnerNumberModel(0, 0, 9999, 1)),
            new JSpinner(new SpinnerNumberModel(0, 0, 12, 1)), new JSpinner(new SpinnerNumberModel(0, 0, 31, 1)),
            new JSpinner(new SpinnerNumberModel(0, 0, 23, 1)), new JSpinner(new SpinnerNumberModel(0, 0, 59, 1)),
            new JSpinner(new SpinnerNumberModel(0, 0, 59, 1)) };
    private final JRadioButton atCheckBox = new JRadioButton("at");
    private final JRadioButton shutdown = new JRadioButton("shutdown");
    private final JRadioButton restart = new JRadioButton("restart");
    private final JButton confirm = new JButton("OK");

    private long getTimeLeft() {
        return timeLeft;
    }

    private void setTimeLeft(long timeLeft) {
        this.timeLeft = timeLeft;
    }

    private void shutdown() {
        try {
            Runtime.getRuntime().exec(String.format("shutdown -s -t %d", getTimeLeft() / 1000));
            System.out.println("shutdown: " + getTimeLeft() / 1000);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void restart() {
        try {
            Runtime.getRuntime().exec(String.format("shutdown -r -t %d", getTimeLeft() / 1000));
            System.out.println("restart: " + getTimeLeft() / 1000);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void cancelShutdown() {
        try {
            Runtime.getRuntime().exec(String.format("shutdown -a"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        AbstractButton absButton = (AbstractButton) e.getSource();
        boolean selected = absButton.getModel().isSelected();
        if (selected == true) {
            f.setUndecorated(true);
            f.setLayout(null);
            f.setLocationRelativeTo(null);
            f.setSize(400, 150);
            f.setVisible(true);
            f.setAlwaysOnTop(true);

            for (int i = 0; i < timeInterval.length; i++) {
                timeInterval[i].setBounds(40 + i * 100, 10, 90, 20);
                f.add(timeInterval[i]);
                timeIntervalSpinner[i].setBounds(20 + i * 100, 30, 90, 20);
                f.add(timeIntervalSpinner[i]);
            }

            for (int i = 0; i < timeIntervalSpinner.length; i++) {
                JSpinner.NumberEditor editor = new JSpinner.NumberEditor(timeIntervalSpinner[i], "0");
                timeIntervalSpinner[i].setEditor(editor);
                JFormattedTextField textField = ((JSpinner.NumberEditor) timeIntervalSpinner[i].getEditor())
                        .getTextField();
                textField.setEditable(true);
                DefaultFormatterFactory factory = (DefaultFormatterFactory) textField.getFormatterFactory();
                NumberFormatter formatter = (NumberFormatter) factory.getDefaultFormatter();
                formatter.setAllowsInvalid(false);
            }
            for (int i = 0; i < timeIntervalSpinner.length; i++)
                timeIntervalSpinner[i].addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        timeLeft = 3600000 * (Integer) (timeIntervalSpinner[0].getValue())
                                + 60000 * (Integer) (timeIntervalSpinner[1].getValue())
                                + 1000 * (Integer) (timeIntervalSpinner[2].getValue());
                        setTimeLeft(timeLeft);
                        System.out.println("Time left: " + timeLeft);
                    }
                });

            laterCheckBox.setBounds(320, 30, 60, 20);
            laterCheckBox.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    if (laterCheckBox.isSelected()) {
                        for (int i = 0; i < timeToTaskSpinner.length; i++) {
                            timeToTask[i].setEnabled(false);
                            timeToTaskSpinner[i].setEnabled(false);
                        }
                    } else
                        for (int i = 0; i < timeToTaskSpinner.length; i++) {
                            timeToTask[i].setEnabled(true);
                            timeToTaskSpinner[i].setEnabled(true);
                        }
                }
            });
            f.add(laterCheckBox);

            for (int i = 0; i < timeToTask.length; i++) {
                timeToTask[i].setBounds(15 + i * 55, 55, 42, 20);
                f.add(timeToTask[i]);
                timeToTaskSpinner[i].setBounds(10 + i * 55, 80, 50, 20);
                f.add(timeToTaskSpinner[i]);
            }
            for (int i = 0; i < timeToTaskSpinner.length; i++) {
                JSpinner.NumberEditor editor = new JSpinner.NumberEditor(timeToTaskSpinner[i], "0");
                timeToTaskSpinner[i].setEditor(editor);
                JFormattedTextField textField = ((JSpinner.NumberEditor) timeToTaskSpinner[i].getEditor())
                        .getTextField();
                textField.setEditable(true);
                DefaultFormatterFactory factory = (DefaultFormatterFactory) textField.getFormatterFactory();
                NumberFormatter formatter = (NumberFormatter) factory.getDefaultFormatter();
                formatter.setAllowsInvalid(false);
            }

            Calendar c = Calendar.getInstance();
            timeToTaskSpinner[0].setValue(c.get(Calendar.YEAR));
            timeToTaskSpinner[1].setValue(c.get(Calendar.MONTH) + 1);// �·��Ǵ�0��ʼ��, ���Լ�1
            timeToTaskSpinner[2].setValue(c.get(Calendar.DAY_OF_MONTH));
            timeToTaskSpinner[3].setValue(c.get(Calendar.HOUR_OF_DAY) + 1);
            timeToTaskSpinner[4].setValue(c.get(Calendar.MINUTE));
            timeToTaskSpinner[5].setValue(c.get(Calendar.SECOND));
            for (int i = 0; i < timeToTask.length; i++)
                timeToTaskSpinner[i].addChangeListener(new ChangeListener() {

                    @Override
                    public void stateChanged(ChangeEvent e) {
                        c.set((int) timeToTaskSpinner[0].getValue(), (int) timeToTaskSpinner[1].getValue() - 1,
                                (int) timeToTaskSpinner[2].getValue(), (int) timeToTaskSpinner[3].getValue(),
                                (int) timeToTaskSpinner[4].getValue(), (int) timeToTaskSpinner[5].getValue());
                        System.out.println(c);
                    }
                });
            atCheckBox.setBounds(340, 80, 60, 20);
            atCheckBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (atCheckBox.isSelected())
                        for (int i = 0; i < timeIntervalSpinner.length; i++) {
                            timeInterval[i].setEnabled(false);
                            timeIntervalSpinner[i].setEnabled(false);
                        }
                    else if (!atCheckBox.isSelected())
                        for (int i = 0; i < timeIntervalSpinner.length; i++) {
                            timeInterval[i].setEnabled(true);
                            timeIntervalSpinner[i].setEnabled(true);
                        }
                }
            });
            f.add(atCheckBox);
            ButtonGroup bg = new ButtonGroup();
            bg.add(laterCheckBox);
            bg.add(atCheckBox);

            shutdown.setBounds(20, 110, 100, 20);
            f.add(shutdown);
            shutdown.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (shutdown.isSelected())
                        o = Operation.SHUTDOWN;
                    else if (!shutdown.isSelected())
                        o = Operation.DO_NOTHING;
                }
            });

            restart.setBounds(120, 110, 100, 20);
            f.add(restart);
            restart.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (restart.isSelected())
                        o = Operation.RESTART;
                    else if (!restart.isSelected())
                        o = Operation.DO_NOTHING;
                }
            });

            ButtonGroup bg1 = new ButtonGroup();
            bg1.add(shutdown);
            bg1.add(restart);

            confirm.setBounds(220, 110, 60, 20);
            f.add(confirm);
            confirm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (atCheckBox.isSelected())
                        setTimeLeft(c.getTimeInMillis() - Calendar.getInstance().getTimeInMillis());
                    f.dispose();
                    f.setVisible(false);
                    switch (o) {
                        case DO_NOTHING:
                            cancelShutdown();
                            break;
                        case SHUTDOWN:
                            shutdown();
                            break;
                        case RESTART:
                            restart();
                            break;
                    }
                }
            });

        } else if (selected == false)

        {
            cancelShutdown();

            f.dispose();
            f.setVisible(false);
        }

    }

    class StartClicking extends TimerTask {
        @Override
        public void run() {
            try {
                r = new Robot();
                r.keyPress(AutoClick.CLICK_HOT_KEY);
                r.keyRelease(AutoClick.CLICK_HOT_KEY);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }

    class StopClicking extends TimerTask {
        @Override
        public void run() {
            r.keyPress(AutoClick.CLICK_HOT_KEY);
            r.keyRelease(AutoClick.CLICK_HOT_KEY);
        }
    }
}
