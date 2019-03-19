package AutoClick;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ClearRecordButtonHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        Clicker.pointsList.clear();
        Clicker.buttonsToClickList.clear();
        Clicker.colorsList.clear();
        Clicker.pointsList.clear();
        Clicker.toDoubleClickList.clear();
        HotKeyHandler.recordFlag = 0;
        Clicker.colorsList.clear();
        Clicker.xMinus.clear();
        Clicker.xMove.clear();
        Clicker.yMinus.clear();
        Clicker.yMove.clear();
        AutoClick.recordTextField.setText("" + HotKeyHandler.recordFlag);
    }
}
