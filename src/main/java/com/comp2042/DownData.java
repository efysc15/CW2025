package com.comp2042;

public final class DownData {
    private final ClearRow clearRow;
    private final ViewData viewData;
    private final boolean landed;

    public DownData(ClearRow clearRow, ViewData viewData, boolean landed) {
        this.clearRow = clearRow;
        this.viewData = viewData;
        this.landed = landed;
    }

    public ClearRow getClearRow() {
        return clearRow;
    }

    public ViewData getViewData() {
        return viewData;
    }

    public boolean isLanded() {
        return landed;
    }
}
