package com.idat.proyect.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

//por que esta sera incluida en otra clase
@Data
@Embeddable
public class OrderDetailsPK implements Serializable {

     private static final long serialVersionUID = 1L;

     @Column(name = "idOrder")
     private Integer idOrder;
     @Column(name = "idProduct")
     private Integer idProduct;
}