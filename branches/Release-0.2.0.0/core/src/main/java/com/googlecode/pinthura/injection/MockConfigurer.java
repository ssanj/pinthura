package com.googlecode.pinthura.injection;

/**
 * Defines parameters to configure which fields are mocked out.
 */
public interface MockConfigurer {

    /**
     * Returns the mock prefix.
     * An example would be "mock".
     *
     * @return The mock prefix
     */
    String getMockPrefix();

    /**
     * Returns the mock control name.
     * An example would be "mockControl".
     * 
     * @return The mock control name.
     */
    String getMockControlName();
}
