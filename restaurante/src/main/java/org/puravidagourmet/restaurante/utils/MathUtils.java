package org.puravidagourmet.restaurante.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {

  public static float round(float d, int decimalPlace) {
    BigDecimal bd = new BigDecimal(Float.toString(d));
    bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
    return bd.floatValue();
  }
}
