package ru.citycheck.core.domain

import org.jobrunr.jobs.mappers.JobMapper
import org.jobrunr.storage.StorageProvider
import org.jobrunr.storage.sql.postgres.PostgresStorageProvider
import org.jooq.Transaction
import org.jooq.TransactionContext
import org.jooq.TransactionProvider
import org.jooq.tools.JooqLogger
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.stereotype.Component
import org.springframework.transaction.TransactionDefinition.PROPAGATION_NESTED
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.DefaultTransactionDefinition
import java.time.Clock
import javax.sql.DataSource

@Configuration
@ComponentScan
@Import(
    DataSourceAutoConfiguration::class,
    FlywayAutoConfiguration::class,
    JooqAutoConfiguration::class,
)
@EnableAutoConfiguration
class DomainConfiguration {
    @Bean
    fun storageProvider(jobMapper: JobMapper, dataSource: DataSource): StorageProvider? {
        val storageProvider = PostgresStorageProvider(dataSource)
        storageProvider.setJobMapper(jobMapper)
        return storageProvider
    }

    @Bean
    fun clock(): Clock {
        return Clock.systemUTC()
    }
}

@Component
class SpringTransactionProvider(
    private val txMgr: DataSourceTransactionManager,
) : TransactionProvider {
    override fun begin(ctx: TransactionContext) {
        log.info("Begin transaction")

        val tx = txMgr.getTransaction(DefaultTransactionDefinition(PROPAGATION_NESTED))
        ctx.transaction(SpringTransaction(tx))
    }

    override fun commit(ctx: TransactionContext) {
        log.info("commit transaction")
        txMgr.commit((ctx.transaction() as SpringTransaction).tx)
    }

    override fun rollback(ctx: TransactionContext) {
        log.info("rollback transaction")
        txMgr.rollback((ctx.transaction() as SpringTransaction).tx)
    }

    companion object {
        private val log = JooqLogger.getLogger(SpringTransactionProvider::class.java)
    }
}

internal class SpringTransaction(val tx: TransactionStatus) : Transaction