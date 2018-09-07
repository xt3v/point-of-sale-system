package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.LoginService;
import services.ViewService;

public class LoginController {

    @FXML TextField username;
    @FXML PasswordField password;
    @FXML Label errorLabel;     //display errors

    /*
     *called when login button clicked
     */
    @FXML void loginAction(){
       if(!validateInputs()) return;      //validate input exit if validation fails
        boolean isLoggedin= LoginService.getLoginSerice().login(username.getText(),password.getText());    //login user

        if (isLoggedin)  {
             ViewService.getViewService().changeScene("../views/menu.fxml");   //check if login success and change view to menu
        }else{
            errorLabel.setText("Wrong Login Credentials !!");  //if login failed display error
        }

    }


    /*
      *Validates input
     */
    private boolean validateInputs() {
        boolean res = true;
        if(password.getText().trim().equals("") ||  username.getText().trim().equals("") ) {   //check if inputs are empty
            res = false;           //if either empty return false and display error
            errorLabel.setText("Please Enter Username and Password");
        }
            return res;
    }


}
