package com.zephyr.scraper.domain.external;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Proxy {
    private String id;
    private String username;
    private String password;
    private String ip;
    private int port;
    private LocalDateTime schedule;
}