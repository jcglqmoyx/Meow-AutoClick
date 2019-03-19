package AutoClick;

//点击间隔比实际设置的慢, 注意!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;

class SelectColor { // 取色类
    Color color;
    Boolean selectColor;

    public SelectColor(Color color, Boolean selectColor) {
        this.color = color;
        this.selectColor = selectColor;
    }
}

class Clicker implements Runnable {
    static ArrayList<Point> pointsList = new ArrayList<>();
    static ArrayList<Integer> xMove = new ArrayList<>();
    static ArrayList<Integer> yMove = new ArrayList<>();
    static ArrayList<Boolean> xMinus = new ArrayList<>();
    static ArrayList<Boolean> yMinus = new ArrayList<>();
    static ArrayList<SelectColor> colorsList = new ArrayList<>();
    static ArrayList<Integer> buttonsToClickList = new ArrayList<>();
    static ArrayList<Boolean> toDoubleClickList = new ArrayList<>();

    public static boolean toClick = true;
    static long clicks = Long.MAX_VALUE;
    static boolean toDoubleClick = false;
    static int buttonsToClick = InputEvent.BUTTON1_DOWN_MASK;

    static boolean freezePointer = false;
    static boolean smartClick = false;

    static boolean toRecord = true;

    private static Robot robot;

    static void setFreezePointer(boolean freezePointer) {
        Clicker.freezePointer = freezePointer;
    }

    static boolean isFreezePointer() {
        return freezePointer;
    }

    static void setSmartClick(boolean smartClick) {
        Clicker.smartClick = smartClick;
    }

    static boolean isSmartClick() {
        return smartClick;
    }

    private static final Clicker clicker = new Clicker();

    private Clicker() {
    }

    public static Clicker getInstance() {
        return clicker;
    }

    @Override
    public void run() {
        try {
            robot = new Robot();
            Point point = MouseInfo.getPointerInfo().getLocation();
            int x = (int) point.getX();
            int y = (int) point.getY();

            if (AutoClick.getClickInterval() == 0)
                AutoClick.setClickInterval(1);
            for (int i = 0; i < clicks; i++)
                if (toClick && !isSmartClick()) {
                    if (Clicker.isFreezePointer() == true)
                        robot.mouseMove(x, y);
                    if (!toDoubleClick)
                        singleClick(robot, buttonsToClick);
                    else if (toDoubleClick)
                        doubleClick(robot, buttonsToClick);
                    Thread.sleep(AutoClick.getClickInterval());
                } else if (toClick && isSmartClick()) {
                    for (int j = 0; j < HotKeyHandler.recordFlag; j++) {
                        int xPlus = 0, yPlus = 0;
                        if (!xMinus.get(j))
                            xPlus = xMove.get(j);
                        else if (xMinus.get(j))
                            xPlus = -xMove.get(j);
                        if (!yMinus.get(j))
                            yPlus = yMove.get(j);
                        else if (yMinus.get(j))
                            yPlus = -yMove.get(j);

                        int xLoc = (int) pointsList.get(j).getX();
                        int yLoc = (int) pointsList.get(j).getY();
                        xLoc += xPlus * i;
                        yLoc += yPlus * i;
                        robot.mouseMove(xLoc, yLoc);
                        if (colorsList.get(j).selectColor == true)
                            try {
                                System.out.println("color selected");
                                Color color = robot.getPixelColor(xLoc, yLoc);
                                while (colorsList.get(j).color.getRed() != color.getRed()
                                        || colorsList.get(j).color.getGreen() != color.getGreen()
                                        || colorsList.get(j).color.getGreen() != color.getGreen()) {
                                    System.out.println("loop while waiting");
                                    Thread.sleep(10);
                                    color = robot.getPixelColor(xLoc, yLoc);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        if (!toDoubleClickList.get(j)) {
                            singleClick(robot, buttonsToClickList.get(j));
                        } else if (toDoubleClickList.get(j))
                            doubleClick(robot, buttonsToClickList.get(j));

                        if (AutoClick.pauseInIteration.isSelected())
                            Thread.sleep(AutoClick.getClickInterval());
                    }
                    if (!AutoClick.pauseInIteration.isSelected())

                        Thread.sleep(AutoClick.getClickInterval());
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void singleClick(Robot robot, int buttons) {
        robot.mousePress(buttons);
        robot.mouseRelease(buttons);
    }

    private void doubleClick(Robot robot, int buttons) {
        singleClick(robot, buttons);
        singleClick(robot, buttons);
    }
}
