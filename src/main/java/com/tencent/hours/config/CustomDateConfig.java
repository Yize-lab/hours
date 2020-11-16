//package com.tencent.hours.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.convert.converter.Converter;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//@Configuration
//public class CustomDateConfig {
//    @Bean
//    public Converter<String, LocalDate> localDateConverter() {
//        return new CustomDateConverter.LocalDateConvert();
//    }
//}
//
//class CustomDateConverter {
//    public static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";
//
//    private CustomDateConverter() {
//    }
//
//    public static class LocalDateConvert implements Converter<String, LocalDate> {
//        @Override
//        public LocalDate convert(String date) {
//            return LocalDate.parse(date, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
//        }
//    }
//}