package com.peanutbutterdawg.gamerental;

public interface Item {
  String getTitle();
  void setTitle(String title);

  Genre getGenre();
  void setGenre(Genre genre);

  int getRating();
  void setRating(int rating);

  ESRB getEsrb();
  void setEsrb(ESRB esrb);
}
