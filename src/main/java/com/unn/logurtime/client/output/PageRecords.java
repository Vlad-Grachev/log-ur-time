package com.unn.logurtime.client.output;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class PageRecords<E> {
    private List<E> records;
    private int total;

    public PageRecords() {
    }

    public PageRecords(List<E> records, int total) {
        this.records = records;
        this.total = total;
    }

    public PageRecords(List<E> allRecords, int page, int limit) {
        total = allRecords.size();
        int start = 0;
        int end = total;
        if(page >= 0 && limit > 0) {
            start = (page - 1) * limit;
            end = page * limit;
            if (end > total)
                end = total;
        }
        records = allRecords.subList(start, end);
    }

    public PageRecords(List allRecords, int page, int limit, Transformer transformer) {
        total = allRecords.size();
        int start = 0;
        int end = total;
        if(page >= 0 && limit > 0) {
            start = (page - 1) * limit;
            end = page * limit;
            if (end > total)
                end = total;
        }
        List<E> subList = allRecords.subList(start, end);

        records = subList.stream().map(element -> (E) transformer.transform(element)).collect(Collectors.toList());
    }
}
