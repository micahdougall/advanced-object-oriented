package org.example.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode @AllArgsConstructor
@Data public class Coordinate {
    private int x;
    private int  y;
}
