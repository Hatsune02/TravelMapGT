package com.navi;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDarkerIJTheme;
import com.navi.UI.DashBoard;

public class Main {
    public static void main(String[] args) {
        FlatMaterialDarkerIJTheme.setup();
        DashBoard d = new DashBoard();
        d.setVisible(true);
    }
}