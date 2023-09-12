//������� 3. ������ 2.

import java.io.*;
import java.util.ArrayList;
import java.util.zip.*;

public class Main {

    public static void main(String[] args) {

        String directory = "Games/savefiles/";

        ArrayList<GameProgress> savefiles = new ArrayList<>();
        ArrayList<String> saveData = new ArrayList<>();

        //1. ������� ��� ���������� ������
        savefiles.add(new GameProgress(94, 10, 2, 254.32));
        savefiles.add(new GameProgress(1, 0, 0, 0));
        savefiles.add(new GameProgress(100, 1, 100, 100));

        //2. ��������� ��������������� �������
        for (int i = 0; i < savefiles.size(); i++) {
            saveData.add(directory + "save" + (i + 1) + ".dat");
            saveGame(savefiles.get(i), saveData.get(i));
        }

        //3. ��������� ����� ���������� �� ����� savegames ���������� � �����
        zipFiles(directory + "zip.zip", saveData);


        //4. ������� ����� ����������, ������� ��� ������.
        deleteFilesWithExtension(new File(directory), "dat");
    }

    public static void saveGame(GameProgress progress, String saveDirectory) {
        try (FileOutputStream fos = new FileOutputStream(saveDirectory);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // ������� ��������� ������ � ����
            oos.writeObject(progress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zipFileDirectory, ArrayList<String> fileName) {
        for (int i = 0; i < fileName.size(); i++) {
            try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFileDirectory, true));
                 FileInputStream fis = new FileInputStream(fileName.get(i))) {
                ZipEntry entry = new ZipEntry("save" + (i + 1) + ".dat");
                zout.putNextEntry(entry);
                // ��������� ���������� ����� � ������ byte
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                // ��������� ���������� � ������
                zout.write(buffer);
                // ��������� ������� ������ ��� ����� ������
                zout.closeEntry();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static void deleteFilesWithExtension(File directory, String extension) {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                deleteFilesWithExtension(file, extension);
            } else if (file.getName().endsWith(extension)) {
                file.delete();
            }
        }
    }
}