package mryazik.github.io.workData;

import java.util.ArrayList;
import java.util.List;

public class ControlProjects {

    public static void main(String[] args)
    {
        jsonData newObject = new jsonData();

        // Для теста создаём новый проект
        Projects projects = new Projects(newObject.getCountProjects(), "Работа", "Тут я работаю"); // Новый проект

        // Создаём идею
        Ideas ideas1 = new Ideas(newObject.getCountIdeas(), "Договор 1", "Это заметка к договору", 1);
        Ideas ideas2 = new Ideas(newObject.getCountIdeas(), "Договор 2", "Это заметка к договору", 2);

        List<Ideas> listIdeasForProject = new ArrayList<>();
        listIdeasForProject.add(ideas1);
        listIdeasForProject.add(ideas2);

        newObject.addIdeas(listIdeasForProject);

        // Получаем id идей и записываем их, как относящиеся к проекту
        List<Integer> listIdIdeas = new ArrayList<>();

        newObject.getIdeas().forEach((idea) -> {
            listIdIdeas.add(idea.getId());
        });

        projects.addIdeas(listIdIdeas); // Добавляем эти идеи как принадлежащие

        List<Projects> projectsList = new ArrayList<>();
        projectsList.add(projects);

        newObject.addProjects(projectsList);

        // Выводим всё о проекте и их под штуках
        jsonData.outJsonToString(newObject);
    }
}
