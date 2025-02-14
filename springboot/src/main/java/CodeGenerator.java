import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

public class CodeGenerator {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3308/hotel?characterEncoding=utf8&useSSL=false", "root", "password")
                .globalConfig(builder -> {
                    builder.author("黄毓峰") // 设置作者
                            .enableSwagger() // 开启 Swagger 模式
                            .outputDir("E:\\code\\vue\\hotel\\springboot\\src\\main\\java"); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent("org.example.springboot") // 设置父包名
                                .moduleName("api") // 设置父包模块名
                                .pathInfo(Collections.singletonMap(OutputFile.xml, "E:\\code\\vue\\hotel\\springboot\\src\\main\\resources\\mapper")) // 设置 Mapper XML 文件的生成路径
                )
                .strategyConfig(builder -> {
                    builder.addInclude("appraise") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                    builder.controllerBuilder() // 启用控制器生成器
                            .enableRestStyle(); // 开启 Rest 风格，生成 @RestController
                    builder.entityBuilder()
                            .enableLombok() // 开启 Lombok 注解
                            .enableTableFieldAnnotation(); // 开启字段注解

                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用 Freemarker 引擎模板
                .execute();
    }
}
