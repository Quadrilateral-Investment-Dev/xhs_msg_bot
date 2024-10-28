package com.example.xiaohongshuautomation;

import androidx.test.uiautomator.UiDevice;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainBotRunner {

    private UiDevice device;

    @Before
    public void setUp() {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void runBotTest() {
        XiaohongshuBot bot = new XiaohongshuBot(device, InstrumentationRegistry.getInstrumentation().getTargetContext());
        bot.runBot();
    }
}
