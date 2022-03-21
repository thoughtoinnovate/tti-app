package org.thoughtoinnovate.spring.ttiapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.InputStream;

@Getter
@AllArgsConstructor
public class MyFile {
    private String title;
    private InputStream stream;
}
