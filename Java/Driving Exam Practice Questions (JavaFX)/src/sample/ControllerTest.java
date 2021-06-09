package sample;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void getAllQuestions() {
        Controller ctrl = new Controller();
        List<Frage> list = ctrl.getAllQuestions();
        Assert.assertEquals(list.size(),26);
    }
}