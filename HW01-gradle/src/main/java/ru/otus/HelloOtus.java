package ru.otus;

import com.google.common.base.Objects;

public class HelloOtus  {
    public boolean objectEqual (String firstString, String secondString) { // применил метод objectEqual из библиотеки guava
        return Objects.equal(firstString, secondString);

    }
}