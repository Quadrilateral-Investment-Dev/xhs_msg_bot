package com.example.xiaohongshuautomation;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class XiaohongshuBot {

    private UiDevice device;
    private Map<String, String> xhsIds;
    private String messageText = "你好老板";

    public XiaohongshuBot(UiDevice device, Context context) {
        this.device = device;
        DataReader dbReader = new DataReader(context);
        this.xhsIds = dbReader.fetchXhsIdUserIdMap();
        this.messageText = loadMessageFromFile(context);
    }

    private String loadMessageFromFile(Context context) {
        StringBuilder message = new StringBuilder();
        AssetManager assetManager = context.getAssets();

        try (InputStream is = assetManager.open("message.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = reader.readLine()) != null) {
                message.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message.toString();
    }

    public void runBot() {
        System.out.println("XiaohongshuBot: " + "Bot launched");
        for (Map.Entry<String, String> entry : xhsIds.entrySet()) {
            try {
                System.out.println("XiaohongshuBot: " + "Now contacting " + entry.getKey());
                searchAndSendMessage(entry.getKey(), entry.getValue());
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void searchAndSendMessage(String xhsId, String userId) throws UiObjectNotFoundException {
        // Open the search field in Xiaohongshu
        UiObject searchIcon = device.findObject(new UiSelector().packageName("com.xingin.xhs")
                .description("Search")
                .className("android.widget.Button"));
        searchIcon.click();

        // Enter xhs_id in the search box
        UiObject searchBox = device.findObject(new UiSelector().packageName("com.xingin.xhs")
                .className("android.widget.EditText")
                .textContains("Search"));
        searchBox.setText(xhsId);
        device.pressEnter();

        // Select user from search results
        UiObject userResult = device.findObject(new UiSelector().packageName("com.xingin.xhs")
                .className("android.widget.TextView")
                .text("Users"));
        userResult.click();
        UiObject userNameField = device.findObject(new UiSelector()
                .packageName("com.xingin.xhs")
                .className("android.widget.TextView")
                .text(userId));

        try {
            // First attempt to click with exact text match
            if (userNameField.exists()) {
                userNameField.click();
                System.out.println("User text view clicked with exact match.");
            } else {
                // If exact match fails, try with starts-with match
                userNameField = device.findObject(new UiSelector()
                        .packageName("com.xingin.xhs")
                        .className("android.widget.TextView")
                        .textContains(userId));

                if (userNameField.exists()) {
                    userNameField.click();
                    System.out.println("User text view clicked with starts-with match.");
                } else {
                    System.out.println("User text view not found with either exact or starts-with match.");
                }
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        // Find the message button
        UiObject button = device.findObject(new UiSelector()
                .className("android.widget.Button")
                .text("发私信"));

        UiObject imageView = device.findObject(new UiSelector()
                .className("android.widget.ImageView")
                .packageName("com.xingin.xhs")
                .description("Message"));

        try {
            if (button.exists()) {
                button.click();
                System.out.println("Button clicked successfully.");
            }
            else if (imageView.exists()) {
                imageView.click();
                System.out.println("ImageView clicked successfully.");
            }
            else {
                System.out.println("Neither element found.");
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        // Enter and send the message
        UiObject messageBox = device.findObject(new UiSelector().packageName("com.xingin.xhs")
                .className("android.widget.EditText")
                .text("Send a message…"));
        messageBox.setText(messageText);

        UiObject sendButton = device.findObject(new UiSelector().packageName("com.xingin.xhs")
                .className("android.widget.TextView")
                .text("Send"));
        sendButton.click();

        // Return back to HOME
        UiObject returnButton = device.findObject(new UiSelector().packageName("com.xingin.xhs")
                .className("android.widget.ImageView")
                .resourceId("com.xingin.xhs:id/fyd"));
        returnButton.click();

        returnButton = device.findObject(new UiSelector().packageName("com.xingin.xhs")
                .className("android.widget.ImageView")
                .descriptionMatches("(?i)back"));
        returnButton.click();

        returnButton = device.findObject(new UiSelector().packageName("com.xingin.xhs")
                .className("android.widget.ImageView")
                .descriptionMatches("(?i)back"));
        returnButton.click();

        returnButton = device.findObject(new UiSelector().packageName("com.xingin.xhs")
                .className("android.widget.Button")
                .descriptionMatches("(?i)back"));
        returnButton.click();



    }
}
