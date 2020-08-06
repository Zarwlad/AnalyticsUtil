select e.type,
       round(avg(total_sending_ms)/1000, 0) as avg_total_sending_in_secs,
       round(avg(total_sending_ms)/360000, 2) as avg_total_sending_in_hours,
       round(avg(total_sending_ms)/(360000 * 24), 2) as avg_total_sending_in_days,
       count(*) as calculated_events_count
from event_stat
         join event e on event_stat.event_id = e.id
where e.created_date between timestamp '2020-06-01 00:00:00' and timestamp '2020-06-30 23:59:59'

group by e.type

union
select
    '_TOTAL_',
    round(avg(total_sending_ms)/1000, 0) as avg_total_sending_in_secs,
    round(avg(total_sending_ms)/360000, 2) as avg_total_sending_in_hours,
    round(avg(total_sending_ms)/(360000 * 24), 2) as avg_total_sending_in_days,
    count(*) as calculated_events_count
from event_stat
         join event e on event_stat.event_id = e.id
where e.created_date between timestamp '2020-06-01 00:00:00' and timestamp '2020-06-30 23:59:59'