package com.example.lab6.view;


import com.example.lab6.pojo.Wizard;
import com.example.lab6.pojo.Wizards;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.OverridesAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Route(value = "mainPage.it")
public class MainWizardView extends VerticalLayout {
    private TextField fullname;
    private Button btn1, btn2, btn3, btn4, btn5;

    public int number = -1;

    public MainWizardView() {
        fullname = new TextField("");
        fullname.setPlaceholder("Fullname");

        RadioButtonGroup<String> gender = new RadioButtonGroup<>();
        gender.setLabel("Gender :");
        gender.setItems("Male", "Female");

        ComboBox<String> position = new ComboBox<>("");
        position.setPlaceholder("Position");
        position.setItems("student", "teacher");

        NumberField dollarField = new NumberField("Dollars");
        dollarField.setPrefixComponent(new Span("$"));

        ComboBox<String> school = new ComboBox<>("");
        school.setPlaceholder("School");
        school.setItems("Hogwarts", "Beauxbatons", "Durmstrang");

        ComboBox<String> house = new ComboBox<>("");
        house.setPlaceholder("House");
        house.setItems("Gryffindor", "Ravenclaw", "Hufflepuff", "Slyther");

        btn1 = new Button("<<");
        btn2 = new Button("Create");
        btn3 = new Button("Update");
        btn4 = new Button("Delete");
        btn5 = new Button(">>");

        HorizontalLayout hrz = new HorizontalLayout();
        hrz.add(btn1, btn2, btn3, btn4, btn5);

        add(fullname, gender, position, dollarField, school, house, hrz);

        btn5.addClickListener(event -> {
            List out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/wizards")
                    .retrieve()
                    .bodyToMono(List.class)
                    .block();

            if (this.number >= out.size()-1){
                this.number = out.size()-1;
            }
            else {
                this.number+=1;
            }

            ObjectMapper mapper = new ObjectMapper();
            Wizard wizard = mapper.convertValue(out.get(this.number), Wizard.class);

            fullname.setValue(wizard.getName());
            if (Objects.equals(wizard.getSex(), "m")){
                gender.setValue("Male");
            }
            else if (Objects.equals(wizard.getSex(), "f")){
                gender.setValue("Female");
            }
            position.setValue(wizard.getPosition());
            dollarField.setValue((double) wizard.getMoney());
            school.setValue(wizard.getSchool());
            house.setValue(wizard.getHouse());
        });
        btn1.addClickListener(event -> {
            List out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/wizards")
                    .retrieve()
                    .bodyToMono(List.class)
                    .block();

            if (this.number <= 0){
                this.number = 0;
            }
            else {
                this.number-=1;
            }

            ObjectMapper mapper = new ObjectMapper();
            Wizard wizard = mapper.convertValue(out.get(this.number), Wizard.class);

            fullname.setValue(wizard.getName());
            if (Objects.equals(wizard.getSex(), "m")){
                gender.setValue("Male");
            }
            else if (Objects.equals(wizard.getSex(), "f")){
                gender.setValue("Female");
            }
            position.setValue(wizard.getPosition());
            dollarField.setValue((double) wizard.getMoney());
            school.setValue(wizard.getSchool());
            house.setValue(wizard.getHouse());
        });
        btn2.addClickListener(event -> {
            String name = fullname.getValue();
            String sex;
            if (gender.getValue() == "Male"){
                sex = "m";
            }
            else {
                sex = "f";
            }
            Double money = dollarField.getValue();
            String out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addWizard")
                    .body(Mono.just(new Wizard(null, name, sex, school.getValue(), house.getValue(), position.getValue(),money)), Wizard.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            Notification notification = Notification.show("Complete");
        });
        btn4.addClickListener(event -> {
            String name = fullname.getValue();
            String sex;
            if (gender.getValue() == "Male"){
                sex = "m";
            }
            else {
                sex = "f";
            }
            Double money = dollarField.getValue();
            String out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/deleteWizard")
                    .body(Mono.just(new Wizard(null, name, sex, school.getValue(), house.getValue(), position.getValue(),money)), Wizard.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            fullname.setValue("");
            gender.setValue("");
            position.setValue("");
            dollarField.setValue((double) 0);
            school.setValue("");
            house.setValue("");
            Notification notification = Notification.show("Complete");
        });
        btn3.addClickListener(event -> {
            List out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/wizards")
                    .retrieve()
                    .bodyToMono(List.class)
                    .block();

            ObjectMapper mapper = new ObjectMapper();
            Wizard wizard = mapper.convertValue(out.get(this.number), Wizard.class);

            String name = fullname.getValue();
            String sex;
            if (gender.getValue() == "Male"){
                sex = "m";
            }
            else {
                sex = "f";
            }
            Double money = dollarField.getValue();
            String out2 = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/updateWizard")
                    .body(Mono.just(new Wizard(wizard.get_id(), name, sex, school.getValue(), house.getValue(), position.getValue(),money)), Wizard.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            Notification notification = Notification.show("Complete");
        });
    }
}
