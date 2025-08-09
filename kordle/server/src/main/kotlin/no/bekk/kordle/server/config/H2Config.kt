package no.bekk.kordle.server.config

import org.h2.tools.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class H2Config {

    @Bean(initMethod = "start", destroyMethod = "stop")
    fun h2TcpServer(): Server {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9090")
    }
}
