package org.geekbang.projects.cs.search.lucene.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {

    private Integer id;
    private String title;
    private String summary;
}