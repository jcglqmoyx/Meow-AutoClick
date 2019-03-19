package AutoClick;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

public class CounterCheckBoxHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        AbstractButton absButton = (AbstractButton) e.getSource();
        boolean selected = absButton.getModel().isSelected();
        if (selected == true)
            Clicker.clicks = AutoClick.getAmountOfClicks();
        else if (selected == false)
            Clicker.clicks = Long.MAX_VALUE;
    }
}
