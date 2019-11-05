package com.peanutbutterdawg.gamerental;

public enum Genre {
  ACTION(1),
  ADVENTURE(2),
  RPG(3),
  SPORTS(4),
  MMO(5),
  STRATEGY(6),
  SIMULATION(7);

  private int value;

  Genre(int value) {
    this.value = value;
  }
}
