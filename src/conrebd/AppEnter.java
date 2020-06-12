/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conrebd;

import conrebd.cli.Navigation;

/**
 *
 * @author knightsong
 */
public class AppEnter {

    private final static String LOGO = " _____   _____   __   _   _____    _____   _   _       _  \n" +
"/  ___| /  _  \\ |  \\ | | |  _  \\  | ____| | | | |     | | \n" +
"| |     | | | | |   \\| | | |_| |  | |__   | |_| |     | | \n" +
"| |     | | | | | |\\   | |  _  /  |  __|  \\___  |  _  | | \n" +
"| |___  | |_| | | | \\  | | | \\ \\  | |___      | | | |_| | \n" +
"\\_____| \\_____/ |_|  \\_| |_|  \\_\\ |_____|     |_| \\_____/ \n";

    public static void main(String[] args) {
        System.out.println("Starting...");
        //初始数据
        ConReDB.initDB();

        System.out.println(LOGO);
        System.out.println("Start successful ");
        //启动cli模块
        try {
            Navigation.main(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
