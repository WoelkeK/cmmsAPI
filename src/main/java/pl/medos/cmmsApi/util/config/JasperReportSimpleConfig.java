package pl.medos.cmmsApi.util.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import pl.medos.cmmsApi.util.SimpleReportExporter;
import pl.medos.cmmsApi.util.SimpleReportFiller;

@Configuration
public class JasperReportSimpleConfig {


    @Bean
    public SimpleReportFiller reportFiller() {
        return new SimpleReportFiller();
    }

    @Bean
    public SimpleReportExporter reportExporter() {
        return new SimpleReportExporter();
    }

}
