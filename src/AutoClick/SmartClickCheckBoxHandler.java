package AutoClick;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.AbstractButton;


public class SmartClickCheckBoxHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        AbstractButton absButton = (AbstractButton) e.getSource();
        boolean selected = absButton.getModel().isSelected();
        if (selected == true) {
            Clicker.setSmartClick(true);
            AutoClick.freezePointer.setEnabled(false);
            AutoClick.pauseInIteration.setEnabled(true);
        } else if (selected == false) {
            Clicker.setSmartClick(false);
            AutoClick.freezePointer.setEnabled(true);
            AutoClick.pauseInIteration.setEnabled(false);
        }
    }
}
