package com.zephyr.scraper.saver;

import java.nio.file.Path;

public interface FileSaver {

    void save(Path path, byte[] content);
}
