package AutoClick;

/*
 * keep tracking of the input of AmoutOfClicksTextField, update the number of clicks in time
 * https://blog.csdn.net/joy_125/article/details/45742345
 */
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

class AmountOfClicksTextFieldInsertUpdateHandler implements DocumentListener {
    @Override
    public void insertUpdate(DocumentEvent e) {
        Document doc = e.getDocument();
        try {
            String s = doc.getText(0, doc.getLength());
            Clicker.clicks = Long.parseLong(s);
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
    }

    public void changedUpdate(DocumentEvent e) {
    }
}
