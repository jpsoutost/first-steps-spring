package academy.mindswap.sportsdatabase.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.client.GraphQlClient;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RestClientConfiguration {

    @Value("${ATHLETE_DATA_SERVICE_URL}")
    private String url;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
    @Bean
    public WebClient getWebClient() {
        return WebClient.builder().build();
    }

    @Bean
    HttpGraphQlClient httpGraphQlClient(WebClient wc){
        return HttpGraphQlClient.builder(wc).url(url).build();
    }

}
