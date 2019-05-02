package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML public ListView<Kanban> ToDoList;
    @FXML public ListView<Kanban> InProgressList;
    @FXML public ListView<Kanban> DoneList;
    ObservableList<Kanban>ToDoL=FXCollections.observableArrayList();
    ObservableList<Kanban>InProgressL=FXCollections.observableArrayList();
    ObservableList<Kanban>DoneL=FXCollections.observableArrayList();
    public void initialize(URL Location, ResourceBundle resourdes)
    {
        initList(ToDoList,1);
        initList(InProgressList,2);
        initList(DoneList,3);
        loadLastSession();
        ToDoList.setItems(ToDoL);
        InProgressList.setItems(InProgressL);
        DoneList.setItems(DoneL);

    }
    @FXML protected void Save (ActionEvent event)
    {
        String filename1 = "ToDo.txt";
        try
        {

            FileOutputStream file = new FileOutputStream(filename1);
            ObjectOutputStream out = new ObjectOutputStream(file);
            ArrayList<Kanban>tmp=new ArrayList<Kanban>();
            for(int i=0;i<ToDoL.size();i++) {
                tmp.add(ToDoList.getItems().get(i));
            }
            out.writeObject(tmp);
            out.close();
            file.close();

        }

        catch(IOException ex)
        {
            ex.printStackTrace();
            System.out.println("IOException is caught");
        }
        String filename2 = "InProgress.txt";
        try
        {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(filename2);
            ObjectOutputStream out = new ObjectOutputStream(file);
            ArrayList<Kanban>tmp=new ArrayList<Kanban>();
            for(int i=0;i<InProgressL.size();i++) {
                tmp.add(InProgressList.getItems().get(i));
            }
            out.writeObject(tmp);
            out.close();
            file.close();

        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }
        String filename3= "Done.txt";
        try
        {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(filename3);
            ObjectOutputStream out = new ObjectOutputStream(file);
            ArrayList<Kanban>tmp=new ArrayList<Kanban>();
            for(int i=0;i<DoneL.size();i++) {
                tmp.add(DoneList.getItems().get(i));
            }
            out.writeObject(tmp);
            out.close();
            file.close();

        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }

    }
    void loadLastSession() {
        String filename1 = "ToDo.txt";
        try {
            FileInputStream fis = new FileInputStream(filename1);
            ObjectInputStream in = new ObjectInputStream(fis);
            ArrayList<Kanban>tmp= (ArrayList<Kanban>)in.readObject();
            for(int i=0;i<tmp.size();i++)
            {
                ToDoL.addAll(tmp.get(i));
            }

            in.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        catch(ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        String filename2 = "InProgress.txt";
        try {
            FileInputStream fis = new FileInputStream(filename2);
            ObjectInputStream in = new ObjectInputStream(fis);
            ArrayList<Kanban>tmp= (ArrayList<Kanban>)in.readObject();
            for(int i=0;i<tmp.size();i++)
            {
                InProgressL.addAll(tmp.get(i));
            }

            in.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        catch(ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        String filename3 = "Done.txt";
        try {
            FileInputStream fis = new FileInputStream(filename3);
            ObjectInputStream in = new ObjectInputStream(fis);
            ArrayList<Kanban>tmp= (ArrayList<Kanban>)in.readObject();
            for(int i=0;i<tmp.size();i++)
            {
                DoneL.addAll(tmp.get(i));
            }

            in.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        catch(ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
    }
    public class KanbanListCell extends ListCell<Kanban> {

        public void updateItem(Kanban item, boolean empty) {
            super.updateItem(item, empty);

            if (item != null) {
                setText(item.toString());
            }
            else{
                setText(null);
                setGraphic(null);
            }
        }
    }
    @FXML protected void closee(ActionEvent event)
    {
        Platform.exit();
    }
    @FXML protected void about(ActionEvent event){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText("Written by Wiciu");
        alert.showAndWait();
    }
    @FXML protected void dod(ActionEvent event) {
        try {
            Kanban a;
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("AddWindow.fxml"));
            Parent root1 = (Parent) fxmlloader.load();
            AddWindow sec= fxmlloader.getController();
            sec.choice.getItems().addAll("High","Low");
            sec.setChoiceValue("Low");
            Stage stage=new Stage();
            stage.setTitle("Add Window");
            stage.setScene(new Scene(root1));
            stage.showAndWait();
            a=sec.function();
            if(sec.OkClicked)
            ToDoList.getItems().add(a);
            ToDoList.refresh();
        }
        catch(Exception e)
        {
            System.out.println("Cant load the new window");
        }
    }
    @FXML protected void exportToCSV(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Resource File");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file=fileChooser.showSaveDialog(new Stage());
        try (FileWriter writer = new FileWriter(file.getPath())) {
            writer.append("TODO");
            for(int i=0;i<ToDoL.size();i++) {
                writer.append('\n');
                writer.append(ToDoList.getItems().get(i).getName());
                writer.append(',');
                writer.append(ToDoList.getItems().get(i).getPriority());
                writer.append(',');
                writer.append(ToDoList.getItems().get(i).getDate());
                writer.append(',');
                writer.append(ToDoList.getItems().get(i).getDescription());
            }
            writer.append('\n');
            writer.append("INPROGRESS");
            for(int i=0;i<InProgressL.size();i++) {
                writer.append('\n');
                writer.append(InProgressList.getItems().get(i).getName());
                writer.append(',');
                writer.append(InProgressList.getItems().get(i).getPriority());
                writer.append(',');
                writer.append(InProgressList.getItems().get(i).getDate());
                writer.append(',');
                writer.append(InProgressList.getItems().get(i).getDescription());
            }
            writer.append('\n');
            writer.append("DONE");
            for(int i=0;i<DoneL.size();i++) {
                writer.append('\n');
                writer.append(DoneList.getItems().get(i).getName());
                writer.append(',');
                writer.append(DoneList.getItems().get(i).getPriority());
                writer.append(',');
                writer.append(DoneList.getItems().get(i).getDate());
                writer.append(',');
                writer.append(DoneList.getItems().get(i).getDescription());
            }
        }
        catch(IOException e)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Cannot open ");
            alert.showAndWait();
        }
    }
    @FXML protected void importFromCSV(ActionEvent event) {
        ToDoList.getItems().clear();
        InProgressList.getItems().clear();
        DoneList.getItems().clear();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Resource File");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(new Stage());
        try (BufferedReader br = Files.newBufferedReader(Paths.get(file.getPath()),
                StandardCharsets.US_ASCII)) {
            String line=br.readLine();
            line=br.readLine();
            while(line!=null)
            {
                if(line.equals("INPROGRESS"))
                    break;
                String attributes[]=line.split(",");
                ToDoList.getItems().add(new Kanban(attributes[0],attributes[2],attributes[3],attributes[1]));
                line=br.readLine();
            }
            line=br.readLine();
            while(line!=null)
            {
                if(line.equals("DONE"))
                    break;
                String attributes[]=line.split(",");
                InProgressList.getItems().add(new Kanban(attributes[0],attributes[2],attributes[3],attributes[1]));
                line=br.readLine();
            }
            line=br.readLine();
            while(line!=null)
            {
                String attributes[]=line.split(",");
                DoneList.getItems().add(new Kanban(attributes[0],attributes[2],attributes[3],attributes[1]));
                line=br.readLine();
            }

        }
        catch(IOException ex)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Cannot open ");
            alert.showAndWait();
        }
    }
    private void initList(ListView<Kanban>kanban,int i)
    {
        kanban.setCellFactory((Callback<ListView<Kanban>,ListCell<Kanban>>)list ->{
            ListCell<Kanban> cell = new KanbanListCell();
            ContextMenu menu=new ContextMenu();
            cell.listViewProperty().getName();

            MenuItem deleteItem = new MenuItem();
            deleteItem.textProperty().bind(Bindings.format("Delete", cell.itemProperty()));
            deleteItem.setOnAction(event -> kanban.getItems().remove(cell.getItem()));

            MenuItem editItem = new MenuItem();
            editItem.textProperty().bind(Bindings.format("Edit", cell.itemProperty()));
            editItem.setOnAction(event -> {
                        Kanban taskToEdit = cell.getItem();
                        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("AddWindow.fxml"));
                        Parent root1 = null;
                        try {
                            root1 = (Parent) fxmlloader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        AddWindow sec = fxmlloader.getController();
                        sec.choice.getItems().addAll("High","Low");
                        sec.setDateText(taskToEdit.date);
                        sec.setDescText(taskToEdit.description);
                        sec.setTitleText(taskToEdit.name);
                        sec.setChoiceValue(taskToEdit.priority);
                        sec.addButton.setText("Edit");
                        Stage stage = new Stage();
                        stage.setTitle("Edit Window");
                        stage.setScene(new Scene(root1));
                        stage.showAndWait();
                        if (sec.OkClicked)
                        {
                            taskToEdit = sec.function();
                            kanban.getItems().remove(cell.getItem());
                            kanban.getItems().add(taskToEdit);
                    }
            });

            MenuItem moveToInProgress = new MenuItem();
            moveToInProgress.textProperty().bind(Bindings.format("Move to In Progress"));
            moveToInProgress.setOnAction(event -> {
                Kanban movedItem = cell.getItem();
                kanban.getItems().remove(cell.getItem());
                InProgressList.getItems().add(movedItem);
                kanban.refresh();
                InProgressList.refresh();
            });

            MenuItem moveToDone = new MenuItem();
            moveToDone.textProperty().bind(Bindings.format("Move to Done"));
            moveToDone.setOnAction(event -> {
                Kanban movedItem = cell.getItem();
               kanban.getItems().remove(cell.getItem());
               DoneList.getItems().add(movedItem);
                kanban.refresh();
                DoneList.refresh();
            });

            MenuItem moveToToDo = new MenuItem();
            moveToToDo.textProperty().bind(Bindings.format("Move to To Do"));
            moveToToDo.setOnAction(event -> {
                Kanban movedItem = cell.getItem();
                kanban.getItems().remove(cell.getItem());
                ToDoList.getItems().add(movedItem);
                kanban.refresh();
                ToDoList.refresh();
            });


            menu.getItems().addAll(deleteItem,editItem);
            if(i==1)
            {
                menu.getItems().addAll(deleteItem,editItem,moveToInProgress,moveToDone);
            }
            else if(i==2)
            {
                menu.getItems().addAll(deleteItem,editItem,moveToDone,moveToToDo);
            }
            else
            {
                menu.getItems().addAll(deleteItem,editItem,moveToToDo,moveToInProgress);
            }
            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(menu);
                }
            });

            cell.setOnMouseEntered(event -> {

                    if(!cell.isEmpty()) {
                        final Tooltip tooltip = new Tooltip();
                        tooltip.setText(kanban.getItems().get(cell.getIndex()).getDescription());
                        cell.setTooltip(tooltip);
                    }
            });

            return cell;

        });
    }

}
