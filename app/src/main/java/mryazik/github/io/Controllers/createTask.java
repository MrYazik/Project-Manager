package mryazik.github.io.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import mryazik.github.io.App;
import mryazik.github.io.Classes.layoutLoad;
import mryazik.github.io.Classes.modalWindow;
import mryazik.github.io.workData.Tasks;
import mryazik.github.io.workData.jsonData;
import mryazik.github.io.workData.workJsonFile;

import java.util.ArrayList;
import java.util.List;

public class createTask {
    String name_project;

    @FXML
    Button done;
    @FXML
    Button back;
    @FXML
    TextField input_task_name;


    int group_id;
    modalWindow.StageAndLoader loader;

    public void initialize()
    {
        done.setOnAction(event -> {
            jsonData jsonObject = workJsonFile.getJsonInfo();

            int taskId = jsonObject.getCountTasks();

            Tasks newTask = new Tasks(group_id, taskId, input_task_name.getText(), "", 0);
            List<Tasks> listTasks = new ArrayList<>();
            listTasks.add(newTask);

            jsonObject.addTask(listTasks);
            workJsonFile.changeJson(jsonObject);

            // Перезагружаем меню
            FXMLLoader inProjectLoader = layoutLoad.loadVBoxInCenter("in-project.fxml");
            inProject controller = inProjectLoader.getController();
            controller.init(name_project);

            loader.stage().close();
        });

        back.setOnAction(event -> {
            loader.stage().close();
        });
    }

    public void init(modalWindow.StageAndLoader loader, int group_id, String name_project)
    {
        this.name_project = name_project;
        this.loader = loader;
        this.group_id = group_id;
    }
}
