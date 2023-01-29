package ru.netology;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.ownText;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selenide.*;
class CardDeliveryTest {


    @Test
    public void shouldChangeDeliveryDate() {
        open("http://localhost:9999");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE); // убираем автозаполнение поля ввода даты
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id=phone] input").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id=agreement]").click();
        $x("//*[text()=\"Запланировать\"]").click();
        $(byClassName("notification__title")).should(ownText("Успешно!"), Duration.ofSeconds(15));
        $(byClassName("notification__content")).should(ownText("Встреча успешно запланирована на " + firstMeetingDate), Duration.ofSeconds(15));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE); // убираем автозаполнение поля ввода даты
        $("[data-test-id=date] input").setValue(secondMeetingDate);
        $x("//*[text()=\"Запланировать\"]").click();
        $("[data-test-id=replan-notification] .notification__title").should(ownText("Необходимо подтверждение"), Duration.ofSeconds(15));
        $("[data-test-id=replan-notification] .notification__content").should(ownText("У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(15));
        $x("//*[text()=\"Перепланировать\"]").click();
        $(byClassName("notification__title")).should(ownText("Успешно!"), Duration.ofSeconds(15));
        $(byClassName("notification__content")).should(ownText("Встреча успешно запланирована на " + secondMeetingDate), Duration.ofSeconds(15));
    }
}
