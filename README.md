# HW_2sem
# RPS
rate(cdc_outbox_execution_time_seconds_count[1m])
# Среднее время исполнения
increase(cdc_outbox_execution_time_seconds_sum[1m]) / increase(cdc_outbox_execution_time_seconds_count[1m])
