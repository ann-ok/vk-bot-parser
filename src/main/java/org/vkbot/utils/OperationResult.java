package org.vkbot.utils;

public class OperationResult {
    public boolean status;
    public String errorMsg;

    public OperationResult() {
        this.status = true;
        this.errorMsg = "";
    }

    public OperationResult(boolean status, String errorMsg) {
        this.status = status;
        this.errorMsg = errorMsg;
    }
}
