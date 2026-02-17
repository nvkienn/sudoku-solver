package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class Generator {
    void createGroupsClass() {
        try {
            File file =
                    new File(
                            System.getProperty("user.dir")
                                    + "/src/main/java/org/example/Groups.java");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    void writeGroups() {
        try (BufferedWriter bw =
                new BufferedWriter(
                        new FileWriter(
                                System.getProperty("user.dir")
                                        + "/src/main/java/org/example/Groups.java"))) {
            bw.write("package org.example;");
            bw.newLine();
            bw.write("class Groups {");
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error writing file.");
        }
    }
}
