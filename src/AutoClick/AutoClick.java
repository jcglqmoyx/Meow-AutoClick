package AutoClick;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.event.*;

import com.melloware.jintellitype.JIntellitype;
public class AutoClick extends JFrame {

    private static final long serialVersionUID = 1L; // version number
    static final int GLOBAL_HOT_KEY_1 = 0; // the first hotkey(clicking hotkey)
    static final int GLOBAL_HOT_KEY_2 = 1; // the second hotkey(recording hotkey)
    static final int GLOBAL_HOT_KEY_3 = 2; // the third hotkey(selecting-color hotkey)
    static int CLICK_HOT_KEY;//用户所选则的记录热键对应的值
    static int RECORD_HOT_KEY;
    static int SELECT_COLOR_HOT_KEY;

    private static long clickInterval;// 点击时间间隔
    static JCheckBox normalizeFrameState;// 使窗口正常化
    private final JLabel timeInterval[] = {new JLabel("hour"), new JLabel("minute"), new JLabel("second"), new JLabel("   0.1s"),
            new JLabel("  0.01s"), new JLabel(" 0.001s")};

    static String[] mouseButtons = {"left", "middle", "right"};
    private JComboBox<String> buttonsToClick;

    final static String[] methodsOfClick = {"single", "double"};
    private final JComboBox<String> methodsOfClickComboBox;

    private final static JSpinner timeIntervalSpinner[] = {new JSpinner(new SpinnerNumberModel(0, 0, 23, 1)),
            new JSpinner(new SpinnerNumberModel(0, 0, 59, 1)), new JSpinner(new SpinnerNumberModel(0, 0, 59, 1)),
            new JSpinner(new SpinnerNumberModel(0, 0, 9, 1)), new JSpinner(new SpinnerNumberModel(0, 0, 9, 1)),
            new JSpinner(new SpinnerNumberModel(0, 0, 9, 1))};

    static JCheckBox freezePointer;
    static JCheckBox keepOnTop;
    private final JCheckBox timer;
    static JCheckBox pauseInIteration;

    static JCheckBox smartClickCheckBox;

    private final JMenu setLanguage;

    private final JMenu help;

    private final JMenu hotKey;

    private final JMenuBar menuBar;

    private final JLabel mouse;
    private final JLabel click;
    private final JCheckBox counterCheckBox;
    private static JTextField amountOfClicksTextField;

    private final JLabel recordLabel;

    static JTextField recordTextField;

    private JButton clearRecordButton;

    private final JLabel amountOfClicksTextFieldLabel;

    public static long getClickInterval() {
        return clickInterval;
    }

    public static void setClickInterval(long clickInterval) {
        AutoClick.clickInterval = clickInterval;
    }

    public static long getAmountOfClicks() {
        return Long.parseLong(amountOfClicksTextField.getText());
    }

