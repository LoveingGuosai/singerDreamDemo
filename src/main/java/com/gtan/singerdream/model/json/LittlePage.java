package com.gtan.singerdream.model.json;

/**
 * Created by user on 15-7-31.
 */
public class LittlePage {

    private int size;
    private long totalElements;
    private long totalPages;
    private long number;

    public LittlePage() {
    }

    public LittlePage(int size, long totalElements, long totalPages, long number) {
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "LittlePage{" +
                "size=" + size +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                ", number=" + number +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LittlePage)) return false;

        LittlePage that = (LittlePage) o;

        if (getSize() != that.getSize()) return false;
        if (getTotalElements() != that.getTotalElements()) return false;
        if (getTotalPages() != that.getTotalPages()) return false;
        return getNumber() == that.getNumber();

    }

    @Override
    public int hashCode() {
        int result = getSize();
        result = 31 * result + (int) (getTotalElements() ^ (getTotalElements() >>> 32));
        result = 31 * result + (int) (getTotalPages() ^ (getTotalPages() >>> 32));
        result = 31 * result + (int) (getNumber() ^ (getNumber() >>> 32));
        return result;
    }
}
