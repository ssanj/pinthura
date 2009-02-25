package com.googlecode.pinthura.injection;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AMockPrefixMockConfigurerUnderTest {

    private MockConfigurer mockConfigurer;

    @Before
    public void setup() {
        mockConfigurer = new MockPrefixMockConfigurer();
    }

    @Test
    public void shouldReturnTheMockPrefix() {
        assertThat(mockConfigurer.getMockPrefix(), equalTo("mock"));
    }
    
    @Test
    public void shouldReturnTheMockControlName() {
        assertThat(mockConfigurer.getMockControlName(), equalTo("mockControl"));
    }
}