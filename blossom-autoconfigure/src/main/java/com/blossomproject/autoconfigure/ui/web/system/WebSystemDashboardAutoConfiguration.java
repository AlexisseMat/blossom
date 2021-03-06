package com.blossomproject.autoconfigure.ui.web.system;

import com.blossomproject.core.common.utils.privilege.Privilege;
import com.blossomproject.core.common.utils.privilege.SimplePrivilege;
import com.blossomproject.ui.menu.MenuItem;
import com.blossomproject.ui.menu.MenuItemBuilder;
import com.blossomproject.ui.web.system.dashboard.DashboardController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.autoconfigure.health.HealthEndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.HealthIndicatorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsEndpointAutoConfiguration;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Maël Gargadennnec on 04/05/2017.
 */
@Configuration
@AutoConfigureAfter({HealthEndpointAutoConfiguration.class, MetricsEndpointAutoConfiguration.class})
@ConditionalOnClass(DashboardController.class)
@ConditionalOnBean({HealthEndpoint.class, MetricsEndpoint.class})
public class WebSystemDashboardAutoConfiguration {

  @Bean
  public MenuItem systemDashboardMenuItem(MenuItemBuilder builder,
    @Qualifier("systemMenuItem") MenuItem systemMenuItem) {
    return builder
      .key("dashboard")
      .label("menu.system.dashboard")
      .link("/blossom/system/dashboard")
      .order(0)
      .icon("fa fa-bar-chart")
      .parent(systemMenuItem)
      .privilege(dashboardPrivilegePlugin())
      .build();
  }

  @Bean
  public DashboardController dashboardController(HealthEndpoint healthEndpoint,
    MetricsEndpoint metricsEndpoint) {
    return new DashboardController(healthEndpoint, metricsEndpoint);
  }

  @Bean
  public Privilege dashboardPrivilegePlugin() {
    return new
      SimplePrivilege("system", "dashboard", "manager");
  }
}
