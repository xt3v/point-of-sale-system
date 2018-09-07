package services;

import dataFormats.dataFormat;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;

public class ViewService {
    private static  ViewService viewService = null;
    private Stage stage;
    private HashMap<String, dataFormat> messages;

    private ViewService(){
           messages = new HashMap<>();
    }

    public static ViewService getViewService(){
        if (viewService == null) viewService = new ViewService();
        return viewService;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void changeScene(String fxmlPath){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToMenu() {
          changeScene("../views/menu.fxml");
    }

    //Pass values from modals to the views tha opened them
    public void passMessage(String to, dataFormat message){
        messages.put(to,message);
    }

    //get the message passed from modal
    public dataFormat getMessage(String key){
        if(messages.containsKey(key))
        return messages.get(key);

        return null;
    }

    //for pop ups information
    public void showMessage(String mesage){
        JOptionPane.showMessageDialog(null,mesage);
    }

    public int showConfirmation(String message){
        //setup confirm pane
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (null, message,"Warning",dialogButton);
        return dialogResult;
    }
}
