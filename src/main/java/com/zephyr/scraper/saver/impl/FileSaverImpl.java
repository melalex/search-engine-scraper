package com.zephyr.scraper.saver.impl;

import com.zephyr.scraper.saver.FileSaver;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Slf4j
@Component
public class FileSaverImpl implements FileSaver {

    @Override
    public void save(Path path, byte[] content) {
        try {
            AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
            channel.write(ByteBuffer.wrap(content), 0, null, OnSaveHandler.of(channel, path));
        } catch (IOException e) {
            log.error("Error while saving file", e);
        }
    }


    @AllArgsConstructor(access = AccessLevel.PACKAGE, staticName = "of")
    private static class OnSaveHandler implements CompletionHandler<Integer, Object> {
        private AsynchronousFileChannel channel;
        private Path path;

        @Override
        public void completed(Integer result, Object attachment) {
            log.info("File saved to {}", path.toAbsolutePath());
            close();
        }

        @Override
        public void failed(Throwable exc, Object attachment) {
            log.error("Error while saving file", exc);
            close();
        }

        private void close() {
            try {
                channel.close();
            } catch (IOException e) {
                log.error("Error while closing channel", e);
            }
        }
    }
}
