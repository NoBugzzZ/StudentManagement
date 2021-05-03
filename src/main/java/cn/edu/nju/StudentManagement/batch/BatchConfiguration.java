package cn.edu.nju.StudentManagement.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import cn.edu.nju.StudentManagement.model.Student;

import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public PoiItemReader<Student> reader() {
        PoiItemReader<Student> reader = new PoiItemReader<>();
        reader.setName("studentItemReader");
        reader.setLinesToSkip(1);
        reader.setResource(new FileSystemResource("src/main/resources/测试数据.xlsx"));
        reader.setRowMapper(excelRowMapper());
        return reader;
    }

    private RowMapper<Student> excelRowMapper() {
        return new StudentExcelRowMapper();
    }

    @Bean
    public StudentItemProcessor processor() {
        return new StudentItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Student> writer(DataSource dataSource) {
        //System.out.println("[dataSource]: "+dataSource);
        return new JdbcBatchItemWriterBuilder<Student>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql("INSERT INTO student (student_id, student_name, sex, birthday, native_place, department, phone) VALUES (:studentId, :name, :sex, :birthday, :nativePlace, :department, :phone)")
            .dataSource(dataSource)
            .build();
    }

    @Bean
    public Job importStudentJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importStudentJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1)
            .end()
            .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Student> writer) {
        return stepBuilderFactory.get("step1")
            .<Student, Student> chunk(10)
            .reader(reader())
            .processor(processor())
            .writer(writer)
            .build();
    }
}