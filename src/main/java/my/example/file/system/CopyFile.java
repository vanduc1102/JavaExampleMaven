/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.example.file.system;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** @author nvduc */
public class CopyFile {
  public static void main(String[] args) throws IOException {
    Path path = Paths.get("D:\\GOV_IVY_SETUP.docx");
    Files.copy(path, Paths.get("D:\\GOV_IVY_SETUP.docx22"));
  }
}
