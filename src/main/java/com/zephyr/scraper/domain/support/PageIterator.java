package com.zephyr.scraper.domain.support;

import com.zephyr.scraper.domain.Page;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;

@RequiredArgsConstructor(staticName = "of")
public class PageIterator implements Iterator<Page> {

    @NonNull
    private Page page;
    private boolean isFirst = true;

    @Override
    public boolean hasNext() {
        return page.isNotLast() || isFirst;
    }

    @Override
    public Page next() {
        if (isFirst) {
            isFirst = false;
        } else {
            page = page.withPage(page.getPage() + 1);
        }

        return page;
    }

    public Spliterator<Page> asSplitIterator() {
        return Spliterators.spliteratorUnknownSize(this, Spliterator.ORDERED);
    }
}