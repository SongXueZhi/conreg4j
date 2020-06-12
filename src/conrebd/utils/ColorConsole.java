/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conrebd.utils;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi.BColor;


/**
 *
 * @author knightsong
 */
public class ColorConsole {
    
    static ColoredPrinter cp = new ColoredPrinter.Builder(0, false)
                .background(BColor.YELLOW) //setting format
                .build();

 
    public static void println(String line) {
        cp.println(line);     
    }
    
}