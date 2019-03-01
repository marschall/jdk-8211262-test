package com.github.marschall.jdk8211262;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

import static org.hamcrest.Matchers.comparesEqualTo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;

class JDK8211262Tests {
  
  private static final Locale SWITZERLAND = new Locale("de", "CH");

  @Test
  void decimalFormat() {
    DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(SWITZERLAND);
    format.applyPattern("#,##0.00");
    
    String formatted = format.format(new BigDecimal("1234567.8"));
    assertEquals("1'234'567.80", formatted);
  }
  
  @Test
  void decimalFormatExplicit() {
    DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(SWITZERLAND);
    format.applyPattern("#,##0.00");
    DecimalFormatSymbols decimalFormatSymbols = format.getDecimalFormatSymbols();
    decimalFormatSymbols.setGroupingSeparator('\'');
    // important because getDecimalFormatSymbols returns a clone
    format.setDecimalFormatSymbols(decimalFormatSymbols);
    
    String formatted = format.format(new BigDecimal("1234567.8"));
    assertEquals("1'234'567.80", formatted);
  }
  
  @Test
  void parseNew() throws ParseException {
    DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(SWITZERLAND);
    format.applyPattern("#,##0.00");
    format.setParseBigDecimal(true);
    
    BigDecimal parsed = (BigDecimal) format.parse("1’234’567.80");
    assertThat(parsed, comparesEqualTo(new BigDecimal("1234567.8")));
  }
  
  @Test
  void parseExplicit() throws ParseException {
    DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(SWITZERLAND);
    format.applyPattern("#,##0.00");
    DecimalFormatSymbols decimalFormatSymbols = format.getDecimalFormatSymbols();
    decimalFormatSymbols.setGroupingSeparator('\'');
    // important because getDecimalFormatSymbols returns a clone
    format.setDecimalFormatSymbols(decimalFormatSymbols);
    format.setParseBigDecimal(true);
    
    BigDecimal parsed = (BigDecimal) format.parse("1'234'567.80");
    assertThat(parsed, comparesEqualTo(new BigDecimal("1234567.8")));
  }
  
  @Test
  void parseOld() throws ParseException {
    DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(SWITZERLAND);
    format.applyPattern("#,##0.00");
    format.setParseBigDecimal(true);
    
    BigDecimal parsed = (BigDecimal) format.parse("1'234'567.80");
    assertThat(parsed, comparesEqualTo(new BigDecimal("1234567.8")));
  }
  
  @Test
  @Disabled
  void stringFormat() {
    DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(SWITZERLAND);
    format.applyPattern("#,##0.00");
    
    String formatted = String.format("%#,##0.00d", new BigDecimal("1234567.8"));
    assertEquals("1'234'567.80", formatted);
    formatted = String.format(SWITZERLAND, "%#,##0.00d", new BigDecimal("1234567.8"));
    assertEquals("1'234'567.80", formatted);
  }
  
  @Test
  void currencyFormat() {
    NumberFormat format = NumberFormat.getCurrencyInstance(SWITZERLAND);
    format.setCurrency(Currency.getInstance("CHF"));
    
    String formatted = format.format(new BigDecimal("1234567.8"));
    assertEquals("SFr. 1'234'567.80", formatted);
  }
  
  @Test
  void currencyFormatExplicit() {
    DecimalFormat format = (DecimalFormat) NumberFormat.getCurrencyInstance(SWITZERLAND);
    format.setCurrency(Currency.getInstance("CHF"));
    DecimalFormatSymbols decimalFormatSymbols = format.getDecimalFormatSymbols();
    decimalFormatSymbols.setGroupingSeparator('\'');
    // important because getDecimalFormatSymbols returns a clone
    format.setDecimalFormatSymbols(decimalFormatSymbols);
    
    String formatted = format.format(new BigDecimal("1234567.8"));
    assertEquals("SFr. 1'234'567.80", formatted);
  }

}
