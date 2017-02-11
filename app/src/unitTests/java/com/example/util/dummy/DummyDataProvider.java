package com.example.util.dummy;

public class DummyDataProvider {

    private final DummyShotsDataProvider dummyShotsDataProvider = new DummyShotsDataProvider();

    public DummyShotsDataProvider shots() {

        return dummyShotsDataProvider;
    }

}
