package com.zephyr.scraper.browser.converters;

import com.zephyr.scraper.domain.external.Proxy;
import org.asynchttpclient.Realm;
import org.asynchttpclient.proxy.ProxyServer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProxyConverter implements Converter<Proxy, ProxyServer> {

    @Nullable
    @Override
    public ProxyServer convert(@Nullable Proxy source) {
        if (Objects.isNull(source)) {
            return null;
        }

        return new ProxyServer.Builder(source.getIp(), source.getPort())
                .setRealm(realm(source))
                .build();
    }

    private Realm realm(Proxy source) {
        if (Objects.isNull(source.getUsername()) || Objects.isNull(source.getPassword())) {
            return null;
        }

        return new Realm.Builder(source.getUsername(), source.getPassword())
                .setScheme(Realm.AuthScheme.BASIC)
                .build();
    }
}