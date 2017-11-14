package com.zephyr.scraper.saver;

import com.zephyr.scraper.domain.EngineResponse;

public interface ResponseSaver {

    void save(EngineResponse response);
}