    public AutoClick() {
        super("Meow-AutoClick1.0 by:Chintsai");
        setLayout(null);

        normalizeFrameState = new JCheckBox();
        normalizeFrameState.setBounds(1, 1, 20, 20);
        normalizeFrameState.setVisible(false);
        normalizeFrameState.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (normalizeFrameState.isSelected() == true)
                    setState(JFrame.ICONIFIED);// 窗口最小化
                else if (normalizeFrameState.isSelected() == false)
                    setState(JFrame.NORMAL);// 窗口正常化
            }
        });

        add(normalizeFrameState);

        JIntellitype.getInstance().addHotKeyListener(new HotKeyHandler());// 注册事件处理器
        setLanguage = new JMenu("语言(L)");
        setLanguage.setMnemonic('L');
        JMenuItem en = new JMenuItem("English");
        en.setMnemonic('E');
        JMenuItem ch1 = new JMenuItem("简体中文(C)");
        ch1.setMnemonic('C');
        JMenuItem ch2 = new JMenuItem("繁體中文(C)");
        ch2.setMnemonic('C');
        JMenuItem jp = new JMenuItem("日本(J)");
        jp.setMnemonic('J');
        JMenuItem kr = new JMenuItem("한국(K)");
        kr.setMnemonic('K');

        setLanguage.add(en);
        setLanguage.add(ch1);
        setLanguage.add(ch2);
        setLanguage.add(jp);
        setLanguage.add(kr);

        hotKey = new JMenu("Hotkey");
        hotKey.setMnemonic('H');
        JMenu clickHotKey = new JMenu("Click Hotkey");
        clickHotKey.setMnemonic('C');
        JMenu recordHotKey = new JMenu("Record HotKey");
        recordHotKey.setMnemonic('R');
        JMenu selectColorHotKey = new JMenu("Select Color");
        selectColorHotKey.setMnemonic('S');
        JRadioButtonMenuItem[] clickHotKeys = new JRadioButtonMenuItem[32];
        JRadioButtonMenuItem[] recordHotKeys = new JRadioButtonMenuItem[32];
        JRadioButtonMenuItem[] selectColorHotKeys = new JRadioButtonMenuItem[32];
        for (int i = 0; i < clickHotKeys.length; i++) {
            clickHotKeys[i] = new JRadioButtonMenuItem();
            recordHotKeys[i] = new JRadioButtonMenuItem();
            selectColorHotKeys[i] = new JRadioButtonMenuItem();
        }
        for (int i = 0; i < 12; i++) {
            clickHotKeys[i].setText("" + String.format("F%d", i + 1));
            clickHotKey.add(clickHotKeys[i]);
            recordHotKeys[i].setText("" + String.format("F%d", i + 1));
            recordHotKey.add(recordHotKeys[i]);
            selectColorHotKeys[i].setText("" + String.format("F%d", i + 1));
            selectColorHotKey.add(selectColorHotKeys[i]);
        }
        for (int i = 12; i < 22; i++) {
            clickHotKeys[i].setText("Numpad: " + (i - 12));
            recordHotKeys[i].setText("Numpad: " + (i - 12));
            selectColorHotKeys[i].setText("Numpad: " + (i - 12));
            clickHotKey.add(clickHotKeys[i]);
            recordHotKey.add(recordHotKeys[i]);
            selectColorHotKey.add(selectColorHotKeys[i]);
        }
        for (int i = 22; i < clickHotKeys.length; i++) {
            clickHotKeys[i].setText("Master Keyboard: " + (i - 22));
            recordHotKeys[i].setText("Master Keyboard: " + (i - 22));
            selectColorHotKeys[i].setText("Master Keyboard: " + (i - 22));
            clickHotKey.add(clickHotKeys[i]);
            recordHotKey.add(recordHotKeys[i]);
            selectColorHotKey.add(selectColorHotKeys[i]);
        }
        ButtonGroup clickHotKeysButtonGroup = new ButtonGroup();
        for (int i = 0; i < clickHotKeys.length; i++)
            clickHotKeysButtonGroup.add(clickHotKeys[i]);
        ButtonGroup recordHotKeysButtonGroup = new ButtonGroup();
        for (int i = 0; i < recordHotKeys.length; i++)
            recordHotKeysButtonGroup.add(recordHotKeys[i]);
        ButtonGroup selectColorHotKeysButtonGroup = new ButtonGroup();
        for (int i = 0; i < selectColorHotKeys.length; i++)
            selectColorHotKeysButtonGroup.add(selectColorHotKeys[i]);

        for (int i = 0; i < clickHotKeys.length; i++) {
            int n = i;
            clickHotKeys[i].addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (clickHotKeys[n].isSelected()) {
                        recordHotKeys[n].setEnabled(false);
                        selectColorHotKeys[n].setEnabled(false);
                        if (n <= 11) {
                            JIntellitype.getInstance().registerHotKey(AutoClick.GLOBAL_HOT_KEY_1, 0, (n + 112));
                            CLICK_HOT_KEY = n + 112;
                        } else if (n >= 12 && n < 22) {
                            JIntellitype.getInstance().registerHotKey(AutoClick.GLOBAL_HOT_KEY_1, 0, (n + 84));
                            CLICK_HOT_KEY = n + 84;
                        } else if (n >= 22){
                            JIntellitype.getInstance().registerHotKey(AutoClick.GLOBAL_HOT_KEY_1, 0, (n + 26));
                            CLICK_HOT_KEY = n +27;
                        }
                        timer.setEnabled(true);
                    } else {
                        recordHotKeys[n].setEnabled(true);
                        selectColorHotKeys[n].setEnabled(true);
                        JIntellitype.getInstance().unregisterHotKey(AutoClick.GLOBAL_HOT_KEY_1);
                    }
                }
            });
        }
        for (int i = 0; i < recordHotKeys.length; i++) {
            int n = i;
            recordHotKeys[i].addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (recordHotKeys[n].isSelected()) {
                        clickHotKeys[n].setEnabled(false);
                        selectColorHotKeys[n].setEnabled(false);
                        if (n <= 11) {
                            JIntellitype.getInstance().registerHotKey(AutoClick.GLOBAL_HOT_KEY_2, 0, (n + 112));
                            RECORD_HOT_KEY = n + 112;
                        } else if (n >= 16 && n < 22) {
                            JIntellitype.getInstance().registerHotKey(AutoClick.GLOBAL_HOT_KEY_2, 0, (n + 84));
                            RECORD_HOT_KEY = n + 84;
                        }else if (n >= 22){
                            JIntellitype.getInstance().registerHotKey(AutoClick.GLOBAL_HOT_KEY_2, 0, (n + 26));
                            RECORD_HOT_KEY = n +27;
                        }
                        /*
                         * 注册快捷键(录制快捷键) n+112为相应键的Keycode值, 例如当n==0时, 该键为F1键, Keycode值为112
                         */
                    } else {
                        clickHotKeys[n].setEnabled(true);
                        selectColorHotKeys[n].setEnabled(true);
                        JIntellitype.getInstance().unregisterHotKey(AutoClick.GLOBAL_HOT_KEY_2);// 取消注册快捷键
                    }
                }
            });
        }
        for (int i = 0; i < selectColorHotKeys.length; i++) {
            int n = i;
            selectColorHotKeys[i].addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (selectColorHotKeys[n].isSelected()) {
                        clickHotKeys[n].setEnabled(false);
                        recordHotKeys[n].setEnabled(false);
                        if (n <= 11) {
                            JIntellitype.getInstance().registerHotKey(AutoClick.GLOBAL_HOT_KEY_3, 0, (n + 112));
                            SELECT_COLOR_HOT_KEY = n + 112;
                        } else if (n >= 16 && n < 23) {
                            JIntellitype.getInstance().registerHotKey(AutoClick.GLOBAL_HOT_KEY_3, 0, (n + 84));
                            SELECT_COLOR_HOT_KEY = n + 84;
                        }else if (n >= 22){
                            JIntellitype.getInstance().registerHotKey(AutoClick.GLOBAL_HOT_KEY_3, 0, (n + 26));
                            RECORD_HOT_KEY = n +27;
                        }
                    } else {
                        clickHotKeys[n].setEnabled(true);
                        recordHotKeys[n].setEnabled(true);
                        JIntellitype.getInstance().unregisterHotKey(AutoClick.GLOBAL_HOT_KEY_3);
                    }
                }
            });
        }
        hotKey.add(clickHotKey);
        hotKey.add(recordHotKey);
        hotKey.add(selectColorHotKey);

        help = new JMenu("Help");
        help.setMnemonic('H');

        menuBar = new JMenuBar();
        menuBar.add(hotKey);

        setJMenuBar(menuBar);

        mouse = new JLabel("Mouse");
        mouse.setBounds(20, 10, 50, 20);

        add(mouse);

        buttonsToClick = new JComboBox(mouseButtons);
        buttonsToClick.setBounds(70, 10, 70, 20);
        buttonsToClick.addItemListener(new ButtonsToClickComboBoxHandler());

        add(buttonsToClick);

        click = new JLabel("Click");
        click.setBounds(20, 40, 50, 20);

        add(click);

        methodsOfClickComboBox = new JComboBox(methodsOfClick);
        methodsOfClickComboBox.setBounds(70, 40, 70, 20);
        methodsOfClickComboBox.addItemListener(new methodsOfClickComboBoxHandler());

        add(methodsOfClickComboBox);

        counterCheckBox = new JCheckBox("Counter");
        counterCheckBox.setBounds(210, 10, 80, 20);
        counterCheckBox.setToolTipText("Stop clicking after finishing amount of clicks you specify");
        counterCheckBox.addActionListener(new CounterCheckBoxHandler());

        add(counterCheckBox);

        amountOfClicksTextField = new JTextField("0", 6);
        amountOfClicksTextField.setBounds(210, 40, 70, 20);

        amountOfClicksTextField.addKeyListener(new AmountOfClicksTextFieldHandler());

        Document doc1 = amountOfClicksTextField.getDocument();
        doc1.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                Clicker.clicks = Long.parseLong(amountOfClicksTextField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        add(amountOfClicksTextField);
        amountOfClicksTextField.setToolTipText("Input the amount of clicks");
        amountOfClicksTextFieldLabel = new JLabel("( clicks )");
        amountOfClicksTextFieldLabel.setBounds(290, 40, 50, 20);

        add(amountOfClicksTextFieldLabel);

        for (int i = 0; i < timeInterval.length; i++) {
            timeInterval[i].setBounds(20 + i * 55, 70, 45, 20);
            add(timeInterval[i]);
            timeIntervalSpinner[i].setBounds(20 + i * 55, 100, 43, 20);
            add(timeIntervalSpinner[i]);
        }

        timeIntervalSpinner[0].setToolTipText("input a number between 0 - 9");

        timeIntervalSpinner[1].setToolTipText("input a number between 0 - 23");

        timeIntervalSpinner[2].setToolTipText("input a number between 0 - 23");
        for (int i = 3; i < 6; i++)
            timeIntervalSpinner[i].
                    setToolTipText("input a number between 0 - 9");
        /*
         * 限制JSpinner中不合法的输入
         */
        for (int i = 0; i < timeIntervalSpinner.length; i++) {
            JSpinner.NumberEditor editor = new JSpinner.NumberEditor(timeIntervalSpinner[i], "0");
            timeIntervalSpinner[i].setEditor(editor);
            JFormattedTextField textField = ((JSpinner.NumberEditor) timeIntervalSpinner[i].getEditor()).getTextField();
            textField.setEditable(true);
            DefaultFormatterFactory factory = (DefaultFormatterFactory) textField.getFormatterFactory();
            NumberFormatter formatter = (NumberFormatter) factory.getDefaultFormatter();
            formatter.setAllowsInvalid(false);
        }

        /*
         * set the interval of click
         */
        for (int i = 0; i < timeIntervalSpinner.length; i++) {
            final JTextField jtf = ((JSpinner.DefaultEditor) timeIntervalSpinner[i].getEditor()).getTextField();
            jtf.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void removeUpdate(DocumentEvent e) {
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    try {
                        setClickInterval(3600000 * (Integer) (timeIntervalSpinner[0].getValue())
                                + 60000 * (Integer) (timeIntervalSpinner[1].getValue())
                                + 1000 * (Integer) (timeIntervalSpinner[2].getValue())
                                + 100 * (Integer) (timeIntervalSpinner[3].getValue())
                                + 10 * (Integer) (timeIntervalSpinner[4].getValue())
                                + 1 * (Integer) (timeIntervalSpinner[5].getValue()));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                // TODO
                @Override
                public void changedUpdate(DocumentEvent e) {
                }
            });
        }

        freezePointer = new JCheckBox("Freeze pointer");
        freezePointer.setBounds(20, 130, 110, 20);
        freezePointer.setToolTipText("Freeze the pointer at the position you want to click");
        freezePointer.addActionListener(new FreezePointerHandler());

        add(freezePointer);

        keepOnTop = new JCheckBox("Keep on top");
        keepOnTop.setBounds(20, 150, 105, 20);
        keepOnTop.setToolTipText("Keep the interface on the top");
        keepOnTop.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (keepOnTop.isSelected())
                    setAlwaysOnTop(true);
                if (!keepOnTop.isSelected())
                    setAlwaysOnTop(false);
            }
        });

        add(keepOnTop);

        timer = new JCheckBox("Timer");
        timer.setBounds(127, 130, 95, 20);
        timer.addItemListener(new TimerHandler());
        timer.setEnabled(false);

        add(timer);

        pauseInIteration = new JCheckBox("Pause in iteration");
        pauseInIteration.setBounds(220, 130, 125, 20);
        pauseInIteration.setToolTipText("Pause for the interval you specify after every click( pause after every iteratioon if not selected )");
        pauseInIteration.setEnabled(false);

        add(pauseInIteration);

        smartClickCheckBox = new JCheckBox("Smart click");
        smartClickCheckBox.setBounds(127, 150, 100, 20);
        smartClickCheckBox.setToolTipText("Click multiple positions and repeat to do that");
        smartClickCheckBox.addActionListener(new SmartClickCheckBoxHandler());

        add(smartClickCheckBox);

        recordLabel = new JLabel("Record:");
        recordLabel.setBounds(25, 175, 55, 20);

        add(recordLabel);

        recordTextField = new JTextField("0");
        recordTextField.setEditable(false);
        recordTextField.setBounds(80, 175, 30, 20);

        add(recordTextField);

        clearRecordButton = new JButton("Clear");
        clearRecordButton.setToolTipText("Clear records");
        clearRecordButton.setBounds(25, 200, 90, 20);
        clearRecordButton.addActionListener(new ClearRecordButtonHandler());

        add(clearRecordButton);
    }
}
