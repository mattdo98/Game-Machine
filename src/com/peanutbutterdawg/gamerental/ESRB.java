package com.peanutbutterdawg.gamerental;

public enum ESRB {
  RP(1),
  C(2),
  E(3),
  T(4),
  M(5),
  A(6);

  public int value;

  ESRB(int value) {
    this.value = value;
  }
}
