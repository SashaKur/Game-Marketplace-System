package com.oleksandr.application.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Main Panel")
@Route(value = "", layout = Navbar.class)
public class MainPanel extends VerticalLayout {

    public MainPanel() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 welcome = new H1();
        welcome.setText("Welcome to Game Marketplace!");
        H2 subtext = new H2();
        subtext.setText("Choose option in navigation panel");
        setSizeFull();

        add(welcome, subtext);

        setClassName("main-layout");
    }

}
