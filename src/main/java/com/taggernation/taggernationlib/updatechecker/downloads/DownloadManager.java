package com.taggernation.taggernationlib.updatechecker.downloads;

import com.taggernation.taggernationlib.updatechecker.UpdateChecker;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class DownloadManager {

    private final URL url;
    private boolean overwrite;

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

    public DownloadManager(UpdateChecker instance, URL url) {
        this.url = url;
    }
    public void download() throws IOException {
        File file = new File("rc24-java-logo.gif");
        Bukkit.getLogger().info("Downloading file...");
        if (checkFileExist(file) && !overwrite) {
            Bukkit.getLogger().info("File already exists, skipping download.");
            return;
        }

        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileChannel fileChannel = fileOutputStream.getChannel();
        fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        fileChannel.close();
        fileOutputStream.close();
        readableByteChannel.close();
        Bukkit.getLogger().info("Downloaded file: " + file.getPath() + "/" + file.getName());

    }
    public boolean checkFileExist(File file) {
        return file.exists();
    }
    public boolean Delete (File file) {
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

}
