package com.zephyr.scraper.browser.converters;

import com.zephyr.scraper.domain.external.Proxy;
import org.asynchttpclient.Realm;
import org.asynchttpclient.proxy.ProxyServer;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProxyConverterTest {
    private static final Realm.AuthScheme SCHEME = Realm.AuthScheme.BASIC;
    private static final String PASSWORD = "PASSWORD";
    private static final String USERNAME = "USERNAME";
    private static final String HOST = "HOST";
    private static final int PORT = 1488;

    private ProxyConverter testInstance = new ProxyConverter();

    @Test
    public void shouldConvert() {
        ProxyServer proxyServer = testInstance.convert(proxy());

        assertNotNull(proxyServer);
        assertEquals(SCHEME, proxyServer.getRealm().getScheme());
        assertEquals(PASSWORD, proxyServer.getRealm().getPassword());
        assertEquals(USERNAME, proxyServer.getRealm().getPrincipal());
        assertEquals(HOST, proxyServer.getHost());
        assertEquals(PORT, proxyServer.getPort());
    }

    @Test
    public void shouldConvertToNull() {
        assertNull(testInstance.convert(null));
    }

    private Proxy proxy() {
        Proxy proxy = new Proxy();
        proxy.setUsername(USERNAME);
        proxy.setPassword(PASSWORD);
        proxy.setIp(HOST);
        proxy.setPort(PORT);

        return proxy;
    }
}