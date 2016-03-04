ALTER TABLE outbound_subscriptions
ADD (service_provider VARCHAR(255));

ALTER TABLE subscriptions
ADD (service_provider VARCHAR(255));