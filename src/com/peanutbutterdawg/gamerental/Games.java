package com.peanutbutterdawg.gamerental;

// Games implements Item
public class Games implements Item {

  // Variables
  private String title;
  private Genre genre;
  private int rating;
  private ESRB esrb;

  // Games Constructor
  public Games(String title, Genre genre, int rating, ESRB esrb) {
    this.title = title;
    this.genre = genre;
    this.rating = rating;
    this.esrb = esrb;
  }

  // ESRB Getter
  public ESRB getEsrb() {
    return esrb;
  }

  // ESRB Setter
  public void setEsrb(ESRB esrb) {
    this.esrb = esrb;
  }

  // Rating Getter
  public int getRating() {
    return rating;
  }

  // Rating Setter
  public void setRating(int rating) {
    this.rating = rating;
  }

  // Genre Getter
  @Override
  public Genre getGenre() {
    return genre;
  }

  // Genre Setter
  @Override
  public void setGenre(Genre genre) {
    this.genre = genre;
  }

  // Title Getter
  public String getTitle() {
    return title;
  }

  // Title Setter
  public void setTitle(String title) {
    this.title = title;
  }
}
