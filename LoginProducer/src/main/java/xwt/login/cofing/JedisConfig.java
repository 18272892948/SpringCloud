package xwt.login.cofing;


import com.xwt.cofing.SystemConst;
import com.xwt.util.JedisUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class JedisConfig {
    @Bean
    public JedisUtil createJ(){
        return new JedisUtil(SystemConst.REDISHOST,SystemConst.REDISPORT,SystemConst.REDISPASS);
    }
}
