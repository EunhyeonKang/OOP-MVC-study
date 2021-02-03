import javax.swing.*;

public class MultiChatData {
    JTextArea msgOut;

    public void addObj(JComponent jComponent){
        msgOut = (JTextArea) jComponent;
    }

    public void refreshData(String data){
        msgOut.append(data);
    }
}
