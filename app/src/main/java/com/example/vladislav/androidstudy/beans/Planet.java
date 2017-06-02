package com.example.vladislav.androidstudy.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vladislav on 02.06.17.
 */

public class Planet implements Parcelable {

    private double size;        // Diameter in kms.
    private double weight;      // In tons.
    private double distance;    // Distance from a star in kms.

    public Planet() { }

    public Planet(double size, double weight, double distance) {
        this.size = size;
        this.weight = weight;
        this.distance = distance;
    }

    protected Planet(Parcel in) {
        size = in.readDouble();
        weight = in.readDouble();
        distance = in.readDouble();
    }

    public static final Creator<Planet> CREATOR = new Creator<Planet>() {
        @Override
        public Planet createFromParcel(Parcel in) {
            return new Planet(in);
        }

        @Override
        public Planet[] newArray(int size) {
            return new Planet[size];
        }
    };

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Planet planet = (Planet) o;

        if (Double.compare(planet.size, size) != 0) return false;
        if (Double.compare(planet.weight, weight) != 0) return false;
        return Double.compare(planet.distance, distance) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(size);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(distance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(size);
        dest.writeDouble(weight);
        dest.writeDouble(distance);
    }
}
