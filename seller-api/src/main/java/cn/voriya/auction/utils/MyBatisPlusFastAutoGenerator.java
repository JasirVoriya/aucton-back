package cn.voriya.auction.utils;

import cn.voriya.framework.mybatis.BaseEntity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.sql.Types;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MyBatisPlusFastAutoGenerator {
    private static final String url = "jdbc:mysql://localhost:3306/auction_platform?characterEncoding=utf8&useSSL=false";
    private static final String username = "root";
    private static final String password = "root";

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

    private static void autoGenerator() {
        FastAutoGenerator.create(url, username, password)
                // 全局配置
                .globalConfig((scanner, builder) -> builder.author(scanner.apply("请输入作者名称？")))
                // 包配置
                .packageConfig((scanner, builder) -> builder.parent(scanner.apply("请输入包名？")))
                // 策略配置
                .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                        .controllerBuilder().enableRestStyle().enableHyphenStyle()
                        .entityBuilder().enableLombok().addTableFills(
                                new Column("create_time", FieldFill.INSERT)
                        ).build())
//                    模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
//                   .templateEngine(new BeetlTemplateEngine())
//                   .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    // 处理 all 情况
    public static void fastAutoGenerator() {
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("JasirVoriya") // 设置作者
                            .commentDate("yyyy-MM-dd") // 设置日期格式
                            .outputDir("D:/Code/GraduationDesign/auction-back/seller-api/src/main/java"); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.parent("cn.voriya") // 设置父包名
                            .moduleName("auction") // 设置父包模块名
                            .entity("entity.dos") // 设置实体类包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D:/Code/GraduationDesign/auction-back/seller-api/src/main/resources/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig((scanner,builder) -> {
                    builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all"))) // 设置需要生成的表名
                            .addTablePrefix("tb_"); // 设置过滤表前缀
                    builder.entityBuilder() // 设置实体类配置
                            .enableLombok() // 开启lombok
                            .disableSerialVersionUID()// 关闭序列化id
                            .superClass(BaseEntity.class)
                            .enableFileOverride(); // 开启文件覆盖
                    builder.controllerBuilder();
                    builder.mapperBuilder();
                    builder.serviceBuilder();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    public static void main(String[] args) {
        fastAutoGenerator();
    }
}
