package config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Config<T> {
    public static Scanner sc;

    public static Scanner scanner() {
        if (sc == null) {
            sc = new Scanner(System.in);
            return sc;
        }
        return sc;
    }

    public static int getValidInteger() {
        int integer;
        while (true) {
            System.out.println("Enter choice / 0 to back");
            String s = sc.nextLine();

            if (Integer.parseInt(s) == 0) return 0;

            if (s.matches("\\d+")) {
                integer = Integer.parseInt(s);
                break;
            } else {
                System.out.println("Invalid number, try again!");
            }
        }
        return integer;
    }

    public T read(String path) {
        try (
                FileInputStream fis = new FileInputStream(path);
                ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            return (T) ois.readObject();
        } catch (Exception e) {
            System.out.println("Error reading");
            return null;
        }
    }

    public void write(String path, T data) {
        try (
                FileOutputStream fos = new FileOutputStream(path);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(data);
        } catch (Exception e) {
            System.out.println("Error writing");
        }
    }

}
