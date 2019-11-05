package com.peanutbutterdawg.gamerental;

public class Games {
  private String title;
  private Genre genre;
  private int rating;

  public Games(String title, Genre genre, int rating) {
    this.title = title;
    this.genre = genre;
    this.rating = rating;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public Genre getGenre() {
    return genre;
  }

  public void setGenre(Genre genre) {
    this.genre = genre;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
