package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class GeneratorOfGroups {
    void writeRows(BufferedWriter bw) throws IOException {
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
    }

    void writeColumns(BufferedWriter bw) throws IOException {
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
    }

    void writeBoxes(BufferedWriter bw) throws IOException {
        for (int x = 3; x <= 9; x += 3) {
            for (int y = 3; y <= 9; y += 3) {
                writeBox(bw, x, y);
            }
        }
    }

    void writeBox(BufferedWriter bw, int xR, int yT) throws IOException {
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
    }

    void writeGroups(BufferedWriter bw) throws IOException {
        bw.write("final static int[][] groups = {");
        bw.newLine();
        writeRows(bw);
        writeColumns(bw);
        writeBoxes(bw);
        bw.write("};");
        bw.newLine();
    }

    void writeRowsAttr(BufferedWriter bw) throws IOException {
        bw.write("final static int[][] rows = {");
        bw.newLine();
        writeRows(bw);
        bw.write("};");
        bw.newLine();
    }

    void writeColumnsAttr(BufferedWriter bw) throws IOException {
        bw.write("final static int[][] columns = {");
        bw.newLine();
        writeColumns(bw);
        bw.write("};");
        bw.newLine();
    }

    void writeBoxesAttr(BufferedWriter bw) throws IOException {
        bw.write("final static int[][] boxes = {");
        bw.newLine();
        writeBoxes(bw);
        bw.write("};");
        bw.newLine();
    }

    void main() {
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

            // this space is within the class Groups
            writeGroups(bw);
            writeRowsAttr(bw);
            writeColumnsAttr(bw);
            writeBoxesAttr(bw);

            bw.write("}");
        } catch (IOException e) {
            System.out.println("Error writing start.");
        }
    }
}
