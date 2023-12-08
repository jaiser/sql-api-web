package fun.jaiser.sqlapiweb;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@Slf4j
@MapperScan("fun.jaiser.sqlapiweb.mapper") //添加mapper扫描
public class SqlApiWebApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(SqlApiWebApplication.class, args);
        showRunInfo(application);

    }

    public static void showRunInfo(ConfigurableApplicationContext application) {
        Environment env = application.getEnvironment();

        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            ip = "localhost";
        }

        String port = env.getProperty("server.port");

        port = port == null ? "8080" : port;

        String path = env.getProperty("server.servlet.context-path");

        path = path == null ? "" : path;

        String appName = env.getProperty("spring.application.name");

        log.info("\n----------------------------------------------------------\n\t" +

                "Application is running! Access URLs:\n\t" +

                "服务名称：\t\t" + appName + "\n\t" +

                "本地访问地址: \thttp://localhost:" + port + path + "/index.html\n\t" +

                "外部访问地址: \thttp://" + ip + ":" + port + path + "/index.html\n\t" +

                "Swagger地址: \thttp://localhost:" + port + path + "/swagger-ui/index.html\n" +

                "----------------------------------------------------------");
    }
}
