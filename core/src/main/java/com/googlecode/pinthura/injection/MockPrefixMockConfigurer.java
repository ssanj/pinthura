package com.googlecode.pinthura.injection;

/**
 * Default <code>MockConfigurer</code>.
 */
public final class MockPrefixMockConfigurer implements MockConfigurer {

    public String getMockPrefix() {
        return "mock";
    }

    public String getMockControlName() {
        return "mockControl";
    }
}
