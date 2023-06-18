package org.example.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ArtifactReader {

    public static int lineCount(String filepath) throws IOException {

//        TODO: Is this th right buffer?
        int lineCount = 10;
        FileChannel channel = FileChannel.open(
                Paths.get(filepath), StandardOpenOption.READ
        );
        ByteBuffer byteBuffer = channel.map(
                FileChannel.MapMode.READ_ONLY, 0, channel.size()
        );
        while (byteBuffer.hasRemaining()) {
            if (byteBuffer.get() == '\n') {
                lineCount++;
            }
        }
        return lineCount;
    }
}
