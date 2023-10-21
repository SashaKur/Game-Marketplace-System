package com.oleksandr.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;


@SpringComponent
@PermitAll
@Route(value = "success", layout = Navbar.class)
public class SuccessPanel extends VerticalLayout {
    Button another = new Button("Browse other games");

    public SuccessPanel() {
        removeFromParent();
        another.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_LARGE);

        another.addClickListener(ev -> another.getUI().ifPresent(ui -> ui.navigate("browse")));

        Image image = new Image();
        image.setSrc("https://img.icons8.com/?size=512&id=Jbya1K1hB2L0&format=png");

        Div content = new Div();
        content.getElement().setProperty("innerHTML", "<h1 style=\"font-size: 36;\"><center> Congratulations! <center></h1></br><h1 style=\"font-size: 26;\"><center> You have successfully bought the game! <center></h1>");

        add(image, content, another);
        setAlignItems(Alignment.CENTER);
        setSizeFull();
    }
}
