package com.epam.esm.entity.identifiable;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "gift_certificate")
public class GiftCertificate extends RepresentationModel<GiftCertificate>
        implements Identifiable, Serializable {

    @PrePersist
    public void onPrePersist() {
        String createDateAudit = createTime();
        setCreateDate(createDateAudit);
        setLastUpdateDate(null);
    }

    @PreUpdate
    public void onPreUpdate() {
        String lastUpdateDateAudit = createTime();
        setLastUpdateDate(lastUpdateDateAudit);
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
    @Column(name = "name", nullable = false, unique = true)
    @NotEmpty
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    @PositiveOrZero
    private Integer price;
    @Column(name = "duration")
    @PositiveOrZero
    private Integer duration;
    @Column(name = "create_date")
    private String createDate;
    @Column(name = "last_update_date")
    private String lastUpdateDate;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "gift_certificate_tag",
            joinColumns = @JoinColumn(name = "certificate"),
            inverseJoinColumns = @JoinColumn(name = "tag"))
    private List<Tag> tags;

    public GiftCertificate() {
    }

    public GiftCertificate(int id,
                           String name,
                           String description,
                           Integer price,
                           Integer duration,
                           String createDate,
                           String lastUpdateDate,
                           List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GiftCertificate that = (GiftCertificate) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price) &&
                Objects.equals(duration, that.duration) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(lastUpdateDate, that.lastUpdateDate) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, duration, createDate, lastUpdateDate, tags);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate='" + createDate + '\'' +
                ", lastUpdateDate='" + lastUpdateDate + '\'' +
                ", tags=" + tags +
                '}';
    }

}