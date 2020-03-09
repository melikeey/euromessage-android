package euromsg.com.euromobileandroid.model;

public class ActionElement {

    private String id;
    private String buttonTitle;

    public ActionElement(String id, String buttonTitle) {
        this.id = id;
        this.buttonTitle = buttonTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getButtonTitle() {
        return buttonTitle;
    }

    public void setButtonTitle(String buttonTitle) {
        this.buttonTitle = buttonTitle;
    }
}
