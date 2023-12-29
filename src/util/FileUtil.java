package util;

import model.Customer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static <T> void saveToFile(String fileName, List<T> list) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static <T> List<T> loadFromFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                return (List<T>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }


}
