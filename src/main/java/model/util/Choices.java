package model.util;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class Choices {

    private String rightChoice;
    private String wrongChoice;
    private String anotherWrongChoice;

    public Choices(String rightChoice, String wrongChoice) {
        this.rightChoice = rightChoice;
        this.wrongChoice = wrongChoice;
    }
}
