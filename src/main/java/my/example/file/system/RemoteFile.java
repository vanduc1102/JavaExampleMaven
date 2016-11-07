/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.example.file.system;

import java.io.File;

/**
 *
 * @author nvduc
 */
public class RemoteFile {

    public static void main(String[] args) {
        String localFolder = "C:\\rootExternalResources\\";
        String remoteFolder = "\\\\vntiws01\\iKool";
        String remoteFolderTemp = remoteFolder.replace("//",String.valueOf(File.separatorChar));
        
        System.out.println("File.pathSeparator : "+String.valueOf(File.separatorChar));
        System.out.println("remoteFolder : "+remoteFolder);
        System.out.println("remoteFolderTemp : "+remoteFolder.replace("\\", "/"));
        
        System.out.println("localFolder : "+isRemoteFolder(localFolder));
        System.out.println("remoteFolder : "+isRemoteFolder(remoteFolder));
        boolean aaa = Boolean.parseBoolean("true");
        System.out.println("my.example.file.system.RemoteFile.main() : "+aaa);

    }

    public static boolean isRemoteFolder(final String folderPath) {
        return folderPath.matches("\\w+?://");
    }
}
