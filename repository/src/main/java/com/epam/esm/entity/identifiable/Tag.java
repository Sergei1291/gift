package com.epam.esm.entity.identifiable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tag")
public class Tag extends RepresentationModel<Tag> implements Identifiable, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name", nullable = false, unique = true)
    @NotEmpty
    private String name;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "gift_certificate_tag",
            joinColumns = @JoinColumn(name = "tag"),
            inverseJoinColumns = @JoinColumn(name = "certificate"))
    private List<GiftCertificate> giftCertificates;

    public Tag() {
    }

    public Tag(int id) {
        this.id = id;
    }

    public Tag(String name) {
        this.id = 0;
        this.name = name;
    }

    public Tag(int id, String name, List<GiftCertificate> giftCertificates) {
        this.id = id;
        this.name = name;
        this.giftCertificates = giftCertificates;
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

    public List<GiftCertificate> getGiftCertificates() {
        return giftCertificates;
    }

    public void setGiftCertificates(List<GiftCertificate> giftCertificates) {
        this.giftCertificates = giftCertificates;
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
        Tag tag = (Tag) o;
        return id == tag.id &&
                Objects.equals(name, tag.name) &&
                Objects.equals(giftCertificates, tag.giftCertificates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, giftCertificates);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", giftCertificates=" + giftCertificates +
                '}';
    }

}