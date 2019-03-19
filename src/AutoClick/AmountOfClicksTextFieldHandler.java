package AutoClick;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AmountOfClicksTextFieldHandler implements KeyListener {
    /*
     * 限制不合法输入
     */
    @Override
    public void keyTyped(KeyEvent e) {
        int keyChar = e.getKeyChar();
        if (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) {

        } else {
            e.consume();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }
}