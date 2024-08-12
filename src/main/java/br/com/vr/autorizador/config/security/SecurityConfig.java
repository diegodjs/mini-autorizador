package br.com.vr.autorizador.config.security;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

/**
 * SecurityConfig - Classe de controle de acesso
 * @author Diego
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Value("${autenticacao.usuario}")
	private String autenticacaoUsuario;
	
	@Value("${autenticacao.senha}")
	private String autenticacaoSenha;
	
	@Value("${admin.usuario}")
	private String adminUsuario;
	
	@Value("${admin.senha}")
	private String adminSenha;

	@Bean
	public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {

		UserDetails user = User.withUsername(autenticacaoUsuario).password(passwordEncoder.encode(autenticacaoSenha)).roles("USER").build();
		UserDetails admin = User.withUsername(adminUsuario).password(passwordEncoder.encode(adminSenha)).roles("USER", "ADMIN").build();

		return new InMemoryUserDetailsManager(user, admin);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests(request -> request.requestMatchers("/**").authenticated()
					.anyRequest().permitAll())
				 .csrf(AbstractHttpConfigurer::disable)
				.httpBasic(Customizer.withDefaults()).build();
	}

	@Bean
	public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
	    return new BeanPostProcessor() {

	        @Override
	        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
	            return bean;
	        }

	        @SuppressWarnings("unused")
			private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
	            List<T> copy = mappings.stream()
	                    .filter(mapping -> mapping.getPatternParser() == null)
	                    .collect(Collectors.toList());
	            mappings.clear();
	            mappings.addAll(copy);
	        }

	        @SuppressWarnings({ "unchecked", "unused" })
	        private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
	            try {
	                Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
	                field.setAccessible(true);
	                return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
	            } catch (IllegalArgumentException | IllegalAccessException e) {
	                throw new IllegalStateException(e);
	            }
	        }
	    };
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return encoder;
	}
}
