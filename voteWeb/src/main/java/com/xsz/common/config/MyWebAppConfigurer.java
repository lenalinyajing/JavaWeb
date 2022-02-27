//package java.com.xsz.common.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//public class MyWebAppConfigurer implements WebMvcConfigurer {
//    @Value("${imagesPath}")
//    private String mImagePath;
//
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        imagePath = imageOath.substring(0,imagesPath.lastIndexOf("/"))+"/images";
//        mImagesPath=imagePath;
//        registry.addResourceHandler("/images/**").addResourceLocations(mImagePath);
//    }
//}
