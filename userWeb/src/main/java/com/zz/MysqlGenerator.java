package com.zz;

import com.sun.org.apache.xpath.internal.operations.String;

public class MysqlGenerator {
    public static void main(String[] args) {
        //数据库信息
        String driver = "com.mysql.cj.jdbc.Driver";
        String databaseUrl = "jdbc://mysql://127.0.0.1:3306/bookedemo?useUnicode=true&useSSL=false&characterEncoding=UTF-8";
        String username = "root";
        String password = "123456";
        //数据库前缀
        String tablePrefix = "tb_";
        String parentPackageName = "com.zz";
        String moduleName = "user";
        excuteGeneratorCode(driver,databaseUrl,username,password,tablePrefix,parentPackageName,moduleName);

    }
    //代码生成
    public static void excuteGeneratorCode(String driver,String datadabseUrl,String username,String password,
                                           String tablePrefix,String parentPackageName,String moduleName){
        AutoGenerator mpg = new AutoGenerator();
        //全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");//文件输出地址
        gc.setOutputDir(projectPath+"/userCenter/src/main/java");
        gc.setFileOverride(false);
        gc.setOpen(false);
        gc.setAuthor("Bsea");
        gc.setActiveRecord(false);
        gc.setEnableCache(false);//xml，二级缓存
        gc.setBaseResultMap(false);
        gc.setBaseColumnList(false);
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("&sMapper");
        gc.setXmlName("%sMapper");
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);
        //数据源配置------------------
        DataSourceConfig dec = new DataSourceConfig();
        dec.setDriverName(driver);
        dec.setUsername(username);
        dec.setPassword(password);
        dec.setUrl(datadabseUrl);
        dec.setDataSource(dec);
        //策略配置===================
        StategyConfig stategyConfig=new StrategyConfig();
        //前缀
        stategyConfig.setTablePrefix(new String[]{tablePrefix});
        //数据库表映射到实体的命名策略
        stategyConfig.setNaming(NamingStratrfy.underline_to_camel);
        stategyConfig.setColumnNaming(NamingStratrfy.underline_to_camel);
        //需要排除的表名，允许使用正则表达式
        stategyConfig.setExclude("user_info");
        stategyConfig.setEntityLombokMode(true);
        stategyConfig.setEntityBooleanColumnRemoveIsPrefix(false);
        stategyConfig.setRestControllerStyle(false);//生成@RestController控制器，默认为true
        stategyConfig.setControllerMappingHyphenStyle(true);//驼峰转连字符，默认true
        mpg.setStrategy(stategyConfig);
        //包配置-------------------------------
        PackageConfig pc = new PackageConfig();
        //父包名，如果为空，将下面子包名必须写全，否则就只需写子包名
        pc.setParent(parentPackageName);
        pc.setModuleName(moduleName);
        pc.setEntity("entity");//实体类包名
        pc.setController("controller");//控制层包名，默认为web
        pc.setService("service");//业务接口层包名
        pc.setServiceImpl("serviceImpl");//业务实现层报名
        pc.setMapper("mapper");//mapper接口层包名
        pc.setXml("xml");//mapper.xml包名
        mpg.setPackageInfo(pc);
        //自定义配置----------------
        //如果模板引擎是freemrker
        //String templatePath="templetes/mapper.xml.ftl";
        //如果模板引擎是velocity
        String templatePath ="templates/mapper.xml/vm";
        InjectionConfig injectionConfig = new InjectionConfig(){
            public void initMap(){

            }
        };
        List<FileOutputConfig> focList = new ArrayList<FileOutConfig>();
        //调整xml生成目录演示
        focList.add(new FileOutConfig(templatePath)){
            public String outputFile(TaleInfo tableInfo){
                return projectPath+"/userCenter/src/main/resources/mapper/"+
                        tableInfo.getEntityName()+"Mapper"+StringPool.DOT_XML;
            }
        })
        injectionConfig.setFileOutConfigList(focList);
        mpg.setCfg(injectionConfig);
        mpg.setTemplate(new TemplateConfig(),setXml(null));
        //执行生成
        mpg.execute();


    }
}
