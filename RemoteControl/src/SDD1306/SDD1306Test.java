package SDD1306;

import java.io.IOException;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import com.pi4j.wiringpi.I2C;
 
//import OLED.SSD1306_Constants;
//import OLED.SSD1306_I2C_Display;
 
public class SDD1306Test 
{
  /**
   * @param args
   */
  public static void main(String[] args) {
    // create gpio controller
    final GpioController gpio = GpioFactory.getInstance();
    I2CBus i2c;
    SDD1306Display display;
    try 
    {
      i2c = I2CFactory.getInstance(I2C.CHANNEL_1);
      display = new SDD1306Display(SDD1306Constants.LCD_WIDTH_128, SDD1306Constants.LCD_HEIGHT_64,
        gpio, i2c, 0x3c);
      display.begin();
      display.displayString("Hello Lars");
    } catch (UnsupportedBusNumberException | IOException e) {
      e.printStackTrace();
    }
  }
}