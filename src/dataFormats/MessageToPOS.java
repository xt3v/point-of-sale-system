package dataFormats;

public class MessageToPOS implements dataFormat {
    private boolean success;
    private float credit;

    public MessageToPOS(boolean success,float credit) {
        this.success = success;
        this.credit = credit;
    }

    public boolean isSuccess() {
        return success;
    }

    public float getCredit() {
        return credit;
    }
}
