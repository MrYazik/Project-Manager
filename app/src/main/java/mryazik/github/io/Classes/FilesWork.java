package mryazik.github.io.Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import mryazik.github.io.util.*;

public class FilesWork {
    static Logger logger = Logger.getLogger(FilesWork.class.getName());
    static Path pathToProject;

    public static void createSystemDir()
    {
        try {
            File folder = new File(getSystemAppPath.get().toUri());
            pathToProject = Paths.get(folder.getPath());

            if (!folder.exists()) { // Если папки нет
                Files.createDirectory(pathToProject);
            } else {
                logger.log(Level.INFO, "Файл программы уже существует");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Не удалось создать системную папку", e);
        }
    }

    public static Path createProject(String name_project)
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

    public static void changeReadme(String readme, Path pathToProject)
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

    /*
    1. Добавить динамические добавление проектов в левое меню

    Добавить функции:
        1. Получение списка проектов в виде массива имён
        2. Удаление проектов
        3. Получение README.md
        4. Получение файлов идей, в виде Map: (name_file, содержание: (основной текст, список задач)

    2. Добавить под низ textField текст, если произошла ошибка (например, файл уже существует)
     */

    public static void main(String args[])
    {
        createSystemDir();

        Path project = createProject("penis228");
        changeReadme("""
                Измените этот README, добавьте информации о проекта
                test228""", project);
    }
}
