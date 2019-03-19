package AutoClick;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class methodsOfClickComboBoxHandler implements ItemListener {

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getItem() == AutoClick.methodsOfClick[0])
            Clicker.toDoubleClick = false;
        else if (e.getItem() == AutoClick.methodsOfClick[1])
            Clicker.toDoubleClick = true;
    }
}
