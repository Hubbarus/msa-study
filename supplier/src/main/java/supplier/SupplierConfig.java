package supplier;

import customer.CustomerConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "supplier")
@Import(value = CustomerConfig.class)
public class SupplierConfig {
}
