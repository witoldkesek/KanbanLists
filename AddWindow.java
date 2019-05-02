package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddWindow {
    @FXML public javafx.scene.control.Button addButton;
    @FXML private TextField TitleField;
    @FXML private TextField DateField;
    @FXML private TextArea DescArea;
    @FXML public ChoiceBox<String> choice;

    public boolean OkClicked=false;

    @FXML protected void AddButton(ActionEvent event)
    {
        OkClicked=true;
        Stage stage=(Stage) addButton.getScene().getWindow();
        stage.close();
    }
    Kanban function()
    {
        Kanban b=new Kanban(TitleField.getText(),DateField.getText(),DescArea.getText(),choice.getValue());
        return b;
    }
    void setTitleText(String a)
    {
        TitleField.setText(a);
    }
    void setDateText(String a)
    {
        DateField.setText(a);
    }
    void setDescText(String a)
    {
        DescArea.setText(a);
    }
    void setChoiceValue(String a)
    {
        choice.setValue(a);
    }
}
