package com.epam.esm.entity.identifiable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "order_certificate")
public class Order extends RepresentationModel<Order> implements Identifiable, Serializable {

    @PrePersist
    public void onPrePersist() {
        String purchaseTime = createTime();
        setPurchaseTime(purchaseTime);
    }

    private String createTime() {
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.ms");
        ZoneOffset zoneOffset = ZoneOffset.UTC;
        DateTimeFormatter dateTimeFormatterZone = dateTimeFormatter.withZone(zoneOffset);
        return dateTimeFormatterZone.format(Instant.now());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "cost")
    @PositiveOrZero
    private int cost;
    @Column(name = "purchase_time")
    @NotEmpty
    private String purchaseTime;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id", nullable = false)
    private GiftCertificate giftCertificate;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Order() {
    }

    public Order(int id, int cost, String purchaseTime, GiftCertificate giftCertificate, User user) {
        this.id = id;
        this.cost = cost;
        this.purchaseTime = purchaseTime;
        this.giftCertificate = giftCertificate;
        this.user = user;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Order order = (Order) o;
        return id == order.id &&
                cost == order.cost &&
                Objects.equals(purchaseTime, order.purchaseTime) &&
                Objects.equals(giftCertificate, order.giftCertificate) &&
                Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, cost, purchaseTime, giftCertificate, user);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "id=" + id +
                ", cost=" + cost +
                ", purchaseTime='" + purchaseTime + '\'' +
                ", giftCertificate=" + giftCertificate +
                ", user=" + user +
                '}';
    }

}