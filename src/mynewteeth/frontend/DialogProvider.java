/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.frontend;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import mynewteeth.backend.interfaces.ICustomDialogAction;
import mynewteeth.backend.interfaces.IOptionDialogAction;

/**
 *
 * @author Us
 */
public class DialogProvider {

    public static void showConfirmDialog(String message, String cautionFrameTitle, Object deletedObject, IOptionDialogAction callback) { // pass data if need
        new Thread(() -> {
            showSyncConfirmDialog(message, cautionFrameTitle, deletedObject, callback);
        }).start();
    }

    public static void showSyncConfirmDialog(String message, String cautionFrameTitle, Object deletedObject, IOptionDialogAction callback) {
        int result = JOptionPane.showConfirmDialog(null,
                message,
                cautionFrameTitle,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            callback.onYesOption(deletedObject);
        } else {
            callback.onNoOption();
        }
    }

    public static void showMessageDialog(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static class CustomDialog extends JDialog {
        private static final int WIDTH = 350, HEIGHT = 210;
        private static final int R_BG = 0, G_BG = 51, B_BG = 102, A = 255;
        private static final int R_FI = 204, G_FI = 204, B_FI = 255; // [204,204,255]
        private static final int R_SE = 153, G_SE = 204, B_SE = 255; // [153,204,255]
        private static final int R_TH = 255, G_TH = 255, B_TH = 255; // [153,204,255]
        private Color bgColor, firstColor, secondColor, thirdColor;
        // xoa , de trong , hoan tac
        private static JPanel container;
        private static JTextArea messageArea;
        private static JButton firstOptionBtn, secondOptionBtn, thirdOptionBtn; 
        private static ICustomDialogAction callback;
        private static CustomDialog instance;
        
        private CustomDialog(JFrame parent, String message, String title, boolean modal, String[] options) {
            super(parent, title, modal);
            initUI(message, options);
            initDialogBehavior();
        }
        
        private void initButtonUI(JButton button, Color background, String text) {
            button.setBackground(background);
            button.setBorder(null);
            button.setText(text);
            button.setForeground(Color.BLACK);
            button.getFont().deriveFont(Font.BOLD, 12.0f);
        }
        
        private void initUI(String message, String[] options) {
            // color
            bgColor = new Color(R_BG, G_BG, B_BG, A);
            firstColor = new Color(R_FI, G_FI, B_FI, A);
            secondColor = new Color(R_SE, G_SE, B_SE, A);
            thirdColor = new Color(R_TH, G_TH, B_TH, A);
            // component
            this.setSize(WIDTH, HEIGHT);
            this.setResizable(false);
            this.setLocationRelativeTo(null);
            setDefaultLookAndFeelDecorated(false);
            container = new JPanel(null);
            container.setSize(WIDTH, HEIGHT);
            container.setBackground(bgColor);
            //
            messageArea = new JTextArea();
            messageArea.setBounds(10, 10, WIDTH - 20, HEIGHT / 3 + 10);
            messageArea.setBackground(bgColor);
            messageArea.setText(message);
            messageArea.setLineWrap(true);
            messageArea.setForeground(thirdColor);
            // 
            firstOptionBtn = new JButton();
            firstOptionBtn.setBounds(20, 110, 80, 30);
            initButtonUI(firstOptionBtn, firstColor, options[0]);
            secondOptionBtn = new JButton();
            secondOptionBtn.setBounds(127, 110, 80, 30);
            initButtonUI(secondOptionBtn, secondColor, options[1]);
            thirdOptionBtn = new JButton();
            thirdOptionBtn.setBounds(235, 110, 80, 30);
            initButtonUI(thirdOptionBtn, thirdColor, options[2]);
            // 
            container.add(messageArea);
            container.add(firstOptionBtn);
            container.add(secondOptionBtn);
            container.add(thirdOptionBtn);
            this.add(container);
        }
        
        private void initDialogBehavior() {
            firstOptionBtn.addActionListener((e) -> {
                callback.onFirstChoosed();
            });
            secondOptionBtn.addActionListener((e) -> {
                callback.onSecondChoosed();
            });
            thirdOptionBtn.addActionListener((e) -> {
                callback.onThirdChoosed();
            });
        }
        
        public static CustomDialog getDialog(String message, String title, String[] options, ICustomDialogAction actionCallback) {
            if(instance == null) {
                instance = new CustomDialog(null, message, title, true, options);
            }
            callback = actionCallback;
            return instance;
        }
        
        public static void hideDialog() {
            instance.setVisible(false);
        }
        
        public static void disposeDialog() {
            instance.dispose();
        }
    }
}
