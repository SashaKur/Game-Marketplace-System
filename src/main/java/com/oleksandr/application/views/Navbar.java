package com.oleksandr.application.views;


import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;

public class Navbar extends AppLayout {

    public Navbar() {
        H1 title = new H1("Digital Game Marketplace");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("left", "var(--lumo-space-l)").set("margin", "0")
                .set("position", "absolute");

        Tabs tabs = new Tabs();
        tabs.getStyle().set("margin", "auto");

        RouterLink mainLink = new RouterLink("Main Panel", MainPanel.class);
        RouterLink browseLink = new RouterLink("Browse games", GameBrowsing.class);
        RouterLink libraryLink = new RouterLink("Game library", MainPanel.class);

        mainLink.setTabIndex(-1);
        browseLink.setTabIndex(-1);
        libraryLink.setTabIndex(-1);

        tabs.add(new Tab(mainLink), new Tab(browseLink), new Tab(libraryLink));

        Div footer = new Div();
        footer.setText("Project created by Oleksandr Kurchak");
        footer.setClassName("footer");

        addToNavbar(title, tabs);
        addToDrawer();
    }
}
