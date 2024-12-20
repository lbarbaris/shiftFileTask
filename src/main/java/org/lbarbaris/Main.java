package org.lbarbaris;

import org.lbarbaris.fileutils.ArgumentManager;

import java.io.IOException;


public class Main {
    public static void main(String[] args) {

        try {
            new ArgumentManager(args).run();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}