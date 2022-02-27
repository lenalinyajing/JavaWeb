package com.xsz;//package java.com.xsz;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import sun.nio.cs.Surrogate;

import javax.management.modelmbean.XMLParseException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class GeneratorCode {
    public static void main(String[] args) throws IOException, InvalidConfigurationException, SQLException, InterruptedException, XMLParserException {
        ArrayList<String> warnings = new ArrayList<String>();
        boolean ooverwrite=true;
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(
                Surrogate.Generator.class.getResourceAsStream("/config/mybatis-generator.xml")
        );

        DefaultShellCallback callback = new DefaultShellCallback(ooverwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);

    }
}
