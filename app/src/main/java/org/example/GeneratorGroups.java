package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class GeneratorGroups {
    void clear() {
        try (BufferedWriter bw =
                new BufferedWriter(
                        new FileWriter(
                                System.getProperty("user.dir")
                                        + "/src/main/java/org/example/Groups.java"))) {
        } catch (IOException e) {
            System.out.println("Error clearing.");
        }
    }

    void start() {
        try (BufferedWriter bw =
                new BufferedWriter(
                        new FileWriter(
                                System.getProperty("user.dir")
                                        + "/src/main/java/org/example/Groups.java",
                                true))) {
            bw.write("package org.example;");
            bw.newLine();
            bw.write("class Groups {");
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error writing start.");
        }
    }

    void startGroups() {
        try (BufferedWriter bw =
                new BufferedWriter(
                        new FileWriter(
                                System.getProperty("user.dir")
                                        + "/src/main/java/org/example/Groups.java",
                                true))) {
            bw.write("int[][] groups = {");
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing start.");
        }
    }

    void writeRows() {
        try (BufferedWriter bw =
                new BufferedWriter(
                        new FileWriter(
                                System.getProperty("user.dir")
                                        + "/src/main/java/org/example/Groups.java",
                                true))) {
            for (int x = 0; x < 9; x++) {
                bw.write("{");

                for (int y = 0; y < 9; y++) {
                    if (y == 8) {
                        bw.write((x * 9 + y) + "");
                    } else {
                        bw.write((x * 9 + y) + ",");
                    }
                }

                bw.write("},");
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing Rows.");
        }
    }

    void writeColumns() {
        try (BufferedWriter bw =
                new BufferedWriter(
                        new FileWriter(
                                System.getProperty("user.dir")
                                        + "/src/main/java/org/example/Groups.java",
                                true))) {
            for (int x = 0; x < 9; x++) {
                bw.write("{");

                for (int y = 0; y < 9; y++) {
                    if (y == 8) {
                        bw.write((y * 9 + x) + "");
                    } else {
                        bw.write((y * 9 + x) + ",");
                    }
                }

                bw.write("},");
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing Columns.");
        }
    }

    void writeBoxes() {
        for (int x = 3; x <= 9; x += 3) {
            for (int y = 3; y <= 9; y += 3) {
                writeBox(x, y);
            }
        }
    }

    void writeBox(int xR, int yT) {
        try (BufferedWriter bw =
                new BufferedWriter(
                        new FileWriter(
                                System.getProperty("user.dir")
                                        + "/src/main/java/org/example/Groups.java",
                                true))) {
            bw.write("{");

            for (int x = xR - 3; x < xR; x++) {
                for (int y = yT - 3; y < yT; y++) {
                    if (x == xR - 1 && y == yT - 1) {
                        bw.write((x * 9 + y) + "");
                    } else {
                        bw.write((x * 9 + y) + ",");
                    }
                }
            }
            bw.write("},");
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error writing Boxes.");
        }
    }

    void endGroups() {
        try (BufferedWriter bw =
                new BufferedWriter(
                        new FileWriter(
                                System.getProperty("user.dir")
                                        + "/src/main/java/org/example/Groups.java",
                                true))) {
            // closing int[][] groups
            bw.write("};");
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing Boxes.");
        }
    }

    void writeGroups() {
        startGroups();
        writeRows();
        writeColumns();
        writeBoxes();
        endGroups();
    }

    void end() {
        try (BufferedWriter bw =
                new BufferedWriter(
                        new FileWriter(
                                System.getProperty("user.dir")
                                        + "/src/main/java/org/example/Groups.java",
                                true))) {
            // closing class Groups
            bw.write("}");

        } catch (IOException e) {
            System.out.println("Error writing end.");
        }
    }

    void main() {
        clear();
        start();
        writeGroups();
        end();
    }
}
