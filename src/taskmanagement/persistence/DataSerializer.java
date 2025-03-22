package taskmanagement.persistence;

import taskmanagement.businesslogic.TasksManagement;

import java.io.*;

public class DataSerializer {
    public static void serializeData(TasksManagement tm, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(tm);
        } catch (IOException e) {
            System.err.println("Serialization error: " + e.getMessage());
        }
    }

    public static TasksManagement deserializeData(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (TasksManagement) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new TasksManagement();
        }
    }
}