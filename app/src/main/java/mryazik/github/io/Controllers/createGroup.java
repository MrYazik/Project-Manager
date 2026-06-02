package mryazik.github.io.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import mryazik.github.io.Classes.layoutLoad;
import mryazik.github.io.Classes.modalWindow;
import mryazik.github.io.workData.Groups;
import mryazik.github.io.workData.jsonData;
import mryazik.github.io.workData.workJsonFile;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

public class createGroup {
    modalWindow.StageAndLoader loaderAndStage;
    String name_project;

    @FXML
    Button done;
    @FXML
    Button back;
    @FXML
    TextField input_name_group;

    public void initialize()
    {
        done.setOnAction(event -> {
            jsonData jsonObject = workJsonFile.getJsonInfo();
            Groups newGroup = new Groups(inProject.current_idea_id, input_name_group.getText(), 0);
            List<Groups> listGroups = new ArrayList<>();
            listGroups.add(newGroup);
            jsonObject.addGroups(listGroups);

            workJsonFile.changeJson(jsonObject);

            // Перезагружаем меню в проекте
            FXMLLoader loader = layoutLoad.loadVBoxInCenter("in-project.fxml");
            inProject controller = loader.getController();
            controller.init(name_project);

            loaderAndStage.stage().close();
        });
        back.setOnAction(event -> {
            loaderAndStage.stage().close();
        });
    }

    public void init(modalWindow.StageAndLoader loaderAndStage, String name_project)
    {
        this.name_project = name_project;
        this.loaderAndStage = loaderAndStage;
    }
}
