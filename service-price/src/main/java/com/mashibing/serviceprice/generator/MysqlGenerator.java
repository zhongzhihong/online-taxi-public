package com.mashibing.serviceprice.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class MysqlGenerator {
    public static void main(String[] args) {

        FastAutoGenerator.create("jdbc:mysql://192.168.75.157:3306/service-price?characterEncoding=utf-8&serverTimezone=GMT%2B8",
                        "root", "root")
                .globalConfig(builder -> {
                    builder.author("钟志宏").fileOverride().outputDir("D:\\Code\\online-taxi-public\\service-price\\src\\main\\java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.mashibing.serviceprice").pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                            "D:\\Code\\online-taxi-public\\service-price\\src\\main\\java\\com\\mashibing\\serviceprice\\mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("price_rule");

                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
