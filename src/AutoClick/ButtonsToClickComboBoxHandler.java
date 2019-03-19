package AutoClick;

import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class ButtonsToClickComboBoxHandler implements ItemListener {

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getItem() == AutoClick.mouseButtons[0])
            Clicker.buttonsToClick = InputEvent.BUTTON1_DOWN_MASK;
        else if (e.getItem() == AutoClick.mouseButtons[1])
            Clicker.buttonsToClick = InputEvent.BUTTON2_DOWN_MASK;
        else
            Clicker.buttonsToClick = InputEvent.BUTTON3_DOWN_MASK;
    }
}
