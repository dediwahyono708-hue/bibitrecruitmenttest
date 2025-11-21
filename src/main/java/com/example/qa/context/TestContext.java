package com.example.qa.context;

public class TestContext {
    private static final ThreadLocal<String> selectedColor = new ThreadLocal<>();

    public static void setSelectedColor(String color) {
        selectedColor.set(color);
    }

    public static String getSelectedColor() {
        String c = selectedColor.get();
        return c == null ? "" : c;
    }

    public static void clear() {
        selectedColor.remove();
    }
}
