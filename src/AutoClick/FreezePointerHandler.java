package AutoClick;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;

class FreezePointerHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        AbstractButton absButton = (AbstractButton) e.getSource();
        boolean selected = absButton.getModel().isSelected();
        if (selected == true)
            Clicker.setFreezePointer(true);
        else if (selected == false)
            Clicker.setFreezePointer(false);
    }
}
