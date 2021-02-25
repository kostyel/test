package com.rincentral.test.services;

class Averager {

    private final int total;
    private final int count;

    public Averager() {
        this.total = 0;
        this.count = 0;
    }

    public Averager(int total, int count) {
        this.total = total;
        this.count = count;
    }

    public double average() {
        return count > 0 ? ((double) total) / count : 0;
    }

    public Averager accept(int i) {
        return new Averager(total + i, count + 1);
    }

    public Averager combine(Averager other) {
        return new Averager(total + other.total, count + other.count);
    }
}
