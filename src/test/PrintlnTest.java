/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi.Attribute;
import com.diogonunes.jcdp.color.api.Ansi.BColor;
import com.diogonunes.jcdp.color.api.Ansi.FColor;

/**
 *
 * @author knightsong
 */
public class PrintlnTest {

    public static void main(String[] args) {
//       System.out.println(ConsoleColors.YELLOW_BACKGROUND + "RED COLORED");
//        System.out.println("sxz");
        //ConsoleColors.printlnc("sxz", ConsoleColors.YELLOW_BACKGROUND);
        ColoredPrinter cp = new ColoredPrinter.Builder(0, false)
                .foreground(FColor.WHITE).background(BColor.BLUE) //setting format
                .build();
        cp.println(cp);
        cp.println("This printer will always format text with WHITE font on BLUE background.");
        cp.setAttribute(Attribute.REVERSE);
        cp.println("From now on, that format is reversed.");
        System.out.println("ColoredPrinters do not affect System.* format.");
        cp.print("Even if");
        System.out.print(" you mix ");
        cp.println("the two.");
        //   +ConsoleColors.RESET + " NORMAL"
    }
}
