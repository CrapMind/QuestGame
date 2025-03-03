package model.util;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class Choices {

    String rightChoice;
    String wrongChoice;
    String anotherWrongChoice;

    public Choices(String rightChoice, String wrongChoice) {
        this.rightChoice = rightChoice;
        this.wrongChoice = wrongChoice;
    }
}
