package com.hacareem.finaldestination.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by waqas on 4/29/17.
 */
@Entity
@DiscriminatorValue(value="dropoff")
public class DropoffLocation extends RideLocation {
}
