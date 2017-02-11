package com.example.util.mvp.base;

/**
 * Common interface for all the mappers mapping the rest service responses to our view model.
 */
public interface Mapper<FROM, TO> {

    TO map(FROM from);

}
