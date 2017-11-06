package com.zephyr.scraper.browser.composer.impl;

import com.zephyr.scraper.browser.composer.HeadersManagerComposer;
import com.zephyr.scraper.browser.composer.managers.HeadersManager;
import com.zephyr.scraper.domain.RequestContext;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class HeadersManagerComposerImpl implements HeadersManagerComposer {

    @Setter(onMethod = @__(@Autowired))
    private List<HeadersManager> managers;

    @Override
    public void compose(Map<String, List<String>> headers, RequestContext context) {
        managers.stream()
                .map(m -> m.manage(context))
                .forEach(headers::putAll);
    }
}