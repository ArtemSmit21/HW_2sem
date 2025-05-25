# HW_2sem
# RPS
rate(cdc_outbox_execution_time_seconds_count[1m])
# Среднее время исполнения
increase(cdc_outbox_execution_time_seconds_sum[1m]) / increase(cdc_outbox_execution_time_seconds_count[1m])
# Quantile Response
histogram_quantile(0.5, sum(rate(cdc_outbox_execution_time_seconds_bucket[$__rate_interval])) by (le))
histogram_quantile(0.75, sum(rate(cdc_outbox_execution_time_seconds_bucket[$__rate_interval])) by (le))
histogram_quantile(0.95, sum(rate(cdc_outbox_execution_time_seconds_bucket[$__rate_interval])) by (le))
histogram_quantile(0.99, sum(rate(cdc_outbox_execution_time_seconds_bucket[$__rate_interval])) by (le))
# Histogram Response
sum(rate(cdc_outbox_execution_time_seconds_bucket[1m])) by (le)
