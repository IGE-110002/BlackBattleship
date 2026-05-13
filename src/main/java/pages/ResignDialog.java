package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class ResignDialog {

    private final SelenideElement confirmButton = $("#confirm");
    private final SelenideElement cancelButton = $("#cancel");

    public void confirmResign() {
        confirmButton.click();
    }

    public void cancelResign() {
        cancelButton.click();
    }
}
