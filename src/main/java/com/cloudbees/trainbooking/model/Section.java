package com.cloudbees.trainbooking.model;

import lombok.Getter;

@Getter
public enum Section {

    A("Section A"),
    B("Section B");

    private final String displayName;

    Section(String displayName) {
        this.displayName = displayName;
    }

}