package mryazik.github.io.Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import mryazik.github.io.util.*;

public class FilesWork {
    static Logger logger = Logger.getLogger(FilesWork.class.getName());
    static Path pathToProject;

    public static Path createSystemDir() // Может возвращать путь до системной папки
    {
        try {
            File folder = new File(getSystemAppPath.get().toUri());
            pathToProject = Paths.get(folder.getPath());

            if (!folder.exists()) { // Если папки нет
                Files.createDirectory(pathToProject);
                return pathToProject;
            } else {
                logger.log(Level.INFO, "Файл программы уже существует");
                return pathToProject;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Не удалось создать системную папку", e);
            return null;
        }
    }

    public static Path createProject(String name_project) // Может возвращать путь до проекта, если проект существует
    {
        createSystemDir(); // если уже есть, то не создаёт

        Path pathToProjectDir = Paths.get(pathToProject.toString() + "/" + name_project);
        try {
            if (Files.notExists(pathToProjectDir)) // если файла не существует
            {
                Files.createDirectory(pathToProjectDir);
                // Создаём README по умолчанию
                changeReadme("""
                        Сюда вы можете добавить информацию о проекте
                        """, pathToProjectDir);
                logger.log(Level.INFO, "Путь до нового проекта: " + pathToProjectDir);
                return pathToProjectDir;
            } else {
                // добавить окно - такой проект уже существует
                logger.log(Level.INFO, "Путь до нового проекта: " + pathToProjectDir);
                return pathToProjectDir;
            }
        } catch (Exception e)
        {
            logger.log(Level.SEVERE, "не удалось создать папку проекта", e);
            return null;
        }
    }

    public static void changeReadme(String readme, Path pathToProject) // Может создавать Readme, если его нет
    {
        try {
            Path readmePath = Paths.get(pathToProject.toString() + "/README.md");
            java.io.BufferedWriter bw = new BufferedWriter(new FileWriter(readmePath.toFile()));
            logger.log(Level.INFO, "BufferWriter: " + bw);
            logger.log(Level.INFO, "readmePath: " + readmePath);

            if (!Files.exists(readmePath)) {
                Files.createFile(readmePath);
                bw.write(readme);
                bw.flush();
                bw.close();
            } else {
                bw.write(readme);
                bw.flush();
                bw.close();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка работы с README.md проекта", e);
        }
    }


    public static String getReadme(String name_project)
    {
        try {
            File file = new File(createSystemDir().toString() + '/' + name_project + "/README.md");

            if (file.exists())
            {
                return Files.readString(file.toPath());
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Не удалось получить README проекта" + name_project);
        }

        return null;
    }

    /*
    1. Добавить динамические добавление проектов в левое меню

    Добавить функции:
        1. Получение списка проектов в виде массива имён -
        2. Удаление проектов
        3. Получение README.md -
        4. Получение файлов идей, в виде Map: (name_file, содержание: (основной текст, список задач)

    2. Добавить под низ textField текст, если произошла ошибка (например, файл уже существует)
     */

    public static ArrayList<String> getListNameProjects()
    {
        File fileProject = createSystemDir().toFile(); // Получаем путь до папки где все проекты
        File[] files = fileProject.getAbsoluteFile().listFiles();

        try {
            String listProjects = ""; // Для лога

            // Создаём новое хранилище названий проектов
            ArrayList<String> names = new ArrayList<String>();

            int i = 1;

            for (File file : files)
            {

                if (file.getName().contains("."))
                {
                    listProjects += i + ". " + file.getName() + " (Системный файл)" + '\n';
                } else
                {
                    listProjects += i + ". " + file.getName() +  '\n';
                    names.add(file.getName());
                }

                i++;
            }

            logger.log(Level.INFO, "Список проектов:\n" + listProjects);
            return names;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Map<String, Object>> getProjectIdeas(String name_project)
    {
        File project_ideas = new File(createProject(name_project).toString() + "/ideas");
        ArrayList<Map<String, Object>> listFiles = new ArrayList<>();

        if (project_ideas.exists())
        {
            File[] list_files_in_dir = project_ideas.listFiles();

            for (File file : list_files_in_dir)
            {
                try {
                    Map<String, Object> map = new HashMap<>();
                    map.put(file.getName(), Files.readString(Paths.get(file.getPath())));

                    listFiles.add(map);
                } catch (Exception e)
                {
                    logger.log(Level.SEVERE, "Не удалось получить содержимое файла: " + file.getName(), e);
                }
            }
        } else {
            try {
                Files.createDirectory(Paths.get(project_ideas.getPath()));
                getProjectIdeas(name_project);
            } catch (Exception e)
            {
                logger.log(Level.SEVERE, "Не удалось получить папку: " + project_ideas,e);
            }
        }

        return listFiles;
    }

    // Создание идеи. Получаем путь до заметки
    public static Path createIdea(String name_project, String name_note, String note_string)
    {
        // Идеи будем хранить в формате .task, всё равно потом будем их расшифровывать

        File file = new File(createProject(name_project).toString() + "/ideas");
        Path pathToNote = Paths.get(file.toPath().toString() + "/" + name_note + ".task");

        try {
            if (file.exists()) {
                Files.createFile(pathToNote);
                Files.writeString(pathToNote, note_string);
                return pathToNote;
            } else {
                Files.createDirectory(file.toPath());
                createIdea(name_project, name_note, note_string); // Повторно вызываем с созданной папкой
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Не удалось создать заметку", e);
            return null;
        }

        return null;
    }

    public static void main(String args[])
    {
//        createSystemDir();
//
//        Path project = createProject("penis228");
//        changeReadme("""
//                Измените этот README, добавьте информации о проекта
//                test228""", project);

        getListNameProjects();
    }
}
