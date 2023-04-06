package com.example.hurtownia.domain;

import java.io.FileNotFoundException;

public abstract class AbstractReport {

    public abstract void generateReport(String path, String title) throws FileNotFoundException;
}
