package com.unn.logurtime.client.output;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class TransformedPageRecords<E, T> {
    private List<T> records;
    private int total;

    public TransformedPageRecords(List<E> allRecords, int page, int limit, Transformer<E,T> transformer) {
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

        records = subList.stream().map(transformer::transform).collect(Collectors.toList());
    }
}
