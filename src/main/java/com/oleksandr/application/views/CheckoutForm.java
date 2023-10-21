package com.oleksandr.application.views;

import com.oleksandr.application.data.entity.CardInformation;
import com.oleksandr.application.data.entity.Client;
import com.oleksandr.application.data.entity.Game;
import com.oleksandr.application.data.entity.Payment;
import com.oleksandr.application.data.entity.enums.CardType;
import com.oleksandr.application.data.service.ClientService;
import com.oleksandr.application.data.service.GameService;
import com.oleksandr.application.data.service.PaymentService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hibernate.Session;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Route(value = "checkout-form/:userID?/:gameID?", layout = Navbar.class)
@PageTitle("Checkout form")
public class CheckoutForm extends VerticalLayout implements BeforeEnterObserver {

    private GameService gameService;
    private ClientService clientService;
    private PaymentService paymentService;

    private int gameId;
    private int clientId;

    RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
    TextField nameOnCard = new TextField();
    TextField creditCardNumber = new TextField();
    TextField expiration = new TextField();
    TextField cvv = new TextField();

    public CheckoutForm(GameService gameService, ClientService clientService, PaymentService paymentService) {
        this.gameService = gameService;
        this.clientService = clientService;
        this.paymentService = paymentService;

        setAlignItems(Alignment.CENTER);
        setSpacing(true);
        setPadding(true);
        getStyle().set("padding-left", "10%");
        getStyle().set("padding-right", "10%");
        setWidthFull();

        VerticalLayout billingAddressLayout = new VerticalLayout();
        billingAddressLayout.setWidth("65%");
        billingAddressLayout.setMinWidth("300px");
        billingAddressLayout.setSpacing(true);
        billingAddressLayout.setPadding(false);


        billingAddressLayout.getElement().getStyle().set("flex-grow", "1");


        H1 header = new H1();
        header.setText("Checkout form");
        header.setWidthFull();
        header.getStyle().set("text-align", "center");
        header.getStyle().set("font-weight", "bold");

        HorizontalLayout contentLayout = new HorizontalLayout();
        contentLayout.addClassName("contentlayout");
        contentLayout.setSpacing(false);
        contentLayout.setHeightFull();
        contentLayout.setWidthFull();

        // A formLayout is used to add the fields. It is also responsive.
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("10em", 1,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("20em", 3,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("30em", 6,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP));

        formLayout.setWidthFull();

        Hr downHR = new Hr();
        formLayout.setColspan(formLayout.addFormItem(downHR, ""), 6);

        H2 paymentHeader = new H2();
        paymentHeader.setText("Payment");
        formLayout.setColspan(formLayout.addFormItem(paymentHeader, ""), 6);


        radioGroup.setItems("Credit card", "Debit card", "PayPal");
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        formLayout.setColspan(formLayout.addFormItem(radioGroup, ""), 6);


        nameOnCard.setWidthFull();
        formLayout.setColspan(
                formLayout.addFormItem(nameOnCard, "Name on card"), 3);


        creditCardNumber.setWidthFull();
        formLayout.setColspan(
                formLayout.addFormItem(creditCardNumber, "Credit card number"),
                3);


        expiration.setWidthFull();
        formLayout.setColspan(formLayout.addFormItem(expiration, "Expiration"),
                1);


        cvv.setWidthFull();
        formLayout.setColspan(formLayout.addFormItem(cvv, "CVV"), 1);

        Button saveButton = new Button("Continue to checkout",
                new Icon(VaadinIcon.ARROW_RIGHT));
        saveButton.addThemeVariants(ButtonVariant.LUMO_LARGE,
                ButtonVariant.LUMO_PRIMARY);

        saveButton.getStyle().set("margin-left", "auto");

        saveButton.addClickListener(ev -> {
            Dialog dialog = new Dialog();

            dialog.setHeaderTitle("Confirm payment?");
            Button yes = new Button("Yes");
            Button no = new Button("No", e -> dialog.close());


            yes.addClickListener(e -> {
                dialog.close();

                if(radioGroup.isEmpty() || nameOnCard.isEmpty() || cvv.isEmpty() || creditCardNumber.isEmpty() || expiration.isEmpty()) {
                    Notification.show("ERROR: Some value is left empty");
                    return;
                }

                String cardFieldVal = creditCardNumber.getValue();
                if(cardFieldVal.length()<15 || cardFieldVal.length()>19 || !cardFieldVal.matches("[0-9]+")) {
                    Notification.show("ERROR: Invalid credit card number");
                    return;
                }

                String expDate = expiration.getValue();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
                simpleDateFormat.setLenient(false);
                Date expiry = null;
                try {
                    expiry = simpleDateFormat.parse(expDate);
                } catch (ParseException ex) {
                    Notification.show("ERROR: Invalid expiration date");
                    return;
                }

                if(expiry.before(new Date())){
                    Notification.show("ERROR: Your card is expired");
                    return;
                }

                purchase();

                yes.getUI().ifPresent(ui -> ui.navigate("success"));
            });


            yes.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                    ButtonVariant.LUMO_SUCCESS);

            no.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                    ButtonVariant.LUMO_ERROR);

            dialog.getFooter().add(yes);
            dialog.getFooter().add(no);

            add(dialog);

            dialog.open();

            getStyle().set("position", "fixed").set("top", "0").set("right", "0")
                    .set("bottom", "0").set("left", "0").set("display", "flex")
                    .set("align-items", "center").set("justify-content", "center");
        });

        billingAddressLayout.add(formLayout, saveButton);
        contentLayout.add(billingAddressLayout);
        add(header, contentLayout);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Optional<String> clientUrl = beforeEnterEvent.getRouteParameters().get("userID");
        Optional<String> gameUrl = beforeEnterEvent.getRouteParameters().get("gameID");

        if(clientUrl.isEmpty() || gameUrl.isEmpty()) {
            this.getUI().ifPresent(ui -> ui.navigate(""));
        }

        clientId = Integer.parseInt(clientUrl.get());
        gameId = Integer.parseInt(gameUrl.get());
    }


    private void purchase(){

        String value = radioGroup.getValue();
        CardType cardType = CardType.DebitCard;

        Optional<Game> game = gameService.findGameById(gameId);

        Optional<Client> client = clientService.getClientById((long) clientId);


        if(value.equals("Credit card"))
            cardType = CardType.CreditCard;
        if(value.equals("PayPal"))
            cardType = CardType.PayPal;

        String name = nameOnCard.getValue();
        String creditCard = creditCardNumber.getValue();
        String expire = expiration.getValue();
        String cvvVal = cvv.getValue();

        CardInformation cardInformation = CardInformation.builder().cardNumber(creditCard).cardType(cardType).cvv(cvvVal).expirationDate(expire).nameOnCard(name).build();

        double amount = 0;
        LocalDate now = LocalDate.now();

        Client client1 = new Client();
        Game game1 = new Game();

        if(client.isPresent())
            client1 = client.get();
        if(game.isPresent()) {
            amount = game.get().getPrice();
            game1 = game.get();
        }

        Payment payment = Payment.builder().amount(amount).buyer(client1).cardInformation(cardInformation).date(now).product(game1).build();

        client1.addPayment(payment);
        game1.addPayment(payment);

        paymentService.savePayment(payment);

    }
}
