package com.googlecode.pinthura.injection;

public final class MockPrefixMockConfigurer implements MockConfigurer {

    @Override
    public String getMockPrefix() {
        return "mock";
    }

    @Override
    public String getMockControlName() {
        return "mockControl";
    }
}
