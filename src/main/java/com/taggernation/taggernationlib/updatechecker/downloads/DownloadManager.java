/*
 *     TaggerNationLib - Common utility library for our products.
 *     Copyright (C) 2022  TaggerNation
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
