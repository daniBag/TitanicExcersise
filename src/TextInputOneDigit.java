import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TextInputOneDigit extends JTextField {

    public TextInputOneDigit(int x, int y) {
        this.setBounds(x, y, Constants.SINGLE_DIGIT_TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_HEIGHT);
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char input = e.getKeyChar();
                if (getText().length() >= 1 || !Character.isDigit(input)) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        this.setTransferHandler(new TransferHandler(){
            public void paste(JComponent component){

            }
        });
    }
}
