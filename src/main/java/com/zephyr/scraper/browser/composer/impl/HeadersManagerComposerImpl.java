package com.zephyr.scraper.browser.composer.impl;

import com.zephyr.scraper.browser.composer.HeadersManagerComposer;
import com.zephyr.scraper.browser.composer.managers.HeadersManager;
import com.zephyr.scraper.domain.RequestContext;
import com.zephyr.scraper.utils.MapUtils;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HeadersManagerComposerImpl implements HeadersManagerComposer {

    @Setter(onMethod = @__(@Autowired))
    private List<HeadersManager> managers;

    @Override
    public Map<String, List<String>> compose(RequestContext context) {
        return managers.stream()
                .map(m -> m.manage(context))
                .reduce(new HashMap<>(), MapUtils::merge);
    }
}